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
        this(  new ReguladorTanque(TipoDeProducto.AGUA),
                new ReguladorTanque(TipoDeProducto.GAS),
                new ReguladorPozo(new LinkedList<Pozo>(), new LinkedList<PozoEnExcavacion>()),
                new ReguladorPlantaSeparadora(),
                reservorio,logger);
    }

    public Simulador(ReguladorTanque reguladorTanqueAgua, ReguladorTanque reguladorTanqueGas,
                     ReguladorPozo reguladorPozo, ReguladorPlantaSeparadora reguladorPlantaSeparadora,
                     Reservorio reservorio, Logger logger){
        this(  reguladorTanqueAgua,
                reguladorTanqueGas,
                reguladorPozo,
                reguladorPlantaSeparadora,
                new PoliticaSiempreTenerUnRIG(),
                new PoliticaExcavarPorMenorTiempoRequerido(),
                new PoliticaComprarTanquesADemanda(logger),
                new PoliticaComprarPlantasADemanda(logger),
                new PoliticaNoVenderGas(),
                new PoliticaReinyectarPorPresionCritica(new ParametrosSimulacion().presionCriticaPozos, new CalculadorPresionPorReinyeccionImpl()),
                new PoliticaExtraerPozosAleatorios(new ParametrosSimulacion().numeroMaximaPozosAAbrirPorDia),
                new PoliticaFinalizarPorDilucionCritica(new ParametrosSimulacion().dilucionCriticaPetroleo),
                reservorio,logger);
    }

    public Simulador(ReguladorTanque reguladorTanqueAgua, ReguladorTanque reguladorTanqueGas, ReguladorPozo reguladorPozo, ReguladorPlantaSeparadora reguladorPlantaSeparadora,
                     PoliticaCompraDeRIGs politicaCompraDeRIGs, PoliticaExcavacion politicaExcavacion, PoliticaCompraDeTanques politicaCompraDeTanques,
                     PoliticaCompraDePlantas politicaCompraDePlantas, PoliticaVentaDeGas politicaVentaDeGas, PoliticaReinyeccion politicaReinyeccion,
                     PoliticaExtraccion politicaExtraccion, PoliticaFinalizacion politicaFinalizacion,
                     Reservorio reservorio, Logger logger) {
        this.reguladorTanqueGas = reguladorTanqueGas;
        this.reguladorTanqueAgua = reguladorTanqueAgua;
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

    public boolean simularUnNuevoDia(){
        avanzarDiaDeConstrucciones();
        //TODO: Politica compra de RIGS
        //TODO: Politica compra de excavacion
        //TODO: Politica compra de tanques
        //TODO: Politica compra de plantas
        //TODO: Politica venta de gas
        if(politicaReinyeccion.hayQueReinyectar(this)){
            politicaReinyeccion.aplicarReinyeccion(this);
        } else {
            politicaExtraccion.aplicarExtraccion(this);
        }
        return politicaFinalizacion.hayQueFinalizarSimulacion(this);
    }

    public void avanzarDiaDeConstrucciones(){
        reguladorPlantaSeparadora.avanzarDiaConstrucciones();
        reguladorTanqueAgua.avanzarDiaConstrucciones();
        reguladorTanqueGas.avanzarDiaConstrucciones();
    }

    //FIXME: Queda medio raro, se puede diseñar mejor? Con lo corta que es a lo mejor se puede dejar
    public void comprarAgua(double unVolumen){
        logger.loguear("Se compro " + unVolumen + "cm3 de agua");
    }

}
