package simOil;

import simOil.logger.Logger;
import simOil.logger.LoggerAConsola;
import simOil.politicas.PoliticaSeleccionDeParcelas;
import simOil.politicas.PoliticaSeleccionDeParcelasSeleccionarLaPrimeraDisponible;

class SimulacionApp {
    //FIXME: Completar
    public static void main(String[] args) {
        ParametrosSimulacion params = new ParametrosSimulacion();

        //logger.loguear("Bienvenido. Seleccione una Pol");

        //Avanzar día de construcciones:
            //Avanzar PlantaEnConstruccion

            //Avanzar



        Logger logger = new LoggerAConsola();
        Reservorio reservorio = new Reservorio(
                params.proporcionGas,
                params.proporcionAgua,
                params.proporcionPetroleo,
                params.volumenInicial,
                logger,
                params.areaReservorio);
        Simulador simulador = new Simulador(reservorio, logger);
        simulador.generarParcelasIniciales();

        PoliticaSeleccionDeParcelas politicaSeleccionDeParcelas = new PoliticaSeleccionDeParcelasSeleccionarLaPrimeraDisponible();
        politicaSeleccionDeParcelas.aplicarPolitica(simulador);



        //logger.loguear();
    }
}
