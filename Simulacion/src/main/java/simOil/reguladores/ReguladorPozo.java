package simOil.reguladores;

import simOil.Parcela;
import simOil.Pozo;
import simOil.PozoEnExcavacion;
import simOil.logger.Logger;

import java.util.LinkedList;
import java.util.List;

public class ReguladorPozo implements Regulador {
    private int ultimoPozoAgregado;
    private List<Pozo> pozosCompletados;
    private List<PozoEnExcavacion> pozosEnExcavacion;
    Logger logger;

    //FIXME: Se pueden pasarle pozos ya completados al regulador?
    public ReguladorPozo(List<Pozo> pozosCompletados, List<PozoEnExcavacion> pozosEnExcavacion, Logger logger) {
        this.pozosCompletados = pozosCompletados;
        this.pozosEnExcavacion = pozosEnExcavacion;
        this.logger = logger;
    }

    public ReguladorPozo(Logger logger){
        this.ultimoPozoAgregado = 0;
        this.pozosCompletados = new LinkedList<Pozo>();
        this.pozosEnExcavacion = new LinkedList<PozoEnExcavacion>();
        this.logger = logger;
    }

    public List<Pozo> damePozosCompletados() {
        return pozosCompletados;
    }
    public int cantidadPozosCompletados(){
        return pozosCompletados.size();
    }


    public List<PozoEnExcavacion> damePozosEnExcavacion() {
        return pozosEnExcavacion;
    }

    public void excavarPozo(Parcela parcela) {
        int profundidad = parcela.profundidadTotal;
        PozoEnExcavacion pozoEnExcavacion = new PozoEnExcavacion(profundidad, parcela);
        pozosEnExcavacion.add(pozoEnExcavacion);
    }

    public void nuevoPozoCompletado(PozoEnExcavacion pozoExcavado){
        Parcela parcelaAsignada = pozoExcavado.parcela();

        Pozo nuevoPozo = new Pozo(ultimoPozoAgregado, parcelaAsignada.presionInicialBocaDePozo(),parcelaAsignada.calculadorDePresionPorReinyeccion(), logger);
        this.pozosCompletados.add(nuevoPozo);
        ultimoPozoAgregado++;
    }
    public void actualizarPresionPozosPorReinyeccion(double volumenInicial, double volumenAntesReinyeccion,
                                                     double volumenReinyectado) {
        for (Pozo pozo: pozosCompletados){
            pozo.actualizarPresionPorReinyeccion(volumenInicial, volumenAntesReinyeccion, volumenReinyectado);
        }
    }

    public double capacidadDeExtraccionTotal(int pozosHabilitados) {
        double capacidadTotal = 0;
        for (Pozo pozo: pozosCompletados) {
            capacidadTotal += pozo.potencialDeVolumenDiario(pozosHabilitados);
        }
        return capacidadTotal;
    }

    public void actualizarPozosEnExacavacion(List<PozoEnExcavacion> listaDePozos){

        this.pozosEnExcavacion = listaDePozos;
    }

    //FIXME: Es necesaria? La excavacion no es por dia, depende de los RIGs que se tiene
    public void avanzarDiaExcavacion() {
        //TODO: Completar
    }
}
