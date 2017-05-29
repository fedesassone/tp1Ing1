package simOil;

import simOil.calculadores.CalculadorPresionPorReinyeccionImpl;
import simOil.logger.Logger;
import simOil.logger.LoggerAArchivo;
import simOil.logger.LoggerAConsola;
import simOil.politicas.*;
import simOil.reguladores.ReguladorPlantaSeparadora;
import simOil.reguladores.ReguladorPozo;
import simOil.reguladores.ReguladorTanque;

import java.util.Scanner;

class SimulacionApp {
    //FIXME: Completar
    public static void main(String[] args) {
        ParametrosSimulacion params = new ParametrosSimulacion();

        //Politicas
        PoliticaSeleccionDeParcelas politicaSeleccionDeParcelas;
        PoliticaCompraDeRIGs politicaCompraDeRIGs;
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
            politicaCompraDeRIGs = new PoliticaSiempreTenerUnRIG();
        } else {
            politicaCompraDeRIGs = new PoliticaTenerUnRigParaCadaPozo();
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

        System.out.println("Seleccione una politica de finalizacion (Unica opcion)");
        politicaFinalizacion = new PoliticaFinalizarPorDilucionCritica(params.dilucionCriticaPetroleo);

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
                politicaCompraDeRIGs,
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

        //Politica

        //logger.loguear();

        // -> Comienza la simulacion
        // Dividir en Parcelas . Hecho.

        // while (politicaFinalizacion no se cumple){
        //
        // Avanzar el dia para plantas en construccion.
        //
        //--> Compramos Rigs en Base a Politica de CompraRigs
        //
        //--> se ejecuta sii no llegamos al limite de Pozos
        // --------Seleccionar Parcelas. Hecho . -> parte de la politica de excavacion
        //
        //
        // Excavamos Pozos.
        //
        // }

        while(!simulador.hayQueFinalizarSimulacion()){
            simulador.simularUnNuevoDia();
        }
        logger.loguear("Se finalizo la simulacion con resultados: FIXME");
    }
}
