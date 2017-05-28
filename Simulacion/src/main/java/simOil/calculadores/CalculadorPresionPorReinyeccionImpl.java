package simOil.calculadores;

public class CalculadorPresionPorReinyeccionImpl implements CalculadorPresionPorReinyeccion {

    //Formula 3 del enunciado
    public double nuevaPresionAnteReinyeccion(double volumenAntesReinyeccion, double volumenReinyectado,
                                              double volumenInicial, double presionInicial){
        double proporcionVolumenSobreInicial = (volumenAntesReinyeccion + volumenReinyectado) / volumenInicial;
        return presionInicial * proporcionVolumenSobreInicial;
    }

    //Derivado de la formula 3 del enunciado
    public double volumenAReinyectarDadaPresionDeseada(double presionActual, double presionDeseada,
                                                       double volumenInicial, double volumenActual){
        return Math.max((presionDeseada / presionActual) * volumenInicial - volumenActual, 0);
    }

}