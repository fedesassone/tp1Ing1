package simOil;

import simOil.logger.Logger;

public class Reservorio {

    public double proporcionDeGas;
    public double proporcionDePetroleo;
    public double proporcionDeAgua;

    private double volumenR;
    private double volumenGlobalExtraido;
    private double volumenGlobalReinyectado;
    private Logger logger;

    public Reservorio(double proporcionDeGas, double proporcionDePetroleo, double proporcionDeAgua,
                      double volumenR, Logger logger) {
        assert((proporcionDeAgua + proporcionDeGas + proporcionDePetroleo) == 1.0);
        this.proporcionDeGas = proporcionDeGas;
        this.proporcionDePetroleo = proporcionDePetroleo;
        this.proporcionDeAgua = proporcionDeAgua;
        this.volumenR = volumenR;
        this.volumenGlobalExtraido = 0;
        this.volumenGlobalReinyectado = 0;
        this.logger = logger;
    }

    public double volumenActual() {
        return volumenR - volumenGlobalExtraido + volumenGlobalReinyectado;
    }

    public double volumenExtraido() {
        return volumenGlobalExtraido;
    }

    public double volumenInicial(){
        return volumenR;
    }

    //FIXME: Se puede actualizarPresionPorReinyeccion de a mas de a un producto? Segun como estan escritas las formulas parece que no
    public void reinyectar(double unVolumenAReinyectarAgua, double unVolumenAReinyectarGas){
        logger.loguear("Se reinyecto " + unVolumenAReinyectarAgua + "cm3 de agua y "
                + unVolumenAReinyectarGas + "cm3 de gas");
        double volumenTotalReinyectado = unVolumenAReinyectarAgua + unVolumenAReinyectarGas;
        assert(volumenGlobalReinyectado + volumenTotalReinyectado <= volumenGlobalExtraido);

        proporcionDePetroleo = proporcionElementoAlReinyectar(proporcionDePetroleo, 0, volumenTotalReinyectado);
        proporcionDeGas = proporcionElementoAlReinyectar(proporcionDeGas, unVolumenAReinyectarGas, volumenTotalReinyectado);
        proporcionDeAgua = proporcionElementoAlReinyectar(proporcionDeAgua, unVolumenAReinyectarAgua, volumenTotalReinyectado);
        volumenGlobalReinyectado += volumenTotalReinyectado;
    }

    public void extraer(double unVolumen){
        volumenGlobalExtraido += unVolumen;
    }

    //FIXME: En el enunciado dice que se podria cambiar esta formula, deberíamos abstraerla?
    //FIXME: Estas formulas no son exactamente las del enunciado (no se entienden las del enunciado,
    //       las escritas aca son las que tiene sentido que sean)
    //Formula 4 del enunciado
    private double proporcionElementoAlReinyectar(double proporcionViejaProducto,
                                                  double volumenReinyectadoEsteProducto,
                                                  double volumenReinyectadoTotal){
        return (proporcionViejaProducto * volumenActual() + volumenReinyectadoEsteProducto) /
                (volumenActual() + volumenReinyectadoTotal);
    }

}
