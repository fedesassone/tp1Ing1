package simOil.politicas;

import org.junit.jupiter.api.Test;
import simOil.*;
import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.LinkedList;
import java.util.List;

public class PoliticaDeCompraDeTanquesADemandaTest {
    @Test
    public void tesTanquesAgua() {
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 500, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(2, 50, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(3, 50, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(4, 50, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);

        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.8, 0.0, 0.2, 1000);
        Logger logger = new Logger();
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);

        int cantInicialTanquesDeAgua = reguladorTanqueAgua.cantidadTanquesEnConstruccion();

        PoliticaComprarTanquesADemanda politicaCompra = new PoliticaComprarTanquesADemanda(logger);
        politicaCompra.aplicarPolitica(simulador);
        double capacidadExtraccionAguaMaximo = simulador.reservorio.proporcionDeAgua*simulador.reguladorPozo.capacidadDeExtraccionTotal(3);

        assert (simulador.reguladorTanqueAgua.futuraCapacidadAlmacenamientoTotal() >= capacidadExtraccionAguaMaximo);
        assert (simulador.reguladorTanqueAgua.cantidadTanquesEnConstruccion() >= cantInicialTanquesDeAgua);

        //int cantidadFinal = reguladorPlantaSeparadora.numeroDePlantasEnConstruccion();
        //assert(cantidadFinal>cantidadInicial);
        //assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(3));
        //assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(5));

    }

    @Test
    public void tesTanquesGas() {
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 500, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(2, 50, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(3, 50, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(4, 50, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);

        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000);
        Logger logger = new Logger();
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);

        int cantInicialTanquesDeGas = reguladorTanqueGas.cantidadTanquesEnConstruccion();

        PoliticaComprarTanquesADemanda politicaCompra = new PoliticaComprarTanquesADemanda(logger);
        politicaCompra.aplicarPolitica(simulador);
        double capacidadExtraccionGasMaximo = simulador.reservorio.proporcionDeGas*simulador.reguladorPozo.capacidadDeExtraccionTotal(1);

        assert (simulador.reguladorTanqueGas.futuraCapacidadAlmacenamientoTotal() >= capacidadExtraccionGasMaximo);
        assert (simulador.reguladorTanqueGas.cantidadTanquesEnConstruccion() >= cantInicialTanquesDeGas);

        //int cantidadFinal = reguladorPlantaSeparadora.numeroDePlantasEnConstruccion();
        //assert(cantidadFinal>cantidadInicial);
        //assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(3));
        //assert(reguladorPlantaSeparadora.futuraCapacidadDeSeparacionTotal() >= reguladorPozo.capacidadDeExtraccionTotal(5));

    }

}
