/**
 * Created by federico on 25/05/17.
 */
public class Pozo {
    int id;
    double presionActualBocaDePozo;

    public Pozo(int id, double presionActualBocaDePozo) {
        this.id = id;
        this.presionActualBocaDePozo = presionActualBocaDePozo; //presion inicial : [3000, 3500]
    }

    //FIXME: No se deberían obtener los alphas de los parametros de la simulación?
    public double potencialDeVolumenDiario(double alphaUno, double alphaDos, int pozosHabilitados){
        return alphaUno * (presionActualBocaDePozo /pozosHabilitados) +
                alphaDos * Math.pow(presionActualBocaDePozo /pozosHabilitados, 2);
    }

    public void reinyectar() {}

    public void actualizarPresionBocaDePozo(double volumenInicial, double volumenActual, int pozosHabilitados) {
        //volumenInicial: [10 000 000, 1 000 000 000]
        double beta_i = (0.1 * (volumenInicial/volumenActual) ) /
                Math.pow(pozosHabilitados, 2/3);
        this.presionActualBocaDePozo = presionActualBocaDePozo * Math.pow(Math.E,beta_i);
    }
}
