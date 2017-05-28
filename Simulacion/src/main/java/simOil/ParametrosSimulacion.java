package simOil;

import java.util.List;

//FIXME: Obtener parametros de un archivo?
public final class ParametrosSimulacion {

    //Constantes sobre el reservorio
    public static double proporcionAgua = 0.3;
    public static double proporcionGas = 0.3;
    public static double proporcionPetroleo = 0.4;
    public static double volumenInicial = 10000000; //Valores tipicos: [10000000..1000000000]
    public static int areaReservorio = 10000000;

    //Constantes sobre parcelas
    public static double presionInicialParcelas = 100;
    public static int profundidadTotalParcelas = 500;

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
    //Por politica seleccion de parcelas
    public static int numeroDePozosAConstruir = 10;
    //Por politica extraccion
    public static int numeroMaximaPozosAAbrirPorDia = 10;
    //Por politica reinyeccion
    public static double presionCriticaPozos = 10;
    //Por politica finalizacion
    public static double dilucionCriticaPetroleo = 0.35; //Valor tipico: 0.35
    //Por politica venta gas
    public static double porcentajeVentaDiarioGas = 0.3;

    //Parametros Rigs
    public static int RIG_BASICO_PODER_EXCAVACION = 100;
    public static int RIG_BASICO_COSTO = 100;

    public static int RIG_AVANZADO_PODER_EXCAVACION = 200;
    public static int RIG_AVANZADO_COSTO = 180;

}
