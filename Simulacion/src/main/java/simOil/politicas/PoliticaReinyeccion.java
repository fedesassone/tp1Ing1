package simOil.politicas;

import simOil.Simulador;

public interface PoliticaReinyeccion extends Politica {

    boolean hayQueReinyectar(Simulador simulador);

    void realizarReinyeccion(Simulador simulador);
}
