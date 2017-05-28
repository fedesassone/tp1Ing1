package simOil;

import simOil.logger.Logger;
import simOil.logger.LoggerAConsola;

class SimulacionApp {
    //FIXME: Completar
    public static void main(String[] args) {
        ParametrosSimulacion params = new ParametrosSimulacion();

        Logger logger = new LoggerAConsola();
        Reservorio reservorio = new Reservorio(
                params.proporcionGas,
                params.proporcionAgua,
                params.proporcionPetroleo,
                params.volumenInicial,
                logger);
        Simulador simulador = new Simulador(reservorio, logger);
    }
}
