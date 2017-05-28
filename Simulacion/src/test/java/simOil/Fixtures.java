package simOil;

import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.LoggerAConsola;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.LinkedList;
import java.util.List;

public class Fixtures {

    private ParametrosSimulacion param = new ParametrosSimulacion();

    public ReguladorTanque reguladorTanqueCon(int unNumeroDeTanques, TipoDeProducto tipoDeProducto){
        ReguladorTanque reguladorTanque = new ReguladorTanque(tipoDeProducto);

        //Compra de tanques
        for (int i = 0; i < unNumeroDeTanques; i++) {
            reguladorTanque.comprarTanque();
        }

        //Construccion de tanques
        for (int i = 0; i < param.diasConstruccionTanques; i++) {
            reguladorTanque.avanzarDiaConstrucciones();
        }

        return reguladorTanque;
    }

    public ReguladorPlantaSeparadora reguladorPlantaCon(int unNumeroDePlantas){
        ReguladorPlantaSeparadora reguladorPlanta = new ReguladorPlantaSeparadora();

        //Compra de tanques
        for (int i = 0; i < unNumeroDePlantas; i++) {
            reguladorPlanta.comprarPlantaSeparadora();
        }

        //Construccion de tanques
        for (int i = 0; i < param.diasConstruccionPlantas; i++) {
            reguladorPlanta.avanzarDiaConstrucciones();
        }

        return reguladorPlanta;
    }

    //FIXME: Renombrar?
    public Simulador simuladorParaTesting(){
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 150, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));
        pozosCompletados.add(new Pozo(2, 75, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));
        pozosCompletados.add(new Pozo(3, 50, new CalculadorPresionPorReinyeccionImpl(), new LoggerAConsola()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);
        ReguladorTanque reguladorTanqueGas = reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000);
        LoggerAConsola logger = new LoggerAConsola();
        return new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);
    }

}
