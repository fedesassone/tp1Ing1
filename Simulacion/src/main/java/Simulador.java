import java.util.LinkedList;
import java.util.List;

public class Simulador {

    //Reguladores
    ReguladorTanque reguladorTanqueGas;
    ReguladorTanque reguladorTanqueAgua;
    ReguladorPozo reguladorPozo;
    ReguladorPlantaSeparadora reguladorPlantaSeparadora;

    //Politicas
    private PoliticaCompraDeRIGs politicaCompraDeRIGs;
    private PoliticaExcavacion politicaExcavacion;
    private PoliticaConstruccionDeTanques politicaConstruccionDeTanques;
    private PoliticaConstruccionDePlantas politicaConstruccionDePlantas;
    private PoliticaVentaDeGas politicaVentaDeGas;
    private PoliticaReinyeccion politicaReinyeccion;
    private PoliticaExtraccion politicaExtraccion;
    private PoliticaFinalizacion politicaFinalizacion;

    //Objetos del yacimiento
    Reservorio reservorio;
    List<Parcela> parcelasNoExcavadas;
    List<Parcela> parcelasExcavacionEmpezada;
    List<RIG> rigsAlquilados;

    //Constructor con politicas y reguladores por defecto
    public Simulador(Reservorio reservorio){
        this(  new ReguladorTanque(),
                new ReguladorTanque(),
                new ReguladorPozo(new LinkedList<Pozo>(), new LinkedList<PozoEnExcavacion>()),
                new ReguladorPlantaSeparadora(),
                new PoliticaSiempreTenerUnRIG(),
                new PoliticaExcavarPorMenorTiempoRequerido(),
                new PoliticaConstruirTanquesADemanda(),
                new PoliticaConstruirPlantasADemanda(),
                new PoliticaNoVenderGas(),
                new PoliticaReinyectarTodoPorPresionCritica(),
                new PoliticaExtraerPozosAleatorios(new ParametrosSimulacion().numeroMaximaPozosAAbrirPorDia),
                new PoliticaFinalizarPorDilucionCritica(),
                reservorio);
    }

    public Simulador(ReguladorTanque reguladorTanqueGas, ReguladorTanque reguladorTanqueAgua, ReguladorPozo reguladorPozo, ReguladorPlantaSeparadora reguladorPlantaSeparadora,
                     PoliticaCompraDeRIGs politicaCompraDeRIGs, PoliticaExcavacion politicaExcavacion, PoliticaConstruccionDeTanques politicaConstruccionDeTanques,
                     PoliticaConstruccionDePlantas politicaConstruccionDePlantas, PoliticaVentaDeGas politicaVentaDeGas, PoliticaReinyeccion politicaReinyeccion,
                     PoliticaExtraccion politicaExtraccion, PoliticaFinalizacion politicaFinalizacion,
                     Reservorio reservorio) {
        this.reguladorTanqueGas = reguladorTanqueGas;
        this.reguladorTanqueAgua = reguladorTanqueAgua;
        this.reguladorPozo = reguladorPozo;
        this.reguladorPlantaSeparadora = reguladorPlantaSeparadora;
        this.politicaCompraDeRIGs = politicaCompraDeRIGs;
        this.politicaExcavacion = politicaExcavacion;
        this.politicaConstruccionDeTanques = politicaConstruccionDeTanques;
        this.politicaConstruccionDePlantas = politicaConstruccionDePlantas;
        this.politicaVentaDeGas = politicaVentaDeGas;
        this.politicaReinyeccion = politicaReinyeccion;
        this.politicaExtraccion = politicaExtraccion;
        this.politicaFinalizacion = politicaFinalizacion;
        this.reservorio = reservorio;
        this.parcelasNoExcavadas = new LinkedList<Parcela>();
        this.parcelasExcavacionEmpezada = new LinkedList<Parcela>();
        this.rigsAlquilados = new LinkedList<RIG>();
    }

    public void simularUnNuevoDia(){
        //TODO: Completar
    }

    public void avanzarDiaDeConstrucciones(){
        reguladorPlantaSeparadora.avanzarDiaConstrucciones();
        reguladorTanqueAgua.avanzarDiaConstrucciones();
        reguladorTanqueGas.avanzarDiaConstrucciones();
    }

}
