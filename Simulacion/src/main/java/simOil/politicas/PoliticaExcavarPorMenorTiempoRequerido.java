package simOil.politicas;

import simOil.*;
import simOil.logger.Logger;

import java.util.LinkedList;
import java.util.List;

public class PoliticaExcavarPorMenorTiempoRequerido implements PoliticaExcavacion {
    //esto excava los pozos que usen menos tiempo de excavacion
    public void aplicarPolitica(Simulador unSimulador){
        Logger log = unSimulador.logger;


        List<PozoEnExcavacion> pozosEnExcav= unSimulador.reguladorPozo.damePozosEnExcavacion();
        List<PozoEnExcavacion> pozosLuegoDeExcavar = new LinkedList<PozoEnExcavacion>();

        List<RIG> rigsDisponibles = unSimulador.rigsAlquilados;
        List<RIG> rigsDisponiblesCopia = rigsDisponibles;

        int n = Math.min(pozosEnExcav.size(), rigsDisponibles.size());
        int i = 0;

        //parcelaAConstruccion(unSimulador);
        //unSimulador.parcelasNoExcavadas
        while (i<n){
            int indicePozoAExcavar = proximoPozo(pozosEnExcav);
            //int indiceParcelaAExcavar = parcelaMinima(unSimulador.parcelasExcavacionEmpezada);
            assert(indicePozoAExcavar!=-1);//esto no deberia pasar nunca

            PozoEnExcavacion pozoAExcavar = pozosEnExcav.get(indicePozoAExcavar);
            pozosEnExcav.remove(indicePozoAExcavar);

            RIG rigAUsar = rigsDisponibles.get(0);
            rigsDisponibles.remove(0);

            rigAUsar.excavarPozo(pozoAExcavar);

            log.loguear("Se excavó un pozo y ahora le restan " + pozoAExcavar.profundidadRestante() + " metros.");

            if(pozoAExcavar.profundidadRestante()==0){//llego a cero, entonces es un pozo en uso
                unSimulador.reguladorPozo.nuevoPozoCompletado(pozoAExcavar);

            }else{//si sigue necesitando que se excarve
                pozosLuegoDeExcavar.add(pozoAExcavar);
            }

            i++;
        }

        while(!pozosEnExcav.isEmpty()){
            PozoEnExcavacion aux = pozosEnExcav.get(0);
            pozosEnExcav.remove(0);
            pozosLuegoDeExcavar.add(aux);
        }

        unSimulador.reguladorPozo.actualizarPozosEnExacavacion(pozosLuegoDeExcavar );
        unSimulador.rigsAlquilados = rigsDisponiblesCopia; //restauro los RIGS para usarlo el proximo dia

    }

    public int proximoPozo(List<PozoEnExcavacion> listaDePozos){
        if (listaDePozos.size()==0){
            return -1;
        }else{
            PozoEnExcavacion pozoMinimo = listaDePozos.get(0);
            for(PozoEnExcavacion pozo:listaDePozos){
                if(pozo.profundidadRestante() < pozoMinimo.profundidadRestante()){//tengo nuevo minimo
                    pozoMinimo = pozo;
                }
            }
            return  listaDePozos.indexOf(pozoMinimo);
        }
    }




}
