package simOil;

import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.Logger;
import simOil.logger.LoggerAConsola;
import simOil.politicas.*;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.Scanner;

class SimulacionApp {
    public static void main(String[] args) {
        ParametrosSimulacion params = new ParametrosSimulacion();

        //Politicas
        PoliticaSeleccionDeParcelas politicaSeleccionDeParcelas;
        PoliticaAlquilerDeRIGs politicaAlquilerDeRIGs;
        PoliticaExcavacion politicaExcavacion;
        PoliticaCompraDeTanques politicaCompraDeTanques;
        PoliticaCompraDePlantas politicaCompraDePlantas;
        PoliticaVentaDeGas politicaVentaDeGas;
        PoliticaReinyeccion politicaReinyeccion;
        PoliticaExtraccion politicaExtraccion;
        PoliticaFinalizacion politicaFinalizacion;

        Scanner s = new Scanner(System.in);

        System.out.println("Bienvenido");
        System.out.println("Seleccione una politica de seleccion de parcelas (Unica opcion)");
        politicaSeleccionDeParcelas = new PoliticaSeleccionDeParcelasMenorProfundidad(params.numeroDePozosAConstruir);

        System.out.println("Seleccione una politica de compra de rigs\n" +
                "\t[1] PoliticaSiempreTenerUnRIG\n" +
                "\t[2] PoliticaTenerUnRigParaCadaPozo");
        int politicaCompraDeRIGsSeleccionada = s.nextInt();
        if(politicaCompraDeRIGsSeleccionada == 1){
            politicaAlquilerDeRIGs = new PoliticaSiempreTenerUnRIG();
        } else {
            politicaAlquilerDeRIGs = new PoliticaTenerUnRigParaCadaPozo();
        }

        System.out.println("Seleccione una politica de excavacion (Unica opcion)");
        politicaExcavacion = new PoliticaExcavarPorMenorTiempoRequerido();

        System.out.println("Seleccione una politica de construccion de tanques (Unica opcion)");
        politicaCompraDeTanques = new PoliticaComprarTanquesADemanda();

        System.out.println("Seleccione una politica de construccion de plantas (Unica opcion)");
        politicaCompraDePlantas = new PoliticaComprarPlantasADemanda();

        System.out.println("Seleccione una politica de ventas\n" +
                "\t[1] PoliticaNoVenderGas\n" +
                "\t[2] PoliticaVenderPorcentajeGas");
        int politicaVentaSeleccionada = s.nextInt();
        if(politicaVentaSeleccionada == 1){
            politicaVentaDeGas = new PoliticaNoVenderGas();
        } else {
            politicaVentaDeGas = new PoliticaVenderPorcentajeGas(params.porcentajeVentaDiarioGas);
        }

        System.out.println("Seleccione una politica de reinyeccion (Unica opcion)");
        politicaReinyeccion = new PoliticaReinyectarPorPresionCritica(params.presionCriticaPozos,
                new CalculadorPresionPorReinyeccionImpl());

        System.out.println("Seleccione una politica de extraccion (Unica opcion)");
        politicaExtraccion = new PoliticaExtraerPozosAleatorios(params.numeroMaximaPozosAAbrirPorDia);

        System.out.println("Seleccione una politica de finalizacion\n" +
                "\t[1] PoliticaFinalizarPorDilucionCritica\n" +
                "\t[2] PoliticaFinalizarPorNumeroDias");
        int politicaFinalizacionSeleccionada = s.nextInt();
        if(politicaFinalizacionSeleccionada == 1){
            politicaFinalizacion = new PoliticaFinalizarPorDilucionCritica(params.dilucionCriticaPetroleo);
        }else{
            politicaFinalizacion = new PoliticaFinalizarPorNumeroDias(params.numeroTotalDiasSimulacion);
        }

        Logger logger = new LoggerAConsola();
        Reservorio reservorio = new Reservorio(
                params.proporcionGas,
                params.proporcionPetroleo,
                params.proporcionAgua,
                params.volumenInicial,
                logger,
                params.areaReservorio);
        Simulador simulador = new Simulador(
                new ReguladorTanque(TipoDeProducto.AGUA, logger),
                new ReguladorTanque(TipoDeProducto.GAS, logger),
                new ReguladorPozo(logger),
                new ReguladorPlantaSeparadora(logger),
                politicaAlquilerDeRIGs,
                politicaExcavacion,
                politicaCompraDeTanques,
                politicaCompraDePlantas,
                politicaVentaDeGas,
                politicaReinyeccion,
                politicaExtraccion,
                politicaFinalizacion,
                reservorio,
                logger);
        simulador.generarParcelasIniciales();

        politicaSeleccionDeParcelas.seleccionarParcelasAExcavar(simulador);

        while(!simulador.hayQueFinalizarSimulacion()){
            simulador.simularUnNuevoDia();
        }
        logger.loguear("Se finalizo la simulacion con los resultados:\n" +
                "\tGasto total: $" + simulador.costoTotal + "\n" +
                "\tGanancia total: $" + simulador.gananciaTotal + "\n" +
                "\tReinyeccion de agua: " + simulador.totalAguaReinyectada + "\n" +
                "\tReinyeccion de gas: " + simulador.totalGasReinyectada + "\n" +
                "\tVolumen de petroleo total vendido: " + simulador.totalPetroleoVendido + "\n" +
                "\tVolumen de gas total vendido: " + simulador.totalGasVendido + "\n"
        );
    }
}
