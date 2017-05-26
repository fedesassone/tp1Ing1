/**
 * Created by federico on 25/05/17.
 */
public class PlantaSeparadora {

    private Logger logger;
    int numeroPlanta;
    private double capacidadProcesamiento;

    public PlantaSeparadora(Logger logger, int numeroPlanta, double capacidadProcesamiento) {
        this.logger = logger;
        this.numeroPlanta = numeroPlanta;
        this.capacidadProcesamiento = capacidadProcesamiento;
    }

    public void separar(double unVolumen) {
        logger.loguear("Se separo " + unVolumen + "cm3 en la planta " + numeroPlanta);
    }

    public double capacidadProcesamiento(){
        return capacidadProcesamiento;
    }

}
