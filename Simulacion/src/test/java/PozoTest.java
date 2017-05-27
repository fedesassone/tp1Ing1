import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PozoTest {
    @Test
    void potencialDeVolumenDiario() {
        ParametrosSimulacion param = new ParametrosSimulacion();

        int pozosHabilitados = 1;
        double presionIncialBocaDelPozo = 0.5;
        double alphaUno = param.alpha1;
        double alphaDos = param.alpha2;
        Pozo pozo = new Pozo(1, presionIncialBocaDelPozo, new CalculadorPresionPorReinyeccionImpl(), new Logger());
        double volumen = pozo.potencialDeVolumenDiario(pozosHabilitados);
        double resultado = alphaUno*(presionIncialBocaDelPozo) + alphaDos * Math.pow((presionIncialBocaDelPozo),2);
        assert(volumen == resultado);
    }

    @Test
    void testExtraccionDePozo(){
        //Parametros
        int pozosHabilitados = 2;
        double volumenInicial = 1000;
        double volumenAntesExtraccion = 500;
        double volumenAExtraer = 3;

        Pozo pozo = new Pozo(1, 30, new CalculadorPresionPorReinyeccionImpl(), new Logger());
        double presionInicialPozo = pozo.presionActualBocaDePozo;

        //Extraemos del pozo
        pozo.extraer(volumenAExtraer, volumenInicial, volumenAntesExtraccion, pozosHabilitados);

        double beta_i = (0.1 * (volumenInicial/(volumenAntesExtraccion - volumenAExtraer)) ) /
                Math.pow(pozosHabilitados, 4/3);
        double presionEsperada = presionInicialPozo * Math.pow(Math.E,beta_i);
        assert(pozo.presionActualBocaDePozo == presionEsperada);
    }

    @Test
    void testReinyeccionPozo(){
        //Parametros
        int pozosHabilitados = 2;
        double volumenInicial = 1000;
        double volumenAntesReinyeccion = 500;
        double volumenAReinyectar = 20;

        Pozo pozo = new Pozo(1, 30, new CalculadorPresionPorReinyeccionImpl(), new Logger());
        double presionInicialPozo = pozo.presionActualBocaDePozo;

        //Extraemos del pozo
        pozo.actualizarPresionPorReinyeccion(volumenInicial, volumenAntesReinyeccion, volumenAReinyectar);

        double presionEsperada = presionInicialPozo *
                ((volumenAntesReinyeccion + volumenAReinyectar) / volumenInicial);
        assert(pozo.presionActualBocaDePozo == presionEsperada);
    }
}