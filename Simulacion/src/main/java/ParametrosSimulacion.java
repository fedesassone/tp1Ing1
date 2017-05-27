//FIXME: Obtener parametros de un archivo?
public final class ParametrosSimulacion {

    //Constantes sobre el reservorio
    public static double proporcionAgua = 0.3;
    public static double proporcionGas = 0.3;
    public static double proporcionPetroleo = 0.4;
    public static double volumenInicial = 10000000; //Valores tipicos: [10000000..1000000000]

    //Constantes sobre pozos
    public static double alpha1 = 0.1; //Valores tipicos: [0.1..0.6]
    public static double alpha2 = 0.01; //Valores tipicos: [0.005..0.01]

    //Constantes sobre plantas separadoras
    public static int capacidadNuevasPlantas = 1000;
    public static int diasConstruccionPlantas = 10;

    //Constantes sobre tanques
    public static int capacidadNuevosTanques = 1000;
    public static int diasConstruccionTanques = 10;

    //Constantes sobre politicas
    public static int numeroMaximaPozosAAbrirPorDia = 10;
    public static double dilucionCriticaPetroleo = 0.35; //Valor tipico: 0.35

    //Parametros Rigs
    public static int RIG_BASICO_PODER_EXCAVACION = 100;
    public static int RIG_BASICO_COSTO = 100;
    public static int COMBUSTIBLE_COSTO = 100;

}
