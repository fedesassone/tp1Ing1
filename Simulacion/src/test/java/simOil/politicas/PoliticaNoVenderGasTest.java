package simOil.politicas;

import org.junit.jupiter.api.Test;
import simOil.Fixtures;
import simOil.Simulador;

class PoliticaNoVenderGasTest {

    @Test
    public void testVentaDeGas(){
        Simulador simulador = new Fixtures().simuladorParaTesting();
        PoliticaVentaDeGas politicaVentaDeGas = new PoliticaNoVenderGas();

        simulador.reguladorTanqueGas.almacenar(1000);
        politicaVentaDeGas.realizarVentaDeGas(simulador);
        assert(simulador.reguladorTanqueGas.capacidadEnUsoTotal() == 1000);
        politicaVentaDeGas.realizarVentaDeGas(simulador);
        assert(simulador.reguladorTanqueGas.capacidadEnUsoTotal() == 1000);
    }

}