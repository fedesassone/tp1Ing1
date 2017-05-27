import java.util.LinkedList;

class SimulacionApp {
    //FIXME: Completar
    public static void main(String[] args) {
        ParametrosSimulacion params = new ParametrosSimulacion();

        Reservorio reservorio = new Reservorio(
                params.proporcionGas,
                params.proporcionAgua,
                params.proporcionPetroleo,
                params.volumenInicial);

        Simulador simulador = new Simulador(reservorio);
    }
}
