package simOil.politicas;

import simOil.ParametrosSimulacion;
import simOil.PozoEnExcavacion;
import simOil.RIG;
import simOil.Simulador;
import simOil.logger.Logger;

import java.util.List;

public class PoliticaSiempreTenerUnRIG implements PoliticaAlquilerDeRIGs {

    public void aplicarPolitica(Simulador unSimulador){
        //TODO: Completar
        Logger log = unSimulador.logger;
        List<PozoEnExcavacion> pozosEnExcavacion = unSimulador.reguladorPozo.damePozosEnExcavacion();
        if(unSimulador.rigsAlquilados.isEmpty()){
            if(!pozosEnExcavacion.isEmpty()){
                log.loguear("Se compró un RIG BASICO por un costo de " + ParametrosSimulacion.RIG_BASICO_COSTO);
                unSimulador.costoTotal=+ ParametrosSimulacion.RIG_BASICO_COSTO;
                RIG unNuevoRig = new RIG(1,
                        ParametrosSimulacion.RIG_BASICO_PODER_EXCAVACION,
                        10);
                 unSimulador.rigsAlquilados.add(unNuevoRig);
            }
        }
    }

}
