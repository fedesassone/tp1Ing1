package simOil.politicas;

import simOil.Pozo;
import simOil.Reservorio;
import simOil.Simulador;
import simOil.calculadores.CalculadorPresionPorReinyeccion;

import java.util.Iterator;
import java.util.List;

/**
 * Politica:
 *      Se reinyecta cuanta agua y gas sea necesario (de acuerdo a la estrategia mencionada a
 *      continuacion) hasta que todos los pozos tengan una presion no menor a presionCritica
 *      (respetando la restriccion de volumen para la reinyeccion).
 *      Para la reinyeccion del valor seleccionado:
 *          1. Se reinyecta agua de los tanques, ya que su unica funcionalidad es para la reinyeccion
 *          2. Si el agua no alcanza se reinyecta gas de los tanques
 *          3. Si ninguno de los dos pasos anteriores alcanza se compra el agua necesaria
 */
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

    public void realizarReinyeccion(Simulador unSimulador){
        double volumenAguaAlmacenado = unSimulador.reguladorTanqueAgua.capacidadEnUsoTotal();
        double volumenGasAlmacenado = unSimulador.reguladorTanqueGas.capacidadEnUsoTotal();

        double volumenAReinyectarParcial = volumenTotalAReinyectar(unSimulador.reservorio,
                unSimulador.reguladorPozo.damePozosCompletados());

        if(volumenAReinyectarParcial > 0){
            //Vemos cuanto reinyectamos de agua sin comprar
            double volumenAReinyectarAguaDeTanques = Math.min(volumenAguaAlmacenado, volumenAReinyectarParcial);
            volumenAReinyectarParcial -= volumenAReinyectarAguaDeTanques;

            //Vemos cuanto reinyectamos de gas
            double volumenAReinyectarGas = Math.min(volumenGasAlmacenado, volumenAReinyectarParcial);
            volumenAReinyectarParcial -= volumenAReinyectarGas;

            //Vemos cuanta agua se necesita comprar
            double volumenAComprarAgua = volumenAReinyectarParcial;

            reinyectarEnSimulacion(volumenAReinyectarAguaDeTanques, volumenAReinyectarGas,
                    volumenAComprarAgua, unSimulador);
        }
    }

    private double volumenTotalAReinyectar(Reservorio reservorio, List<Pozo> pozos){
        //Se obtiene la presion minima de los pozos (la cual determina exclusivamente cuanto se tiene
        //que reinyectar)
        double presionMinimaDePozos = Double.MAX_VALUE;
        for(Pozo pozo : pozos){
            if(pozo.presionActualBocaDePozo < presionMinimaDePozos){
                presionMinimaDePozos = pozo.presionActualBocaDePozo;
            }
        }

        //Vemos cuanto material se debe actualizarPresionPozosPorReinyeccion
        double volumenNecesarioReinyectar = calculadorPresionPorReinyeccion.volumenAReinyectarDadaPresionDeseada(
                presionMinimaDePozos, presionCritica,
                reservorio.volumenInicial(), reservorio.volumenActual());

        //No se reinyecta demasiado como para hacer valer que vol_globalReinyectado >= vol_globalExtraido
        return Math.min(
                reservorio.volumenInicial() - reservorio.volumenActual(),
                volumenNecesarioReinyectar);
    }

    private void reinyectarEnSimulacion(double volumenAReinyectarAguaDeTanques,
                                        double volumenAReinyectarGas, double volumenAComprarAgua,
                                        Simulador unSimulador){
        double volumenTotalAReinyectar = volumenAReinyectarAguaDeTanques + volumenAReinyectarGas + volumenAComprarAgua;

        //Se hace la compra y extraccion de agua y gas de los tanques
        unSimulador.comprarAgua(volumenAComprarAgua);
        unSimulador.reguladorTanqueAgua.extraer(volumenAReinyectarAguaDeTanques);
        unSimulador.reguladorTanqueGas.extraer(volumenAReinyectarGas);

        //Se actualiza la presion de los pozos y parcelas
        unSimulador.reguladorPozo.actualizarPresionPozosPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar);
        unSimulador.parcelasNoExcavadas.forEach(
                parcela -> parcela.actualizarPresionPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                        unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar));

        //Se actualiza el volumen del reservorio
        unSimulador.reservorio.reinyectar(volumenAReinyectarAguaDeTanques + volumenAComprarAgua,
                volumenAReinyectarGas);
    }
}
