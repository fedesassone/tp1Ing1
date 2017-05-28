package simOil.politicas;


import org.junit.jupiter.api.Test;
import simOil.*;
import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.LoggerAConsola;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.LinkedList;
import java.util.List;

class PoliticaTenerUnRigParaCadaPozoTest {
    @Test
    void testAplicarPolitica() {
        //Setup de politica, pozos, plantas y tanques
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;

        List<PozoEnExcavacion> pozosEnExcavacion = new LinkedList<PozoEnExcavacion>();
        Parcela mockParcela = new Parcela(TipoDeTerreno.TERRENO_TIERRA, 200, 100, new CalculadorPresionPorReinyeccionImpl());
        pozosEnExcavacion.add(new PozoEnExcavacion(200, mockParcela));
        pozosEnExcavacion.add(new PozoEnExcavacion(300, mockParcela));

        ReguladorPozo reguladorPozo = new ReguladorPozo(new LinkedList<Pozo>(), pozosEnExcavacion);

        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);

        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);

        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000, new LoggerAConsola(), 1000000);
        LoggerAConsola logger = new LoggerAConsola();


        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);

        PoliticaCompraDeRIGs politicaCompraDeRIGs = new PoliticaTenerUnRigParaCadaPozo();

        politicaCompraDeRIGs.aplicarPolitica(simulador);

        assert (simulador.rigsAlquilados.size() == 2 );

    }
}