package simOil.politicas;

import simOil.Simulador;

public interface PoliticaReinyeccion extends Politica {
    //FIXME: Cambiar nombre para que quede mas claro que es el booleano?
    boolean hayQueReinyectar(Simulador simulador);

    void realizarReinyeccion(Simulador simulador);
}
