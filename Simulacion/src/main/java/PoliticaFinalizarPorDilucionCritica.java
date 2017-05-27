public class PoliticaFinalizarPorDilucionCritica implements PoliticaFinalizacion {

    private double dilucionCriticaPetroleo;

    public PoliticaFinalizarPorDilucionCritica(double dilucionCriticaPetroleo) {
        this.dilucionCriticaPetroleo = dilucionCriticaPetroleo;
    }

    public boolean finalizarSimulacion(Simulador unSimulador){
        return unSimulador.reservorio.proporcionDePetroleo < dilucionCriticaPetroleo;
    }

}
