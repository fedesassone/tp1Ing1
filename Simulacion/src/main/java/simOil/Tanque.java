package simOil;

import simOil.logger.Logger;

public class Tanque {

    public int numeroTanque;
    public double capacidadTotal;
    public double espacioUtilizado;
    private TipoDeProducto tipoDeProducto;
    private Logger logger;

    public Tanque(int numeroTanque, double capacidadTotal, TipoDeProducto tipoDeProducto, Logger logger) {
        this.numeroTanque = numeroTanque;
        this.capacidadTotal = capacidadTotal;
        this.espacioUtilizado = 0;
        this.logger = logger;
        this.tipoDeProducto = tipoDeProducto;
    }

    public void extraer(double unaCantidad){
        assert(espacioUtilizado >= unaCantidad);
        espacioUtilizado -= unaCantidad;
        logger.loguear("Se extrajo " + unaCantidad + "cm3 del tanque " + numeroTanque + " de tipo " + tipoDeProducto);
    }

    public void almacenar(double unaCantidad){
        assert(espacioUtilizado + unaCantidad <= capacidadTotal);
        espacioUtilizado += unaCantidad;
        logger.loguear("Se almaceno " + unaCantidad + "cm3 del tanque " + numeroTanque + " de tipo " + tipoDeProducto);
    }

    public double capacidadLibre(){
        return capacidadTotal - espacioUtilizado;
    }

}
