package simOil.politicas;

import org.junit.jupiter.api.Test;
import simOil.Fixtures;
import simOil.Parcela;
import simOil.Simulador;
import simOil.TipoDeTerreno;
import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;

class PoliticaSeleccionDeParcelasMenorProfundidadTest {

    @Test
    void testSeleccionDeParcelasMenorProfundidad(){
        Simulador simulador = new Fixtures().simuladorParaTesting();
        PoliticaSeleccionDeParcelas politicaSeleccionDeParcelas = new PoliticaSeleccionDeParcelasMenorProfundidad(2);

        Parcela parcela1 = new Parcela(TipoDeTerreno.TERRENO_TIERRA, 200, 3, new CalculadorPresionPorReinyeccionImpl());
        simulador.parcelasNoExcavadas.add(parcela1);
        Parcela parcela2 = new Parcela(TipoDeTerreno.TERRENO_TIERRA, 200, 5, new CalculadorPresionPorReinyeccionImpl());
        simulador.parcelasNoExcavadas.add(parcela2);
        Parcela parcela3 = new Parcela(TipoDeTerreno.TERRENO_TIERRA, 200, 7, new CalculadorPresionPorReinyeccionImpl());
        simulador.parcelasNoExcavadas.add(parcela3);

        politicaSeleccionDeParcelas.seleccionarParcelasAExcavar(simulador);
        assert(simulador.reguladorPozo.damePozosEnExcavacion().get(0).parcela() == parcela1);
        assert(simulador.reguladorPozo.damePozosEnExcavacion().get(1).parcela() == parcela2);
    }

}