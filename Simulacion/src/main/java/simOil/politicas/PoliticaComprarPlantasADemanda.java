package simOil.politicas;

import simOil.Logger;
import simOil.PlantaSeparadoraEnConstruccion;
import simOil.Simulador;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;

public class PoliticaComprarPlantasADemanda implements PoliticaCompraDePlantas {

    private Logger logger;


    public PoliticaComprarPlantasADemanda(Logger logger) {
        this.logger = logger;
    }


    public void aplicarPolitica(Simulador unSimulador){
        //TODO: cuerpo del codigo repetido entre este método y aplicarPolitica de simOil.politicas.PoliticaComprarTanquesADemanda
        ReguladorPlantaSeparadora elReguladorDePlantas = unSimulador.reguladorPlantaSeparadora;
        ReguladorPozo elReguladorDePozos = unSimulador.reguladorPozo;
        int pozosCompletados = elReguladorDePozos.cantidadPozosCompletados();
        double capacidadDeExtraccionTotal = elReguladorDePozos.capacidadDeExtraccionTotal(1);
        double capacacidadAFuturo = elReguladorDePlantas.futuraCapacidadDeSeparacionTotal();

        while(capacidadDeExtraccionTotal>capacacidadAFuturo){
            PlantaSeparadoraEnConstruccion nuevaPlanta = elReguladorDePlantas.comprarPlantaSeparadora();
            logger.loguear("Se compro la planta separadora que va a estar listo en "+nuevaPlanta.diasRestantes+" días");
            capacacidadAFuturo = elReguladorDePlantas.futuraCapacidadDeSeparacionTotal();
        }


    }

}
