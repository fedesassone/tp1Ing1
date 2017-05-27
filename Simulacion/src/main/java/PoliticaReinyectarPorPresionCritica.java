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
     *      Se reinyecta primero agua que es lo que solo sirve para aplicarReinyeccion
     *      Si el agua no alcanza se reinyecta gas
     *      Si ninguno de los dos alcanza se compra el agua necesaria
     */
    public void aplicarReinyeccion(Simulador unSimulador){
        double volumenAguaAlmacenado = unSimulador.reguladorTanqueAgua.capacidadAlmacenadaTotal();
        double volumenGasAlmacenado = unSimulador.reguladorTanqueGas.capacidadAlmacenadaTotal();

        double presionMinimaDePozos = Double.MAX_VALUE;
        for(Pozo pozo : unSimulador.reguladorPozo.damePozosCompletados()){
            if(pozo.presionActualBocaDePozo < presionMinimaDePozos){
                presionMinimaDePozos = pozo.presionActualBocaDePozo;
            }
        }

        //Vemos cuanto material se debe actualizarPresionPozosPorReinyeccion
        double volumenNecesarioReinyectarParcial = calculadorPresionPorReinyeccion.volumenAReinyectarDadaPresionDeseada(
                presionMinimaDePozos, presionCritica,
                unSimulador.reservorio.volumenInicial(), unSimulador.reservorio.volumenActual());

        //Vemos cuanto reinyectamos de agua sin comprar
        double volumenAReinyectarAguaDeTanques = Math.min(volumenAguaAlmacenado, volumenNecesarioReinyectarParcial);
        volumenNecesarioReinyectarParcial -= volumenAReinyectarAguaDeTanques;

        //Vemos cuanto reinyectamos de gas
        double volumenAReinyectarGas = Math.min(volumenGasAlmacenado, volumenNecesarioReinyectarParcial);
        volumenNecesarioReinyectarParcial -= volumenAReinyectarAguaDeTanques;

        //Vemos cuanta agua se necesita comprar
        double volumenAComprarAgua = volumenNecesarioReinyectarParcial;

        //Se hace la compra y extraccion de agua y gas de los tanques
        double volumenTotalAReinyectar = volumenAReinyectarAguaDeTanques + volumenAReinyectarGas + volumenAComprarAgua;

        unSimulador.comprarAgua(volumenAComprarAgua);
        unSimulador.reguladorTanqueAgua.extraer(volumenAReinyectarAguaDeTanques);
        unSimulador.reguladorTanqueGas.extraer(volumenAReinyectarGas);
        unSimulador.reguladorPozo.actualizarPresionPozosPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar);
        unSimulador.parcelasExcavacionEmpezada.forEach(
                parcela -> parcela.actualizarPresionPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                        unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar));
        unSimulador.parcelasNoExcavadas.forEach(
                parcela -> parcela.actualizarPresionPorReinyeccion(unSimulador.reservorio.volumenInicial(),
                        unSimulador.reservorio.volumenActual(), volumenTotalAReinyectar));
        unSimulador.reservorio.reinyectar(volumenAReinyectarAguaDeTanques + volumenAComprarAgua,
                volumenAReinyectarGas);
    }
}
