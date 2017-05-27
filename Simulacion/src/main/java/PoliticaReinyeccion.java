public interface PoliticaReinyeccion extends Politica {
    //FIXME: Cambiar nombre para que quede mas claro que es el booleano?
    boolean hayQueReinyectar(Simulador simulador);

    void aplicarReinyeccion(Simulador simulador);
}
