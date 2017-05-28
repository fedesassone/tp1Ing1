package simOil.politicas;

import simOil.Simulador;

public class PoliticaFinalizarPorDilucionCritica implements PoliticaFinalizacion {

    private double dilucionCriticaPetroleo;

    public PoliticaFinalizarPorDilucionCritica(double dilucionCriticaPetroleo) {
        this.dilucionCriticaPetroleo = dilucionCriticaPetroleo;
    }

    public boolean hayQueFinalizarSimulacion(Simulador unSimulador){
        return unSimulador.reservorio.proporcionDePetroleo < dilucionCriticaPetroleo;
    }

}
