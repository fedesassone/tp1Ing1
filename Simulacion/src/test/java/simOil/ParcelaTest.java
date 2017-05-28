package simOil;

import org.junit.jupiter.api.Test;
import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.LoggerAConsola;

class ParcelaTest {

    @Test
    void testReinyeccionPozo(){
        //Parametros
        int pozosHabilitados = 2;
        double volumenInicial = 1000;
        double volumenAntesReinyeccion = 500;
        double volumenAReinyectar = 20;

        Parcela parcela = new Parcela(TipoDeTerreno.TERRENO_ARCILLA, 30, 1000, new CalculadorPresionPorReinyeccionImpl());
        double presionInicialParcela = parcela.presionActual;

        //Extraemos del pozo
        parcela.actualizarPresionPorReinyeccion(volumenInicial, volumenAntesReinyeccion, volumenAReinyectar);

        double presionEsperada = presionInicialParcela *
                ((volumenAntesReinyeccion + volumenAReinyectar) / volumenInicial);
        assert(parcela.presionActual == presionEsperada);

        parcela.actualizarPresionPorReinyeccion(volumenInicial, volumenAntesReinyeccion, volumenAReinyectar);

        double presionEsperada2 = presionInicialParcela *
                ((volumenAntesReinyeccion + volumenAReinyectar) / volumenInicial);
        assert(parcela.presionActual == presionEsperada2);
    }

}