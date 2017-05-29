package simOil.politicas;

import simOil.ParametrosSimulacion;
import simOil.PozoEnExcavacion;
import simOil.RIG;
import simOil.Simulador;
import simOil.logger.Logger;

import java.util.List;

public class PoliticaTenerUnRigParaCadaPozo implements PoliticaAlquilerDeRIGs {
    public void aplicarPolitica(Simulador simulador) {
        Logger log = simulador.logger;
        List<PozoEnExcavacion> listaPozosEnExcavacion = simulador.reguladorPozo.damePozosEnExcavacion();
        List<RIG> listaRigsAlquilados = simulador.rigsAlquilados;

        int cantidadRigsAlquilados = listaRigsAlquilados.size();
        int cantidadPozosEnExcavacion = listaPozosEnExcavacion.size();
        if(cantidadRigsAlquilados<cantidadPozosEnExcavacion){
            for (int i = cantidadRigsAlquilados; i<cantidadPozosEnExcavacion;i++){
                log.loguear("Se alquilo un RIG BASICO por " + ParametrosSimulacion.numeroDiasAlquilerDeRigs +
                        " con un costo de " + (ParametrosSimulacion.rigBasicoCostoPorDia * ParametrosSimulacion.rigBasicoCostoPorDia));
                simulador.costoTotal=+ ParametrosSimulacion.rigBasicoCostoPorDia;
                RIG nuevoRig = new RIG(cantidadRigsAlquilados+1,
                        ParametrosSimulacion.rigBasicoPoderExcavacion,
                        ParametrosSimulacion.numeroDiasAlquilerDeRigs);
                simulador.rigsAlquilados.add(nuevoRig);
            }
        }
    }
}
