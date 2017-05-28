package simOil.politicas;

import org.junit.jupiter.api.Test;
import simOil.Fixtures;
import simOil.Simulador;

class PoliticaVenderPorcentajeGasTest {

    @Test
    public void testVentaDeGas(){
        Simulador simulador = new Fixtures().simuladorParaTesting();
        PoliticaVentaDeGas politicaVentaDeGas = new PoliticaVenderPorcentajeGas(0.3);

        simulador.reguladorTanqueGas.almacenar(1000);
        politicaVentaDeGas.realizarVentaDeGas(simulador);
        assert(simulador.reguladorTanqueGas.capacidadEnUsoTotal() == 700);
        politicaVentaDeGas.realizarVentaDeGas(simulador);
        assert(simulador.reguladorTanqueGas.capacidadEnUsoTotal() == 490);
    }

}