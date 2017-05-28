package simOil;

class SimulacionApp {
    //FIXME: Completar
    public static void main(String[] args) {
        ParametrosSimulacion params = new ParametrosSimulacion();

        Reservorio reservorio = new Reservorio(
                params.proporcionGas,
                params.proporcionAgua,
                params.proporcionPetroleo,
                params.volumenInicial);
        Logger logger = new Logger();
        Simulador simulador = new Simulador(reservorio, logger);
    }
}
