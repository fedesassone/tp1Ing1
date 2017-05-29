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
        //TODO: cuerpo del codigo repetido entre este método y seleccionarParcelasAExcavar de simOil.politicas.PoliticaComprarTanquesADemanda

        Logger log = unSimulador.logger;
        ReguladorPlantaSeparadora elReguladorDePlantas = unSimulador.reguladorPlantaSeparadora;
        ReguladorPozo elReguladorDePozos = unSimulador.reguladorPozo;
        int pozosCompletados = elReguladorDePozos.cantidadPozosCompletados();
        double capacidadDeExtraccionTotal = elReguladorDePozos.capacidadDeExtraccionTotal(1);
        double capacacidadAFuturo = elReguladorDePlantas.futuraCapacidadDeSeparacionTotal();

        while(capacidadDeExtraccionTotal>capacacidadAFuturo){

            log.loguear("Se compró una Planta Separadora por un costo de " + ParametrosSimulacion.PLANTA_SEPARADORA_COSTO);
            unSimulador.costoTotal=+ ParametrosSimulacion.PLANTA_SEPARADORA_COSTO;

            PlantaSeparadoraEnConstruccion nuevaPlanta = elReguladorDePlantas.comprarPlantaSeparadora();
            //FIXME: Esta bien que se loggue esto aca? No deberia loggearlo el regulador por como manejamos el logger por ahora?
            //       De donde se obtiene el precio de lo que se compro?
            //       A quien se avisa que algo se compro para registrar esto?
            log.loguear("Se compro la planta separadora que va a estar listo en "+nuevaPlanta.diasRestantes+" días");
            capacacidadAFuturo = elReguladorDePlantas.futuraCapacidadDeSeparacionTotal();
        }


    }

}
