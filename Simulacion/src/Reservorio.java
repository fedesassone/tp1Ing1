public class Reservorio {

    double proporcionDeGas;
    double proporcionDePetroleo;
    double proporcionDeAgua;

    private double volumenR;
    private double volumenGlobalExtraido;
    private double volumenGlobalReinyectado;

    public Reservorio(double proporcionDeGas, double proporcionDePetroleo, double proporcionDeAgua,
                      double volumenR) {
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

    public void reinyectar(double unVolumenGas, double unVolumenAgua){
        double volumenReinyectado = unVolumenAgua + unVolumenGas;
        assert(volumenGlobalReinyectado + volumenReinyectado < volumenGlobalExtraido);

        volumenGlobalReinyectado += volumenReinyectado;
        proporcionDePetroleo = proporcionDespuesDeReinyeccion(proporcionDePetroleo, volumenReinyectado);
        proporcionDeGas = proporcionDespuesDeReinyeccion(proporcionDeGas, volumenReinyectado);
        proporcionDeAgua = proporcionDespuesDeReinyeccion(proporcionDeAgua, volumenReinyectado);
    }

    public void extraer(double unVolumen){
        volumenGlobalExtraido += unVolumen;
    }

    //Formula 4 del enunciado (aplicada a las distintas proporciones)
    private double proporcionDespuesDeReinyeccion(double proporcionProducto,
                                                  double volumenReinyectado){
        return (proporcionProducto * (volumenR - volumenGlobalExtraido)) /
                (volumenR - volumenGlobalExtraido + volumenGlobalReinyectado);
    }

}
