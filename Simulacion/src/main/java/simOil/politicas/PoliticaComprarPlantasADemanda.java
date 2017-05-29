package simOil.politicas;

import simOil.ParametrosSimulacion;
import simOil.logger.Logger;
import simOil.PlantaSeparadoraEnConstruccion;
import simOil.Simulador;
import simOil.logger.LoggerAConsola;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import sun.util.resources.cldr.lag.LocaleNames_lag;

public class PoliticaComprarPlantasADemanda implements PoliticaCompraDePlantas {


    public void aplicarPolitica(Simulador unSimulador){

        Logger log = unSimulador.logger;
        ReguladorPlantaSeparadora elReguladorDePlantas = unSimulador.reguladorPlantaSeparadora;
        ReguladorPozo elReguladorDePozos = unSimulador.reguladorPozo;
        double capacidadDeExtraccionTotal = elReguladorDePozos.capacidadDeExtraccionTotal(1);
        double capacacidadAFuturo = elReguladorDePlantas.futuraCapacidadDeSeparacionTotal();

        while(capacidadDeExtraccionTotal>capacacidadAFuturo){

            log.loguear("Se compró una Planta Separadora por un costo de " + ParametrosSimulacion.PLANTA_SEPARADORA_COSTO);
            unSimulador.costoTotal=+ ParametrosSimulacion.PLANTA_SEPARADORA_COSTO;

            PlantaSeparadoraEnConstruccion nuevaPlanta = elReguladorDePlantas.comprarPlantaSeparadora();
            log.loguear("Se compro la planta separadora que va a estar listo en "+nuevaPlanta.diasRestantes+" días " +
                    "por un precio de $" + ParametrosSimulacion.PLANTA_SEPARADORA_COSTO);
            capacacidadAFuturo = elReguladorDePlantas.futuraCapacidadDeSeparacionTotal();
        }


    }

}
