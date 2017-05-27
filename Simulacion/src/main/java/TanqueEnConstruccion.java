public class TanqueEnConstruccion {
    int diasRestantes;
    double capacidad;
    public TanqueEnConstruccion(int diasRestantes, double capacidad) {
        assert(diasRestantes > 0);
        this.diasRestantes = diasRestantes;
        this.capacidad = capacidad;
    }

    public void avanzarDiaConstruccion(){
        if(diasRestantes > 0){
            diasRestantes -= 1;
        }
    }
    public double capacidad(){
        return this.capacidad;
    }

    public boolean construccionTerminada(){
        return diasRestantes == 0;
    }
}
