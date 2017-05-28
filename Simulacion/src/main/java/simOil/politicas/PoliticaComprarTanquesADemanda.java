package simOil.politicas;

import simOil.logger.Logger;
import simOil.Simulador;
import simOil.TanqueEnConstruccion;
import simOil.reguladores.ReguladorPozo;

public class PoliticaComprarTanquesADemanda implements PoliticaCompraDeTanques {
    private Logger logger;
    public PoliticaComprarTanquesADemanda(Logger logger) {
        this.logger = logger;
    }

    public void aplicarPolitica(Simulador unSimulador){
        ReguladorPozo elReguladorDePozos = unSimulador.reguladorPozo;
        double capacidadDeExtraccionTotal = elReguladorDePozos.capacidadDeExtraccionTotal(1);
        double proporcionDeAguaMaximo = unSimulador.reservorio.proporcionDeAgua*capacidadDeExtraccionTotal;
        double proporcionDeGasMaximo = unSimulador.reservorio.proporcionDeGas*capacidadDeExtraccionTotal;
        double capacidadAlmacenamientoDeAgua = unSimulador.reguladorTanqueAgua.futuraCapacidadAlmacenamientoTotal();
        double capacidadAlmacenamientoDeGas = unSimulador.reguladorTanqueGas.futuraCapacidadAlmacenamientoTotal();

        while (proporcionDeAguaMaximo>capacidadAlmacenamientoDeAgua){
            TanqueEnConstruccion tanqueAguaConstruido = unSimulador.reguladorTanqueAgua.comprarTanque();
            logger.loguear("Se compró un tanque de almacenamiento de agua que va a estar listo en "+tanqueAguaConstruido.diasRestantes+" días");
            capacidadAlmacenamientoDeAgua = unSimulador.reguladorTanqueAgua.futuraCapacidadAlmacenamientoTotal();
        }

        while(proporcionDeGasMaximo>capacidadAlmacenamientoDeGas){
            TanqueEnConstruccion tanqueGasConstruido = unSimulador.reguladorTanqueGas.comprarTanque();
            logger.loguear("Se compró un tanque de almacenamiento de gas que va a estar listo en "+tanqueGasConstruido.diasRestantes+" días");
            capacidadAlmacenamientoDeGas = unSimulador.reguladorTanqueGas.futuraCapacidadAlmacenamientoTotal();
        }

    }

}
