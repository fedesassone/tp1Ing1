import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class PoliticaFinalizarPorDilucionCriticaTest {

    @Test
    public void testSeDebeFinalizar(){
        //Setup de politica, pozos, plantas y tanques
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 150, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(2, 150, new CalculadorPresionPorReinyeccionImpl(),new Logger()));
        pozosCompletados.add(new Pozo(3, 150, new CalculadorPresionPorReinyeccionImpl(),new Logger()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);
        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000);
        Logger logger = new Logger();
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);

        PoliticaFinalizacion politicaFinalizacion = new PoliticaFinalizarPorDilucionCritica(2 * new ParametrosSimulacion().dilucionCriticaPetroleo);
        assert(politicaFinalizacion.hayQueFinalizarSimulacion(simulador));
    }

    @Test
    public void testNoSeDebeFinalizar(){
        //Setup de politica, pozos, plantas y tanques
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 150, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(2, 150, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(3, 150, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);
        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000);
        Logger logger = new Logger();
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);

        PoliticaFinalizacion politicaFinalizacion = new PoliticaFinalizarPorDilucionCritica(new ParametrosSimulacion().dilucionCriticaPetroleo / 2);
        assert(!politicaFinalizacion.hayQueFinalizarSimulacion(simulador));
    }

}