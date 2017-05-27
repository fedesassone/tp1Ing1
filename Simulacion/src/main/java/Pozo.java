public class Pozo {

    //Obtenemos los valores de las constantes para la extraccion de los pozos
    double alpha1 = new ParametrosSimulacion().alpha1;
    double alpha2 = new ParametrosSimulacion().alpha1;

    int id;
    double presionActualBocaDePozo;
    Logger logger;

    public Pozo(int id, double presionActualBocaDePozo, Logger logger) {
        this.id = id;
        this.presionActualBocaDePozo = presionActualBocaDePozo; //presion inicial : [3000, 3500]
        this.logger = logger;
    }

    public double potencialDeVolumenDiario(int pozosHabilitados){
        return alpha1 * (presionActualBocaDePozo /pozosHabilitados) +
                alpha2 * Math.pow(presionActualBocaDePozo /pozosHabilitados, 2);
    }

    public void extraer(double unVolumen, double volumenInicial, double volumenAntesExtraccion,
                        int pozosHabilitados){
        assert(unVolumen <= potencialDeVolumenDiario(pozosHabilitados));
        assert(volumenAntesExtraccion - unVolumen>= 0);
        actualizarPresionBocaDePozo(
                volumenInicial, volumenAntesExtraccion - unVolumen,
                pozosHabilitados
        );
        logger.loguear("Se extrajeron " + unVolumen + "cm3 del pozo " + id);
    }

    public void reinyectar() {
        //TODO: Completar
    }

    private void actualizarPresionBocaDePozo(double volumenInicial, double volumenActual, int pozosHabilitados) {
        //volumenInicial: [10 000 000, 1 000 000 000]
        double beta_i = (0.1 * (volumenInicial/volumenActual) ) /
                Math.pow(pozosHabilitados, 4/3);
        this.presionActualBocaDePozo = presionActualBocaDePozo * Math.pow(Math.E,beta_i);
    }
}
