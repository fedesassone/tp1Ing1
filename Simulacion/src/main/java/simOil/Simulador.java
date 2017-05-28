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
    private Logger logger;
    //private PoliticaCompraPlantas politicaCompraPlantas;

    //Objetos del yacimiento
    public Reservorio reservorio;
    public List<Parcela> parcelasNoExcavadas;
    public List<Parcela> parcelasExcavacionEmpezada;
    public List<RIG> rigsAlquilados;

    //Constructor con simOil.politicas y simOil.simOil.reguladores por defecto
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

    //FIXME: Queda medio raro que devuelva un booleano y que tenga ese nombre la funcion...
    public boolean simularUnNuevoDia(){
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
