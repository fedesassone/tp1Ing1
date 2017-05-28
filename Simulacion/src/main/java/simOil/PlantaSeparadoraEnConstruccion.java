package simOil;

public class PlantaSeparadoraEnConstruccion {

    public int diasRestantes;
    private double capacidadProcesamiento;

    public PlantaSeparadoraEnConstruccion(int diasRestantes, double capacidadProcesamiento) {
        assert(diasRestantes > 0);
        this.diasRestantes = diasRestantes;
        this.capacidadProcesamiento = capacidadProcesamiento;
    }

    public void avanzarDiaConstruccion(){
        if(diasRestantes > 0){
            diasRestantes -= 1;
        }
    }

    public boolean construccionTerminada(){
        return diasRestantes == 0;
    }

    public double capacidadProcesamiento(){
        return capacidadProcesamiento;
    }
}
