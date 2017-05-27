import java.util.LinkedList;
import java.util.List;

public class Simulador {

    //Reguladores
    ReguladorTanque reguladorTanque;
    ReguladorPozo reguladorPozo;
    ReguladorPlantaSeparadora reguladorPlantaSeparadora;

    //Politicas
    private PoliticaCompraDeRIGs politicaCompraDeRIGs;
    private PoliticaExcavacion politicaExcavacion;
    private PoliticaCompraDeTanques politicaCompraDeTanques;
    private PoliticaCompraDePlantas politicaCompraDePlantas;
    private PoliticaVentaDeGas politicaVentaDeGas;
    private PoliticaReinyeccion politicaReinyeccion;
    private PoliticaExtraccion politicaExtraccion;
    private PoliticaFinalizacion politicaFinalizacion;
    private  Logger logger;
    //private PoliticaCompraPlantas politicaCompraPlantas;

    //Objetos del yacimiento
    Reservorio reservorio;
    List<Parcela> parcelasNoExcavadas;
    List<Parcela> parcelasExcavacionEmpezada;
    List<RIG> rigsAlquilados;

    //Constructor con politicas y reguladores por defecto
    public Simulador(Reservorio reservorio, Logger logger){
        //Logger logger = new Logger();
        this(  new ReguladorTanque(), new ReguladorPozo(new LinkedList<Pozo>(), new LinkedList<PozoEnExcavacion>()), new ReguladorPlantaSeparadora(),
                new PoliticaSiempreTenerUnRIG(), new PoliticaExcavarPorMenorTiempoRequerido(), new PoliticaComprarTanquesADemanda(),
                new PoliticaComprarPlantasADemanda(logger), new PoliticaNoVenderGas(), new PoliticaReinyectarTodoPorPresionCritica(),
                new PoliticaExtraerPozosAleatorios(), new PoliticaFinalizarPorDilucionCritica(), reservorio, logger);
    }

    public Simulador(ReguladorTanque reguladorTanque, ReguladorPozo reguladorPozo, ReguladorPlantaSeparadora reguladorPlantaSeparadora,
                     PoliticaCompraDeRIGs politicaCompraDeRIGs, PoliticaExcavacion politicaExcavacion, PoliticaCompraDeTanques politicaCompraDeTanques,
                     PoliticaCompraDePlantas politicaCompraDePlantas, PoliticaVentaDeGas politicaVentaDeGas, PoliticaReinyeccion politicaReinyeccion,
                     PoliticaExtraccion politicaExtraccion, PoliticaFinalizacion politicaFinalizacion,
                     Reservorio reservorio, Logger logger) {
        this.reguladorTanque = reguladorTanque;
        this.reguladorPozo = reguladorPozo;
        this.reguladorPlantaSeparadora = reguladorPlantaSeparadora;
        this.politicaCompraDeRIGs = politicaCompraDeRIGs;
        this.politicaExcavacion = politicaExcavacion;
        this.politicaCompraDeTanques = politicaCompraDeTanques;
        this.politicaCompraDePlantas = politicaCompraDePlantas;
        this.politicaVentaDeGas = politicaVentaDeGas;
        this.politicaReinyeccion = politicaReinyeccion;
        this.politicaExtraccion = politicaExtraccion;
        this.politicaFinalizacion = politicaFinalizacion;
        this.reservorio = reservorio;
        this.parcelasNoExcavadas = new LinkedList<Parcela>();
        this.parcelasExcavacionEmpezada = new LinkedList<Parcela>();
        this.rigsAlquilados = new LinkedList<RIG>();
        this.logger = logger;
    }

    public void simularUnNuevoDia(){
        //TODO: Completar
    }

    public void avanzarDiaDeConstrucciones(){
        reguladorPlantaSeparadora.avanzarDiaConstrucciones();
        reguladorTanque.avanzarDiaConstrucciones();
    }

}
