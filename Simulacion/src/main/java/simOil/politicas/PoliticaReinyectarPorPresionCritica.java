package simOil.politicas;

import simOil.Pozo;
import simOil.Simulador;
import simOil.calculadores.CalculadorPresionPorReinyeccion;

import java.util.Iterator;

public class PoliticaReinyectarPorPresionCritica implements PoliticaReinyeccion {

    double presionCritica;
    CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion;

    public PoliticaReinyectarPorPresionCritica(double presionCritica, CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion) {
        this.presionCritica = presionCritica;
        this.calculadorPresionPorReinyeccion = calculadorPresionPorReinyeccion;
    }

    public boolean hayQueReinyectar(Simulador unSimulador){
        boolean hayQueReinyectar = false;

        Iterator<Pozo> pozosIt = unSimulador.reguladorPozo.damePozosCompletados().iterator();
        while(pozosIt.hasNext() && !hayQueReinyectar){
            hayQueReinyectar = pozosIt.next().presionActualBocaDePozo < presionCritica;
        }
        return hayQueReinyectar;
    }

    /**
     *  IDEA:
     *      Se reinyecta primero agua que es lo que solo sirve para realizarReinyeccion
     *      Si el agua no alcanza se reinyecta gas
     *      Si ninguno de los dos alcanza se compra el agua necesaria
     */
    public void realizarReinyeccion(Simulador unSimulador){
        double volumenAguaAlmacenado = unSimulador.reguladorTanqueAgua.capacidadEnUsoTotal();
        double volumenGasAlmacenado = unSimulador.reguladorTanqueGas.capacidadEnUsoTotal();

        double presionMinimaDePozos = Double.MAX_VALUE;
        for(Pozo pozo : unSimulador.reguladorPozo.damePozosCompletados()){
            if(pozo.presionActualBocaDePozo < presionMinimaDePozos){
                presionMinimaDePozos = pozo.presionInicial;
            }
        }

        //Vemos cuanto material se debe actualizarPresionPozosPorReinyeccion
        double volumenNecesarioReinyectar = calculadorPresionPorReinyeccion.volumenAReinyectarDadaPresionDeseada(
                presionMinimaDePozos, presionCritica,
                unSimulador.reservorio.volumenInicial(), unSimulador.reservorio.volumenActual());

        //No se reinyecta demasiado como para hacer valer que vol_globalReinyectado >= vol_globalExtraido
        double volumenAReinyectarParcial = Math.min(
                unSimulador.reservorio.volumenInicial() - unSimulador.reservorio.volumenActual(),
                volumenNecesarioReinyectar);

        if(volumenAReinyectarParcial > 0){
            //Vemos cuanto reinyectamos de agua sin comprar
            double volumenAReinyectarAguaDeTanques = Math.min(volumenAguaAlmacenado, volumenAReinyectarParcial);
            volumenAReinyectarParcial -= volumenAReinyectarAguaDeTanques;

            //Vemos cuanto reinyectamos de gas
            double volumenAReinyectarGas = Math.min(volumenGasAlmacenado, volumenAReinyectarParcial);
            volumenAReinyectarParcial -= volumenAReinyectarGas;

            //Vemos cuanta agua se necesita comprar
            double volumenAComprarAgua = volumenAReinyectarParcial;

            double volumenTotalAReinyectar = volumenAReinyectarAguaDeTanques + volumenAReinyectarGas + volumenAComprarAgua;

            //Se hace la compra y extraccion de agua y gas de los tanques
            unSimulador.comprarAgua(volumenAComprarAgua);
            unSimulador.reguladorTanqueAgua.extraer(volumenAReinyectarAguaDeTanques);
            unSimulador.reguladorTanqueGas.extraer(volumenAReinyectarGas);

            //Se actualiza la presion de los pozos y parcelas
            unSimulador.reguladorPozo.actualizarPresionPozosPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                    unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar);
            unSimulador.parcelasExcavacionEmpezada.forEach(
                    parcela -> parcela.actualizarPresionPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                            unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar));
            unSimulador.parcelasNoExcavadas.forEach(
                    parcela -> parcela.actualizarPresionPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                            unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar));

            //Se actualiza el volumen del reservorio
            unSimulador.reservorio.reinyectar(volumenAReinyectarAguaDeTanques + volumenAComprarAgua,
                    volumenAReinyectarGas);
        }
    }
}
