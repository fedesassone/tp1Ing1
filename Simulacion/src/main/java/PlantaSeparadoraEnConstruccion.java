/**
 * Created by federico on 25/05/17.
 */
public class PlantaSeparadoraEnConstruccion {

    int diasRestantes;

    public PlantaSeparadoraEnConstruccion(int diasRestantes) {
        assert(diasRestantes > 0);
        this.diasRestantes = diasRestantes;
    }

    public void avanzarDiaConstruccion(){
        if(diasRestantes > 0){
            diasRestantes -= 1;
        }
    }

    public boolean construccionTerminada(){
        return diasRestantes == 0;
    }
}
