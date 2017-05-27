/**
 * Created by federico on 25/05/17.
 */
public class PlantaSeparadora {

    int numeroPlanta;
    private double capacidadProcesamiento;
    private Logger logger;

    public PlantaSeparadora(int numeroPlanta, double capacidadProcesamiento, Logger logger) {
        this.numeroPlanta = numeroPlanta;
        this.capacidadProcesamiento = capacidadProcesamiento;
        this.logger = logger;
    }

    public void separar(double unVolumen) {
        logger.loguear("Se separo " + unVolumen + "cm3 en la planta " + numeroPlanta);
    }

    public double capacidadProcesamiento(){
        return capacidadProcesamiento;
    }

}
