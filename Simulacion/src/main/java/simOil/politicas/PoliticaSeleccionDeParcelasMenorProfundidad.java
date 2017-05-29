package simOil.politicas;

import simOil.Parcela;
import simOil.Simulador;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
  *  Politica: Se selecciona las 'numerosDePozosAConstruir' parcelas con menor profundidad
  */
public class PoliticaSeleccionDeParcelasMenorProfundidad implements PoliticaSeleccionDeParcelas {

    private int numeroDePozosAConstruir;

    public PoliticaSeleccionDeParcelasMenorProfundidad(int numeroDePozos) {
        this.numeroDePozosAConstruir = numeroDePozos;
    }

    @Override
    public void seleccionarParcelasAExcavar(Simulador simulador) {

        List<Parcela> parcelasNoExcavadas = new LinkedList<Parcela>(simulador.parcelasNoExcavadas);

        parcelasNoExcavadas.sort(new ParcelaComparator());
        List<Parcela> parcelasAExcavar = parcelasNoExcavadas.subList(0, numeroDePozosAConstruir);

        simulador.parcelasNoExcavadas.removeAll(parcelasAExcavar);

        for(Parcela parcelaAExcavar: parcelasAExcavar){
            simulador.reguladorPozo.excavarPozo(parcelaAExcavar);
        }
    }

    private class ParcelaComparator implements Comparator<Parcela> {
        @Override
        public int compare(Parcela a, Parcela b) {
            return a.profundidadTotal < b.profundidadTotal ? -1 : a.profundidadTotal == b.profundidadTotal ? 0 : 1;
        }
    }

}
