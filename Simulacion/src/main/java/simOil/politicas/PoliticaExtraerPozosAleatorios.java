package simOil.politicas;

import simOil.Pozo;
import simOil.Simulador;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PoliticaExtraerPozosAleatorios extends PoliticaExtraccion {

    private int numeroMaximoPozosAHabilitar;

    public PoliticaExtraerPozosAleatorios(int numeroMaximoPozosAHabilitar) {
        this.numeroMaximoPozosAHabilitar = numeroMaximoPozosAHabilitar;
    }

    int numeroPozosAHabilitar(Simulador unSimulador){
        return Math.min(unSimulador.reguladorPozo.damePozosCompletados().size(), numeroMaximoPozosAHabilitar);
    }

    Optional<Pozo> siguiente(List<Pozo> pozosNoAbiertosTodavia, Simulador unSimulador){
        int numeroPozosUsados = unSimulador.reguladorPozo.damePozosCompletados().size() -
                pozosNoAbiertosTodavia.size();
        if(pozosNoAbiertosTodavia.isEmpty() || numeroPozosUsados >= numeroMaximoPozosAHabilitar)
            return Optional.empty();
        else {
            int numeroDePozo = new Random().nextInt(pozosNoAbiertosTodavia.size());
            return Optional.of(pozosNoAbiertosTodavia.get(numeroDePozo));
        }
    }

    double volumenAExtraer(double unaCapacidadExtraccionTotal, double unaCapacidadExtraccion){
        return Math.min(unaCapacidadExtraccionTotal, unaCapacidadExtraccion);
    }

}
