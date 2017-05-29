package simOil;

import simOil.calculadores.CalculadorPresionPorReinyeccion;
import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.Logger;
import simOil.politicas.*;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Simulador {

    //Reguladores
    public ReguladorTanque reguladorTanqueGas;
    public ReguladorTanque reguladorTanqueAgua;
    public ReguladorPozo reguladorPozo;
    public ReguladorPlantaSeparadora reguladorPlantaSeparadora;

    //Politicas
    private PoliticaAlquilerDeRIGs politicaAlquilerDeRIGs;
    private PoliticaExcavacion politicaExcavacion;
    private PoliticaCompraDeTanques politicaCompraDeTanques;
    private PoliticaCompraDePlantas politicaCompraDePlantas;
    private PoliticaVentaDeGas politicaVentaDeGas;
    private PoliticaReinyeccion politicaReinyeccion;
    private PoliticaExtraccion politicaExtraccion;
    private PoliticaFinalizacion politicaFinalizacion;

    //Logger
    public Logger logger;

    //Llevador de Costos y Beneficios
    public int costoTotal; //FIXME: Se toma en cuenta costo de RIGS (combustible)?
    public int gananciaTotal;

    public double totalAguaReinyectada;
    public double totalGasReinyectada;

    public double totalPetroleoVendido;
    public double totalGasVendido;


    //Objetos del yacimiento
    public int numeroDeDia;
    public Reservorio reservorio;
    public List<Parcela> parcelasNoExcavadas;
    public List<RIG> rigsAlquilados;

    //Constructor con simOil.politicas y simOil.simOil.reguladores por defecto
    public Simulador(Reservorio reservorio, Logger logger){
        this(  new ReguladorTanque(TipoDeProducto.AGUA, logger),
                new ReguladorTanque(TipoDeProducto.GAS, logger),
                new ReguladorPozo(logger),
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
                new PoliticaComprarTanquesADemanda(),
                new PoliticaComprarPlantasADemanda(),
                new PoliticaVenderPorcentajeGas(new ParametrosSimulacion().porcentajeVentaDiarioGas),
                new PoliticaReinyectarPorPresionCritica(new ParametrosSimulacion().presionCriticaPozos, new CalculadorPresionPorReinyeccionImpl()),
                new PoliticaExtraerPozosAleatorios(new ParametrosSimulacion().numeroMaximaPozosAAbrirPorDia),
                new PoliticaFinalizarPorDilucionCritica(new ParametrosSimulacion().dilucionCriticaPetroleo),
                reservorio,logger);
    }

    public Simulador(ReguladorTanque reguladorTanqueAgua, ReguladorTanque reguladorTanqueGas, ReguladorPozo reguladorPozo, ReguladorPlantaSeparadora reguladorPlantaSeparadora,
                     PoliticaAlquilerDeRIGs politicaAlquilerDeRIGs, PoliticaExcavacion politicaExcavacion, PoliticaCompraDeTanques politicaCompraDeTanques,
                     PoliticaCompraDePlantas politicaCompraDePlantas, PoliticaVentaDeGas politicaVentaDeGas, PoliticaReinyeccion politicaReinyeccion,
                     PoliticaExtraccion politicaExtraccion, PoliticaFinalizacion politicaFinalizacion,
                     Reservorio reservorio, Logger logger) {
        //Reguladores
        this.reguladorTanqueGas = reguladorTanqueGas;
        this.reguladorTanqueAgua = reguladorTanqueAgua;
        this.reguladorPozo = reguladorPozo;
        this.reguladorPlantaSeparadora = reguladorPlantaSeparadora;

        //Politicas
        this.politicaAlquilerDeRIGs = politicaAlquilerDeRIGs;
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
        this.rigsAlquilados = new LinkedList<RIG>();

        //Logger
        this.logger = logger;
    }

    public void simularUnNuevoDia(){
        logger.loguear("Comenzo simulacion del dia numero " + numeroDeDia);
        numeroDeDia ++;
        politicaAlquilerDeRIGs.aplicarPolitica(this);
        politicaExcavacion.aplicarPolitica(this);
        politicaCompraDePlantas.aplicarPolitica(this);
        politicaCompraDeTanques.aplicarPolitica(this);
        politicaVentaDeGas.realizarVentaDeGas(this);
        if(politicaReinyeccion.hayQueReinyectar(this)){
            politicaReinyeccion.realizarReinyeccion(this);
        } else {
            politicaExtraccion.realizarExtracciones(this);
        }
        avanzarDiaDeAlquileres();
        avanzarDiaDeConstrucciones();
    }

    public boolean hayQueFinalizarSimulacion(){
        return politicaFinalizacion.hayQueFinalizarSimulacion(this);
    }

    private void avanzarDiaDeAlquileres(){
        Iterator<RIG> rigsAlquiladosIt = rigsAlquilados.iterator();
        while(rigsAlquiladosIt.hasNext()){
            RIG rigAlquilado = rigsAlquiladosIt.next();
            rigAlquilado.diasRestantesDeUso --;
            if(rigAlquilado.diasRestantesDeUso == 0){
                rigsAlquiladosIt.remove();
            }
        }
    }

    private void avanzarDiaDeConstrucciones(){
        reguladorPlantaSeparadora.avanzarDiaConstrucciones();
        reguladorTanqueAgua.avanzarDiaConstrucciones();
        reguladorTanqueGas.avanzarDiaConstrucciones();
    }

    public void comprarAgua(double unVolumenAComprar){
        logger.loguear("Se compro " + unVolumenAComprar + "cm3 de agua");
    }

    public void venderGas(double unVolumenAVender) {
        double gananciaVenta = unVolumenAVender * new ParametrosSimulacion().gananciaPorCm3VendidoGas;
        gananciaTotal += gananciaVenta;
        totalGasVendido += unVolumenAVender;
        logger.loguear("Se vendio " + unVolumenAVender + "cm3 de gas por " + gananciaVenta);
    }

    public void venderPetroleo(double unVolumenAVender) {
        double gananciaVenta = unVolumenAVender * new ParametrosSimulacion().gananciaPorCm3VendidoPetroleo;
        gananciaTotal += gananciaVenta;
        totalPetroleoVendido += unVolumenAVender;
        logger.loguear("Se vendio " + unVolumenAVender + "cm3 de petroleo por " + gananciaVenta);
    }

    CalculadorPresionPorReinyeccion calculadorPresionPorReinyeccion = new CalculadorPresionPorReinyeccionImpl();

    public void generarParcelasIniciales(){
        int cantidadParcelas = this.reservorio.area / 10000;
        for (int i = 0; i<cantidadParcelas; i++) {
            Parcela nuevaParcela = new Parcela(TipoDeTerreno.TERRENO_TIERRA,
            ParametrosSimulacion.presionInicialParcelas,
            ParametrosSimulacion.profundidadTotalParcelas, calculadorPresionPorReinyeccion);
            this.parcelasNoExcavadas.add(nuevaParcela);
        }
        logger.loguear("Se generaron " + cantidadParcelas + " parcelas.");
    }

}
