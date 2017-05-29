package simOil;

public class RIG {
    public int id;
    public int poderDeExcavacionDiario;
    //int combustibleActual; //FIXME: Manejamos en algun lado esto? No seria el costo por combustible o en el enunciado habla de esto?
    public int diasRestantesDeUso;

    public RIG(int id, int poderDeExcavacionDiario, int diasRestantesDeUso) {
        this.id = id;
        this.poderDeExcavacionDiario = poderDeExcavacionDiario;
        //this.combustibleActual = combustibleActual;
        this.diasRestantesDeUso = diasRestantesDeUso;
    }

    public void excavarPozo(PozoEnExcavacion pozoEnExcavacion) {
        int metrosAExcavarEnUnDIa = (int ) Math.floor(pozoEnExcavacion.parcela.tipoDeTerreno.porcentaje * this.poderDeExcavacionDiario );
        pozoEnExcavacion.excavar(metrosAExcavarEnUnDIa);
    }
}
