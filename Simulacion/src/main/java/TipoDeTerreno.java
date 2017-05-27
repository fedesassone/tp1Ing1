public enum TipoDeTerreno {
    TERRENO_ROCA(0.6), //60% de la capacidad
    TERRENO_TIERRA(0),
    TERRENO_ARCILLA(1.1); //10% mas rapido

    double porcentaje;
    TipoDeTerreno(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
