package simOil;

import simOil.calculadores.CalculadorPresionPorReinyeccion;

public class Parcela {
    public TipoDeTerreno tipoDeTerreno;
    private double presionInicial;
    public double presionActual;
    public int profundidadTotal;
    private CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion;

    public Parcela(TipoDeTerreno tipoDeTerreno, double presionInicial, int profundidadTotal, CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion) {
        this.tipoDeTerreno = tipoDeTerreno;
        this.presionInicial = presionInicial;
        this.presionActual = presionInicial;
        this.profundidadTotal = profundidadTotal;
        this.calculadorPresionPorReinyeccion = calculadorPresionPorReinyeccion;
    }

    public void actualizarPresionPorReinyeccion(double volumenInicial, double volumenAntesReinyeccion,
                                                double volumenReinyectado){
        assert(volumenAntesReinyeccion + volumenReinyectado <= volumenInicial);
        this.presionActual = calculadorPresionPorReinyeccion.nuevaPresionAnteReinyeccion(
                volumenAntesReinyeccion, volumenReinyectado, volumenInicial, this.presionInicial);

    }
}
