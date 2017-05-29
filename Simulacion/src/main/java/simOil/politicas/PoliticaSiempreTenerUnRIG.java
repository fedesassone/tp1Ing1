package simOil.politicas;

import simOil.ParametrosSimulacion;
import simOil.PozoEnExcavacion;
import simOil.RIG;
import simOil.Simulador;
import simOil.logger.Logger;

import java.util.List;

public class PoliticaSiempreTenerUnRIG implements PoliticaAlquilerDeRIGs {

    public void aplicarPolitica(Simulador unSimulador){
        Logger log = unSimulador.logger;
        List<PozoEnExcavacion> pozosEnExcavacion = unSimulador.reguladorPozo.damePozosEnExcavacion();
        if(unSimulador.rigsAlquilados.isEmpty()){
            if(!pozosEnExcavacion.isEmpty()){
                log.loguear("Se alquilo un RIG BASICO por " + ParametrosSimulacion.numeroDiasAlquilerDeRigs +
                        " con un costo de $" + (ParametrosSimulacion.rigBasicoCostoPorDia * ParametrosSimulacion.rigBasicoCostoPorDia));
                unSimulador.costoTotal=+ ParametrosSimulacion.rigBasicoCostoPorDia;
                RIG unNuevoRig = new RIG(1,
                        ParametrosSimulacion.rigBasicoPoderExcavacion,
                        ParametrosSimulacion.numeroDiasAlquilerDeRigs);
                 unSimulador.rigsAlquilados.add(unNuevoRig);
            }
        }
    }

}
