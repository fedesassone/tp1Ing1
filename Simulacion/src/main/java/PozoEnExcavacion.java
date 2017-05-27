/**
 * Created by federico on 25/05/17.
 */
public class PozoEnExcavacion {
    Parcela parcela;
    int profundidadRestante;

    public PozoEnExcavacion(int profundidadRestante) {
        this.profundidadRestante = profundidadRestante;
    }

    public void excavar(int profundidad) {
        if(this.profundidadRestante<= profundidad) {
            this.profundidadRestante = 0;
        } else {
            this.profundidadRestante = profundidadRestante - profundidad;
        }
        // el de afuera tendria que chequear esto y crear el nuevo pozo
    }
}
