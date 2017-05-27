/**
 * Created by federico on 25/05/17.
 */
public class Parcela {
    TipoDeTerreno tipoDeTerreno;
    double presionInicial;
    int profundidadTotal;


    public Parcela(TipoDeTerreno tipoDeTerreno, double presionInicial, int profundidadTotal) {
        this.tipoDeTerreno = tipoDeTerreno;
        this.presionInicial = presionInicial;
        this.profundidadTotal = profundidadTotal;
    }
}
