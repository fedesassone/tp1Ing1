public class TanqueEnConstruccion {
    int diasRestantes;

    public TanqueEnConstruccion(int diasRestantes) {
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
