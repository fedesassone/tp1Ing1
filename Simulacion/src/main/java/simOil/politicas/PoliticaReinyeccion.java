package simOil.politicas;

import simOil.Simulador;

public interface PoliticaReinyeccion {

    boolean hayQueReinyectar(Simulador simulador);

    void realizarReinyeccion(Simulador simulador);
}
