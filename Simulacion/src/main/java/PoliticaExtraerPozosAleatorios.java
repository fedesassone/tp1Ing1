import java.util.List;
import java.util.Optional;
import java.util.Random;

//FIXME: Agregar tests
public class PoliticaExtraerPozosAleatorios extends PoliticaExtraccion {

    private int numeroMaximoPozosAHabilitar;

    public PoliticaExtraerPozosAleatorios(int numeroMaximoPozosAHabilitar) {
        this.numeroMaximoPozosAHabilitar = numeroMaximoPozosAHabilitar;
    }

    int numeroPozosAHabilitar(Simulador unSimulador){
        return Math.min(unSimulador.reguladorPozo.damePozosCompletados().size(), numeroMaximoPozosAHabilitar);
    }

    Optional<Pozo> siguiente(List<Pozo> pozosNoAbiertosTodavia, Simulador unSimulador){
        if(pozosNoAbiertosTodavia.isEmpty())
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
