public class Reservorio {

    double proporcionDeGas;
    double proporcionDePetroleo;
    double proporcionDeAgua;

    private double volumenR;
    private double volumenGlobalExtraido;
    private double volumenGlobalReinyectado;

    public Reservorio(double proporcionDeGas, double proporcionDePetroleo, double proporcionDeAgua,
                      double volumenR) {
        assert((proporcionDeAgua + proporcionDeGas + proporcionDePetroleo) == 1.0);
        this.proporcionDeGas = proporcionDeGas;
        this.proporcionDePetroleo = proporcionDePetroleo;
        this.proporcionDeAgua = proporcionDeAgua;
        this.volumenR = volumenR;
        this.volumenGlobalExtraido = 0;
        this.volumenGlobalReinyectado = 0;
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

    //FIXME: Se puede reinyectar de a mas de a un producto? Segun como estan escritas las formulas parece que no
    public void reinyectar(double unVolumenAReinyectar, String producto){
        assert(volumenGlobalReinyectado + unVolumenAReinyectar < volumenGlobalExtraido);

        proporcionDePetroleo = proporcionElementoNoInyectadoAlReinyectar(proporcionDePetroleo, unVolumenAReinyectar);
        if(producto.equals("Agua")){
            proporcionDeGas = proporcionElementoNoInyectadoAlReinyectar(proporcionDeGas, unVolumenAReinyectar);
            proporcionDeAgua = proporcionElementoInyectadoAlReinyectar(proporcionDeAgua, unVolumenAReinyectar);
        } else if(producto.equals("Gas")){
            proporcionDeGas = proporcionElementoInyectadoAlReinyectar(proporcionDeGas, unVolumenAReinyectar);
            proporcionDeAgua = proporcionElementoNoInyectadoAlReinyectar(proporcionDeAgua, unVolumenAReinyectar);
        }
        volumenGlobalReinyectado += unVolumenAReinyectar;
    }

    public void extraer(double unVolumen){
        volumenGlobalExtraido += unVolumen;
    }

    //FIXME: En el enunciado dice que se podria cambiar esta formula, deberíamos abstraerla?
    //FIXME: Estas formulas no son exactamente las del enunciado (no se entienden las del enunciado,
    //       las escritas aca son las que tiene sentido que sean)
    //Formula 4 del enunciado (aplicada a los elementos de los que no se reinyecta)
    private double proporcionElementoNoInyectadoAlReinyectar(double proporcionViejaProducto,
                                                             double volumenReinyectado){
        return (proporcionViejaProducto * volumenActual()) /
                (volumenActual() + volumenReinyectado);
    }

    //Formula 4 del enunciado (aplicada a los elementos que se reinyecta)
    private double proporcionElementoInyectadoAlReinyectar(double proporcionViejaProducto,
                                                           double volumenReinyectado){
        return (proporcionViejaProducto * volumenActual() + volumenReinyectado) /
                (volumenActual() + volumenReinyectado);
    }

}
