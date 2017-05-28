package simOil.politicas;

import simOil.Simulador;

/**
 * Politica:
 *      Se debe finalizar la simulacion si es que la proporcion de petroleo en el reservorio
 *      es menor a un valor critico dilucionCriticaPetroleo.
 */
public class PoliticaFinalizarPorDilucionCritica implements PoliticaFinalizacion {

    private double dilucionCriticaPetroleo;

    public PoliticaFinalizarPorDilucionCritica(double dilucionCriticaPetroleo) {
        this.dilucionCriticaPetroleo = dilucionCriticaPetroleo;
    }

    public boolean hayQueFinalizarSimulacion(Simulador unSimulador){
        return unSimulador.reservorio.proporcionDePetroleo < dilucionCriticaPetroleo;
    }

}
