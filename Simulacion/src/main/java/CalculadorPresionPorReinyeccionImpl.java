public class CalculadorPresionPorReinyeccionImpl implements CalculadorPresionPorReinyeccion {

    //Formula 3 del enunciado
    public double nuevaPresionAnteReinyeccion(double volumenAntesReinyeccion, double volumenReinyectado,
                                       double volumenInicial, double presionAntesDeReinyeccion){
        double proporcionVolumenSobreInicial = (volumenAntesReinyeccion + volumenReinyectado) / volumenInicial;
        return presionAntesDeReinyeccion * proporcionVolumenSobreInicial;
    }

    //Derivado de la formula 3 del enunciado
    public double volumenAReinyectarDadaPresionDeseada(double presionPozo, double presionDeseada,
                                                double volumenInicial, double volumenActual){
        return Math.max((presionDeseada / presionPozo) * volumenInicial - volumenActual, 0);
    }

}