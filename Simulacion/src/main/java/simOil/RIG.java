package simOil;

public class RIG {
    int id;
    int poderDeExcavacionDiario;
    int combustibleActual;
    int diasRestantesDeUso;

    public RIG(int id, int poderDeExcavacionDiario, int combustibleActual, int diasRestantesDeUso) {
        this.id = id;
        this.poderDeExcavacionDiario = poderDeExcavacionDiario;
        this.combustibleActual = combustibleActual;
        this.diasRestantesDeUso = diasRestantesDeUso;
    }

    public void excavarPozo(PozoEnExcavacion pozoEnExcavacion) {
        int metrosAExcavarEnUnDIa = (int ) Math.floor(pozoEnExcavacion.parcela.tipoDeTerreno.porcentaje * this.poderDeExcavacionDiario );
        pozoEnExcavacion.excavar(metrosAExcavarEnUnDIa);
    }
}
