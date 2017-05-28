package simOil.politicas;

import simOil.ParametrosSimulacion;
import simOil.PozoEnExcavacion;
import simOil.RIG;
import simOil.Simulador;

import java.util.List;

public class PoliticaSiempreTenerUnRIG implements PoliticaCompraDeRIGs {

    public void aplicarPolitica(Simulador unSimulador){
        //TODO: Completar
        List<PozoEnExcavacion> pozosEnExcavacion = unSimulador.reguladorPozo.damePozosEnExcavacion();
        if(unSimulador.rigsAlquilados.isEmpty()){
            if(!pozosEnExcavacion.isEmpty()){
                RIG unNuevoRig = new RIG(1,
                        ParametrosSimulacion.RIG_BASICO_PODER_EXCAVACION,
                        0,
                        10);
                 unSimulador.rigsAlquilados.add(unNuevoRig);
            }
        }
    }

}
