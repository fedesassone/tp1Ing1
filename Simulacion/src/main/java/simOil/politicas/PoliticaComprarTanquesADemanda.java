package simOil.politicas;

import simOil.ParametrosSimulacion;
import simOil.logger.Logger;
import simOil.Simulador;
import simOil.TanqueEnConstruccion;
import simOil.reguladores.ReguladorPozo;

public class PoliticaComprarTanquesADemanda implements PoliticaCompraDeTanques {

    public void aplicarPolitica(Simulador unSimulador){
        Logger log = unSimulador.logger;

        ReguladorPozo elReguladorDePozos = unSimulador.reguladorPozo;
        double capacidadDeExtraccionTotal = elReguladorDePozos.capacidadDeExtraccionTotal(1);
        double proporcionDeAguaMaximo = unSimulador.reservorio.proporcionDeAgua*capacidadDeExtraccionTotal;
        double proporcionDeGasMaximo = unSimulador.reservorio.proporcionDeGas*capacidadDeExtraccionTotal;
        double capacidadAlmacenamientoDeAgua = unSimulador.reguladorTanqueAgua.futuraCapacidadAlmacenamientoTotal();
        double capacidadAlmacenamientoDeGas = unSimulador.reguladorTanqueGas.futuraCapacidadAlmacenamientoTotal();

        while (proporcionDeAguaMaximo>capacidadAlmacenamientoDeAgua){
            log.loguear("Se compró un Tanque de Agua por un costo de " + ParametrosSimulacion.TANQUE_AGUA_COSTO);
            unSimulador.costoTotal=+ ParametrosSimulacion.TANQUE_AGUA_COSTO;

            TanqueEnConstruccion tanqueAguaConstruido = unSimulador.reguladorTanqueAgua.comprarTanque();
            log.loguear("El tanque de agua que va a estar listo en "+tanqueAguaConstruido.diasRestantes+" días");
            capacidadAlmacenamientoDeAgua = unSimulador.reguladorTanqueAgua.futuraCapacidadAlmacenamientoTotal();
        }

        while(proporcionDeGasMaximo>capacidadAlmacenamientoDeGas){
            log.loguear("Se compró un Tanque de Gas por un costo de " + ParametrosSimulacion.TANQUE_GAS_COSTO);
            unSimulador.costoTotal=+ ParametrosSimulacion.TANQUE_GAS_COSTO;

            TanqueEnConstruccion tanqueGasConstruido = unSimulador.reguladorTanqueGas.comprarTanque();
            log.loguear("Se compró un tanque de almacenamiento de gas que va a estar listo en "+tanqueGasConstruido.diasRestantes+" días");
            capacidadAlmacenamientoDeGas = unSimulador.reguladorTanqueGas.futuraCapacidadAlmacenamientoTotal();
        }

    }

}
