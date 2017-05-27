public class Parcela {
    TipoDeTerreno tipoDeTerreno;
    double presionInicial;
    int profundidadTotal;
    CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion;

    public Parcela(TipoDeTerreno tipoDeTerreno, double presionInicial, int profundidadTotal, CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion) {
        this.tipoDeTerreno = tipoDeTerreno;
        this.presionInicial = presionInicial;
        this.profundidadTotal = profundidadTotal;
        this.calculadorPresionPorReinyeccion = calculadorPresionPorReinyeccion;
    }

    public void actualizarPresionPorReinyeccion(double volumenInicial, double volumenAntesReinyeccion,
                                                double volumenReinyectado){
        assert(volumenAntesReinyeccion + volumenReinyectado <= volumenInicial);
        this.presionInicial = calculadorPresionPorReinyeccion.nuevaPresionAnteReinyeccion(
                volumenAntesReinyeccion, volumenReinyectado, volumenInicial, this.presionInicial);

    }
}
