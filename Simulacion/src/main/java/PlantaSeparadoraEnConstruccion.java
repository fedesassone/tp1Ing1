/**
 * Created by federico on 25/05/17.
 */
public class PlantaSeparadoraEnConstruccion {

    int diasRestantes;
    double capacidadProcesamiento;

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
