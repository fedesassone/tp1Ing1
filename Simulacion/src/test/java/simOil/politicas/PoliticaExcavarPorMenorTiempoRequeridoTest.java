package simOil.politicas;

import org.junit.jupiter.api.Test;
import simOil.Fixtures;
import simOil.PozoEnExcavacion;
import simOil.RIG;
import simOil.Simulador;

class PoliticaExcavarPorMenorTiempoRequeridoTest {

    @Test
    void testPoliticaExcavarPorMenorTiempoRequerido(){
        Simulador simulador = new Fixtures().simuladorParaTesting();
        PoliticaExcavacion politicaExcavacion = new PoliticaExcavarPorMenorTiempoRequerido();

        RIG rigUsado = new Fixtures().rigParaTesting();
        simulador.rigsAlquilados.add(rigUsado);
        simulador.rigsAlquilados.add(rigUsado);
        simulador.reguladorPozo.damePozosEnExcavacion().add(new PozoEnExcavacion(100 + rigUsado.poderDeExcavacionDiario, new Fixtures().parcelaParaTesting()));
        simulador.reguladorPozo.damePozosEnExcavacion().add(new PozoEnExcavacion(150 + rigUsado.poderDeExcavacionDiario, new Fixtures().parcelaParaTesting()));
        simulador.reguladorPozo.damePozosEnExcavacion().add(new PozoEnExcavacion(200 + rigUsado.poderDeExcavacionDiario, new Fixtures().parcelaParaTesting()));

        politicaExcavacion.aplicarPolitica(simulador);

        assert(simulador.reguladorPozo.damePozosEnExcavacion().get(0).profundidadRestante() == 100);
        assert(simulador.reguladorPozo.damePozosEnExcavacion().get(1).profundidadRestante() == 150);
        assert(simulador.reguladorPozo.damePozosEnExcavacion().get(2).profundidadRestante() == 200 + rigUsado.poderDeExcavacionDiario);
    }

}