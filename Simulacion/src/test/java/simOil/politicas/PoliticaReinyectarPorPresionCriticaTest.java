package simOil.politicas;

import org.junit.jupiter.api.Test;
import simOil.*;
import simOil.calculadores.CalculadorPresionPorReinyeccion;
import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.LoggerAConsola;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.LinkedList;
import java.util.List;

class PoliticaReinyectarPorPresionCriticaTest {

    @Test
    public void testPoliticaReinyeccion(){
        //Setup de politica, pozos, plantas y tanques
        Simulador simulador = new Fixtures().simuladorParaTesting();
        double volumenInicial = simulador.reservorio.volumenInicial();
        simulador.reguladorTanqueGas.almacenar(10);
        simulador.reguladorTanqueAgua.almacenar(15);
        simulador.reservorio.extraer(30);
        simulador.reguladorPozo.damePozosCompletados().get(0).extraer(30, volumenInicial, volumenInicial, 3);
        simulador.reservorio.extraer(14);
        simulador.reguladorPozo.damePozosCompletados().get(1).extraer(14, volumenInicial, volumenInicial - 30, 3);
        simulador.reservorio.extraer(14);
        simulador.reguladorPozo.damePozosCompletados().get(2).extraer(14, volumenInicial, volumenInicial - 44, 3);

        //Testeo que es necesario realizar una reinyeccion
        PoliticaReinyeccion politicaReinyeccion = new PoliticaReinyectarPorPresionCritica(98, new CalculadorPresionPorReinyeccionImpl());
        assert(politicaReinyeccion.hayQueReinyectar(simulador));

        double volumenAntesReinyeccion = simulador.reservorio.volumenActual();
        CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion = new CalculadorPresionPorReinyeccionImpl();
        double volumenReinyectado = calculadorPresionPorReinyeccion.volumenAReinyectarDadaPresionDeseada(
                simulador.reguladorPozo.damePozosCompletados().get(1).presionInicial,
                98,
                simulador.reservorio.volumenInicial(),
                volumenAntesReinyeccion);
        politicaReinyeccion.realizarReinyeccion(simulador);

        //Testeo que los valores de las presiones despues de las reinyecciones son correctos
        Pozo pozo0 = simulador.reguladorPozo.damePozosCompletados().get(0);
        assert(pozo0.presionActualBocaDePozo == pozo0.presionInicial *
                (volumenAntesReinyeccion + volumenReinyectado) / simulador.reservorio.volumenInicial());
        Pozo pozo1 = simulador.reguladorPozo.damePozosCompletados().get(1);
        assert(pozo1.presionActualBocaDePozo == pozo1.presionInicial *
                (volumenAntesReinyeccion + volumenReinyectado) / simulador.reservorio.volumenInicial());
        Pozo pozo2 = simulador.reguladorPozo.damePozosCompletados().get(2);
        assert(pozo2.presionActualBocaDePozo == pozo2.presionInicial *
                (volumenAntesReinyeccion + volumenReinyectado) / simulador.reservorio.volumenInicial());
    }
}