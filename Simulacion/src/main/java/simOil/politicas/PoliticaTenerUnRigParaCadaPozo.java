package simOil.politicas;

import simOil.ParametrosSimulacion;
import simOil.PozoEnExcavacion;
import simOil.RIG;
import simOil.Simulador;

import java.util.List;

/**
 * Created by federico on 27/05/17.
 */
public class PoliticaTenerUnRigParaCadaPozo implements PoliticaCompraDeRIGs {
    public void aplicarPolitica(Simulador simulador) {

        List<PozoEnExcavacion> listaPozosEnExcavacion = simulador.reguladorPozo.damePozosEnExcavacion();
        List<RIG> listaRigsAlquilados = simulador.rigsAlquilados;

        int cantidadRigsAlquilados = listaRigsAlquilados.size();
        int cantidadPozosEnExcavacion = listaPozosEnExcavacion.size();
        if(cantidadRigsAlquilados<cantidadPozosEnExcavacion){
            for (int i = cantidadRigsAlquilados; i<cantidadPozosEnExcavacion;i++){
                RIG nuevoRig = new RIG(cantidadRigsAlquilados+1,
                        ParametrosSimulacion.RIG_BASICO_PODER_EXCAVACION,
                        10);
                simulador.rigsAlquilados.add(nuevoRig);
            }
        }
    }
}
