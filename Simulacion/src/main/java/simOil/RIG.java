package simOil;

public class RIG {
    public int id;
    public int poderDeExcavacionDiario;
    public int diasRestantesDeUso;

    public RIG(int id, int poderDeExcavacionDiario, int diasRestantesDeUso) {
        assert(diasRestantesDeUso >= ParametrosSimulacion.minimoDiaAlquilerDeRigs);
        this.id = id;
        this.poderDeExcavacionDiario = poderDeExcavacionDiario;
        this.diasRestantesDeUso = diasRestantesDeUso;
    }

    public void excavarPozo(PozoEnExcavacion pozoEnExcavacion) {
        int metrosAExcavarEnUnDIa = (int ) Math.floor(pozoEnExcavacion.parcela.tipoDeTerreno.porcentaje * this.poderDeExcavacionDiario );
        pozoEnExcavacion.excavar(metrosAExcavarEnUnDIa);
    }
}
