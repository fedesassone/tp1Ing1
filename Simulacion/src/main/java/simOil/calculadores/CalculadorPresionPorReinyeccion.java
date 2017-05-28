package simOil.calculadores;

public interface CalculadorPresionPorReinyeccion {

    double nuevaPresionAnteReinyeccion(double volumenAntesReinyeccion, double volumenReinyectado,
                                       double volumenInicial, double presionAntesDeReinyeccion);

    double volumenAReinyectarDadaPresionDeseada(double presionActual, double presionDeseada,
                                                double volumenInicial, double volumenActual);

}