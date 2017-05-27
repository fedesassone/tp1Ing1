public class Tanque {

    int numeroTanque;
    double capacidadTotal;
    double espacioUtilizado;
    private Logger logger;

    public Tanque(int numeroTanque, double capacidadTotal, Logger logger) {
        this.numeroTanque = numeroTanque;
        this.capacidadTotal = capacidadTotal;
        this.espacioUtilizado = 0;
        this.logger = logger;
    }

    public void extraer(double unaCantidad){
        assert(espacioUtilizado >= unaCantidad);
        espacioUtilizado -= unaCantidad;
        logger.loguear("Se extrajo " + unaCantidad + "cm3 del tanque " + numeroTanque);
    }

    //FIXME: Necesitamos 2 tipos de tanques para aca se logguee que se almaceno?
    public void almacenar(double unaCantidad){
        assert(espacioUtilizado + unaCantidad <= capacidadTotal);
        espacioUtilizado += unaCantidad;
        logger.loguear("Se almaceno " + unaCantidad + "cm3 del tanque " + numeroTanque);
    }

    public double capacidadLibre(){
        return capacidadTotal - espacioUtilizado;
    }

}
