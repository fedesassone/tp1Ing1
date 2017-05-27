import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class PoliticaExtraerPozosAleatoriosTest {

    @Test
    public void testPoliticaExtraccion(){
        //Setup de politica, pozos, plantas y tanques
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 500, new Logger()));
        pozosCompletados.add(new Pozo(2, 50, new Logger()));
        pozosCompletados.add(new Pozo(3, 50, new Logger()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);
        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000);
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio);

        PoliticaExtraerPozosAleatorios politicaExtraccion = new PoliticaExtraerPozosAleatorios(2);
        politicaExtraccion.aplicarPolitica(simulador);
    }

}