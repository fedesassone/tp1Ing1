package simOil.calculadores;

import org.junit.jupiter.api.Test;
import simOil.Pozo;
import simOil.logger.LoggerAConsola;

class CalculadorPresionPorReinyeccionImplTest {

    @Test
    public void testNuevaPresionAnteReinyeccion(){
        //Parametros
        double presionInicial = 40;
        double volumenInicial = 1000;
        double volumenAntesReinyeccion = 500;
        double volumenAReinyectar = 20;

        CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion = new CalculadorPresionPorReinyeccionImpl();

        double presionObtenida = calculadorPresionPorReinyeccion.nuevaPresionAnteReinyeccion(volumenAntesReinyeccion, volumenAReinyectar, volumenInicial, presionInicial);
        double presionEsperada = presionInicial *
                ((volumenAntesReinyeccion + volumenAReinyectar) / volumenInicial);
        assert(presionObtenida == presionEsperada);
    }

    public void testVolumenAReinyectarDadaPresionDeseada(){
        //Parametros
        double presionInicial = 40;
        double volumenInicial = 1000;
        double volumenAntesReinyeccion = 500;
        double volumenActual = 20;

        CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion = new CalculadorPresionPorReinyeccionImpl();

        double presionObtenida = calculadorPresionPorReinyeccion.nuevaPresionAnteReinyeccion(volumenAntesReinyeccion, volumenActual, volumenInicial, presionInicial);
        double volumenObtenido = calculadorPresionPorReinyeccion.volumenAReinyectarDadaPresionDeseada(presionInicial, presionObtenida, volumenInicial, volumenAntesReinyeccion);
        assert(volumenObtenido == volumenActual);
    }

}