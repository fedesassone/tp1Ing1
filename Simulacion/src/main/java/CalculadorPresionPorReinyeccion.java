public interface CalculadorPresionPorReinyeccion {

    double nuevaPresionAnteReinyeccion(double volumenAntesReinyeccion, double volumenReinyectado,
                                       double volumenInicial, double presionAntesDeReinyeccion);

    double volumenAReinyectarDadaPresionDeseada(double presionPozo, double presionDeseada,
                                                double volumenInicial, double volumenActual);

}