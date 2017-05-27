import java.util.List;

/**
 * Created by federico on 25/05/17.
 */
public class ReguladorPozo implements Regulador {

    private List<Pozo> pozosCompletados;
    private List<PozoEnExcavacion> pozosEnExcavacion;

    public ReguladorPozo(List<Pozo> pozosCompletados, List<PozoEnExcavacion> pozosEnExcavacion) {
        this.pozosCompletados = pozosCompletados;
        this.pozosEnExcavacion = pozosEnExcavacion;
    }

    public List<Pozo> damePozosCompletados() {
        return pozosCompletados;
    }

    public List<PozoEnExcavacion> damePozosEnExcavacion() {
        return pozosEnExcavacion;
    }

    public void excavarPozo(Parcela parcela) {
        int profundidad = parcela.profundidadTotal;
        PozoEnExcavacion pozoEnExcavacion = new PozoEnExcavacion(profundidad);
        pozosEnExcavacion.add(pozoEnExcavacion);
    }

    public void reinyectar () {}

    public double capacidadDeExtraccionTotal(double alphaUno, double alphaDos, int pozosHabilitados) {
        double capacidadTotal = 0;
        for (Pozo pozo: pozosCompletados) {
            capacidadTotal += pozo.potencialDeVolumenDiario(alphaUno, alphaDos, pozosHabilitados);
        }
        return capacidadTotal;
    }

    public void avanzarDiaExcavacion() {}
}
