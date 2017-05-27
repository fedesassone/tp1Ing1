public class PlantaSeparadora {

    int numeroPlanta;
    private double capacidadProcesamiento;
    private Logger logger;

    public PlantaSeparadora(int numeroPlanta, double capacidadProcesamiento, Logger logger) {
        this.numeroPlanta = numeroPlanta;
        this.capacidadProcesamiento = capacidadProcesamiento;
        this.logger = logger;
    }

    //FIXME: Se deberia loguear en cuanto de agua y de gas se separo el producto
    //       Le pasamos la proporcion de gas y agua como parametro?
    public void separar(double unVolumen) {
        logger.loguear("Se separo " + unVolumen + "cm3 en la planta " + numeroPlanta);
    }

    public double capacidadProcesamiento(){
        return capacidadProcesamiento;
    }

}
