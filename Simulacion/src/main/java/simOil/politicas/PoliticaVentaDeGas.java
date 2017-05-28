package simOil.politicas;

import simOil.Simulador;

public interface PoliticaVentaDeGas extends Politica {
    void realizarVentaDeGas(Simulador simulador);
}