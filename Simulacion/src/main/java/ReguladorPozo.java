import java.util.List;

/**
 * Created by federico on 25/05/17.
 */
public class ReguladorPozo implements Regulador {

    private List<Pozo> pozosCompletados;
    private List<PozoEnExcavacion> pozosEnExcavacion;

    //FIXME: Se pueden pasarle pozos ya completados al regulador?
    public ReguladorPozo(List<Pozo> pozosCompletados, List<PozoEnExcavacion> pozosEnExcavacion) {
        this.pozosCompletados = pozosCompletados;
        this.pozosEnExcavacion = pozosEnExcavacion;
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
        PozoEnExcavacion pozoEnExcavacion = new PozoEnExcavacion(profundidad);
        pozosEnExcavacion.add(pozoEnExcavacion);
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

    public void avanzarDiaExcavacion() {}
}
