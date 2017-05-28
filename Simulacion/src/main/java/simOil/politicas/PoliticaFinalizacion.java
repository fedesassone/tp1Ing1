package simOil.politicas;

import simOil.Simulador;

public interface PoliticaFinalizacion extends Politica {

    boolean hayQueFinalizarSimulacion(Simulador simulador);
}
