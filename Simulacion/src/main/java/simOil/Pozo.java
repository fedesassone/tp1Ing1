package simOil;

import simOil.calculadores.CalculadorPresionPorReinyeccion;
import simOil.logger.Logger;

public class Pozo {

    //Obtenemos los valores de las constantes para la extraccion de los pozos
    private double alpha1 = new ParametrosSimulacion().alpha1;
    private double alpha2 = new ParametrosSimulacion().alpha2;

    public int id;
    public double presionInicial;
    public double presionActualBocaDePozo;
    private CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion;
    private Logger logger;

    //FIXME: Acordarse de borrar la parcela al crear el pozo
    public Pozo(int id, double presionActualBocaDePozo, CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion, Logger logger) {
        this.id = id;
        this.presionInicial = presionActualBocaDePozo;
        this.presionActualBocaDePozo = presionActualBocaDePozo;
        this.calculadorPresionPorReinyeccion = calculadorPresionPorReinyeccion;
        this.logger = logger;
    }

    //Formula 1 del enunciado
    public double potencialDeVolumenDiario(int pozosHabilitados){
        return alpha1 * (presionActualBocaDePozo /pozosHabilitados) +
                alpha2 * Math.pow(presionActualBocaDePozo /pozosHabilitados, 2);
    }

    public void extraer(double unVolumen, double volumenInicial, double volumenAntesExtraccion,
                        int pozosHabilitados){
        assert(unVolumen <= potencialDeVolumenDiario(pozosHabilitados));
        assert(volumenAntesExtraccion - unVolumen >= 0);
        actualizarPresionBocaDePozo(
                volumenInicial, volumenAntesExtraccion - unVolumen,
                pozosHabilitados
        );
        logger.loguear("Se extrajeron " + unVolumen + "cm3 del pozo " + id);
    }

    public void actualizarPresionPorReinyeccion(double volumenInicial, double volumenAntesReinyeccion,
                                                double volumenReinyectado) {
        assert(volumenAntesReinyeccion + volumenReinyectado <= volumenInicial);
        this.presionActualBocaDePozo = calculadorPresionPorReinyeccion.nuevaPresionAnteReinyeccion(
                volumenAntesReinyeccion, volumenReinyectado, volumenInicial, this.presionInicial);
    }

    //Formula 2 del enunciado
    private void actualizarPresionBocaDePozo(double volumenInicial, double volumenActual, int pozosHabilitados) {
        double beta_i = (0.1 * (volumenInicial/volumenActual) ) /
                Math.pow(pozosHabilitados, 4/3);
        this.presionActualBocaDePozo = presionActualBocaDePozo * Math.pow(Math.E,-beta_i);
    }
}
