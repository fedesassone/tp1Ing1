import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PozoTest {
    @Test
    void potencialDeVolumenDiario() {
        int pozosHabilitados = 1;
        double presionIncialBocaDelPozo = 0.5;
        double alphaUno = 0.4;
        double alphaDos = 0.004;
        Pozo pozo = new Pozo(1, presionIncialBocaDelPozo);
        double volumen = pozo.potencialDeVolumenDiario(alphaUno,alphaDos,pozosHabilitados);
        double resultado = alphaUno*(presionIncialBocaDelPozo) + alphaDos * Math.pow((presionIncialBocaDelPozo),2);
        assert(volumen == resultado);
    }
}