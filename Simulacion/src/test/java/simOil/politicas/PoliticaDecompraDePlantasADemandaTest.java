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

public class PoliticaDecompraDePlantasADemandaTest {
    @Test
    public void test1() {
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 500, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));
        pozosCompletados.add(new Pozo(2, 50, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));


        pozosCompletados.add(new Pozo(3, 50, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);
        int cantidadInicial = reguladorPlantaSeparadora.numeroDePlantasEnConstruccion();
        double b = reguladorPozo.capacidadDeExtraccionTotal(3);
        double a = reguladorPlantaSeparadora.capacidadDeSeparacionTotal();
        //double b = reguladorPozo.cant
        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000, new LoggerAConsola());
        LoggerAConsola logger = new LoggerAConsola();
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);
        PoliticaComprarPlantasADemanda politicaCompra = new PoliticaComprarPlantasADemanda(logger);
        politicaCompra.aplicarPolitica(simulador);
        int cantidadFinal = reguladorPlantaSeparadora.numeroDePlantasEnConstruccion();
        assert(cantidadFinal>cantidadInicial);
        assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(3));
        assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(5));

    }



    @Test
    public void test2() {
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 500, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));
        pozosCompletados.add(new Pozo(2, 50, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));



        pozosCompletados.add(new Pozo(3, 50, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);
        int cantidadInicial = reguladorPlantaSeparadora.numeroDePlantasEnConstruccion();
        double b = reguladorPozo.capacidadDeExtraccionTotal(1);
        double a = reguladorPlantaSeparadora.capacidadDeSeparacionTotal();
        //double b = reguladorPozo.cant
        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000, new LoggerAConsola());
        LoggerAConsola logger = new LoggerAConsola();
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);
        PoliticaComprarPlantasADemanda politicaCompra = new PoliticaComprarPlantasADemanda(logger);
        politicaCompra.aplicarPolitica(simulador);
        int cantidadFinal = reguladorPlantaSeparadora.numeroDePlantasEnConstruccion();
        double c = reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal();

        assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(3));
        assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(5));
    }
}
