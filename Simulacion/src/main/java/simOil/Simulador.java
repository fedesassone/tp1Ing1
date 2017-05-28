package simOil;

import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.Logger;
import simOil.politicas.*;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.LinkedList;
import java.util.List;

public class Simulador {

    //Reguladores
    public ReguladorTanque reguladorTanqueGas;
    public ReguladorTanque reguladorTanqueAgua;
    public ReguladorPozo reguladorPozo;
    public ReguladorPlantaSeparadora reguladorPlantaSeparadora;

    //Politicas
    private PoliticaCompraDeRIGs politicaCompraDeRIGs;
    private PoliticaExcavacion politicaExcavacion;
    private PoliticaCompraDeTanques politicaCompraDeTanques;
    private PoliticaCompraDePlantas politicaCompraDePlantas;
    private PoliticaVentaDeGas politicaVentaDeGas;
    private PoliticaReinyeccion politicaReinyeccion;
    private PoliticaExtraccion politicaExtraccion;
    private PoliticaFinalizacion politicaFinalizacion;

    //Logger
    private Logger logger;

    //Objetos del yacimiento
    public int numeroDeDia;
    public Reservorio reservorio;
    public List<Parcela> parcelasNoExcavadas;
    public List<Parcela> parcelasExcavacionEmpezada;
    public List<RIG> rigsAlquilados;

    //Constructor con simOil.politicas y simOil.simOil.reguladores por defecto
    public Simulador(Reservorio reservorio, Logger logger){
        this(  new ReguladorTanque(TipoDeProducto.AGUA, logger),
                new ReguladorTanque(TipoDeProducto.GAS, logger),
                new ReguladorPozo(new LinkedList<Pozo>(), new LinkedList<PozoEnExcavacion>()),
                new ReguladorPlantaSeparadora(logger),
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
                new PoliticaVenderPorcentajeGas(new ParametrosSimulacion().porcentajeVentaDiarioGas),
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
        //Reguladores
        this.reguladorTanqueGas = reguladorTanqueGas;
        this.reguladorTanqueAgua = reguladorTanqueAgua;
        this.reguladorPozo = reguladorPozo;
        this.reguladorPlantaSeparadora = reguladorPlantaSeparadora;

        //Politicas
        this.politicaCompraDeRIGs = politicaCompraDeRIGs;
        this.politicaExcavacion = politicaExcavacion;
        this.politicaCompraDeTanques = politicaCompraDeTanques;
        this.politicaCompraDePlantas = politicaCompraDePlantas;
        this.politicaVentaDeGas = politicaVentaDeGas;
        this.politicaReinyeccion = politicaReinyeccion;
        this.politicaExtraccion = politicaExtraccion;
        this.politicaFinalizacion = politicaFinalizacion;

        //Objetos yacimiento
        this.numeroDeDia = 0;
        this.reservorio = reservorio;
        this.parcelasNoExcavadas = new LinkedList<Parcela>();
        this.parcelasExcavacionEmpezada = new LinkedList<Parcela>();
        this.rigsAlquilados = new LinkedList<RIG>();

        //Logger
        this.logger = logger;
    }

    //FIXME: Queda medio raro que devuelva un booleano y que tenga ese nombre la funcion...
    //       A lo mejor dividir esta en simularUnNuevo dia y finalizar ejecucion?
    public boolean simularUnNuevoDia(){
        logger.loguear("Comenzo simulacion del dia numero " + numeroDeDia);
        numeroDeDia ++;
        avanzarDiaDeConstrucciones();
        //TODO: simOil.Politica compra de RIGS
        //TODO: simOil.Politica compra de excavacion
        //TODO: simOil.Politica compra de tanques
        //TODO: simOil.Politica compra de plantas
        politicaVentaDeGas.realizarVentaDeGas(this);
        if(politicaReinyeccion.hayQueReinyectar(this)){
            politicaReinyeccion.realizarReinyeccion(this);
        } else {
            politicaExtraccion.realizarExtracciones(this);
        }
        return politicaFinalizacion.hayQueFinalizarSimulacion(this);
    }

    public void avanzarDiaDeConstrucciones(){
        reguladorPlantaSeparadora.avanzarDiaConstrucciones();
        reguladorTanqueAgua.avanzarDiaConstrucciones();
        reguladorTanqueGas.avanzarDiaConstrucciones();
    }

    //FIXME: Queda medio raro, se puede diseñar mejor? Con lo corta que es a lo mejor se puede dejar
    public void comprarAgua(double unVolumenAComprar){
        logger.loguear("Se compro " + unVolumenAComprar + "cm3 de agua");
    }

    public void venderGas(double unVolumenAVender) {
        logger.loguear("Se vendio " + unVolumenAVender + "cm3 de gas");
    }

    public void venderPetroleo(double unVolumenAVender) {
        logger.loguear("Se vendio " + unVolumenAVender + "cm3 de petroleo");
    }

}
