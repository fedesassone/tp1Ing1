package simOil.reguladores;

import simOil.logger.Logger;
import simOil.ParametrosSimulacion;
import simOil.PlantaSeparadora;
import simOil.PlantaSeparadoraEnConstruccion;
import simOil.logger.LoggerAConsola;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class ReguladorPlantaSeparadora implements Regulador {

    //Obtenemos los valores de las constantes para la construccion de plantas separadoras
    private final double capacidadNuevasPlantas = new ParametrosSimulacion().capacidadNuevasPlantas;
    private final int diasConstruccionPlantas = new ParametrosSimulacion().diasConstruccionPlantas;

    private List<PlantaSeparadora> plantasSeparadorasCompletadas;
    private List<PlantaSeparadoraEnConstruccion> plantasSeparadorasEnConstruccion;
    private Logger logger;

    public ReguladorPlantaSeparadora(Logger logger) {
        this.plantasSeparadorasCompletadas = new LinkedList<PlantaSeparadora>();
        this.plantasSeparadorasEnConstruccion = new LinkedList<PlantaSeparadoraEnConstruccion>();
        this.logger = logger;
    }

    public int numeroDePlantasEnConstruccion(){
        return this.plantasSeparadorasEnConstruccion.size();
    }

    public List<PlantaSeparadora> damePlantasCompletadas(){
        return plantasSeparadorasCompletadas;
    }

    //El reparto de la separacion en las plantas se hace en el orden en el que estas se construyeron
    //Se separa lo mas que se puede con cada planta hasta que no hay mas material para separar
    public void separar(double unVolumen){
        double volumenPorSeparar = unVolumen;
        Iterator<PlantaSeparadora> plantasSeparadorasIterator = plantasSeparadorasCompletadas.iterator();
        while(plantasSeparadorasIterator.hasNext() && volumenPorSeparar > 0) {
            PlantaSeparadora plantaAUsar = plantasSeparadorasIterator.next();
            double volumenSeparado = Math.min(volumenPorSeparar, plantaAUsar.capacidadProcesamiento());
            plantaAUsar.separar(volumenSeparado);
            volumenPorSeparar -= volumenSeparado;
        }
    }

    public PlantaSeparadoraEnConstruccion comprarPlantaSeparadora(){
        PlantaSeparadoraEnConstruccion nuevaPlanta = new PlantaSeparadoraEnConstruccion(diasConstruccionPlantas, capacidadNuevasPlantas );
        plantasSeparadorasEnConstruccion.add(nuevaPlanta);
        return nuevaPlanta;
    }

    public double capacidadDeSeparacionTotal(){
        double capacidadTotal = 0;
        for(PlantaSeparadora planta : plantasSeparadorasCompletadas){
            capacidadTotal += planta.capacidadProcesamiento();
        }
        return capacidadTotal;
    }

    public double futuraCapacidadDeSeparacionTotal(){
        double capacidadTotal = capacidadDeSeparacionTotal();
        for(PlantaSeparadoraEnConstruccion planta : plantasSeparadorasEnConstruccion){
            capacidadTotal += planta.capacidadProcesamiento();
        }
        return capacidadTotal;
    }

    public void avanzarDiaConstrucciones(){
        Iterator<PlantaSeparadoraEnConstruccion> plantasEnConstruccionIterator =
                plantasSeparadorasEnConstruccion.iterator();
        while(plantasEnConstruccionIterator.hasNext()){
            PlantaSeparadoraEnConstruccion plantaEnConstruccion = plantasEnConstruccionIterator.next();
            plantaEnConstruccion.avanzarDiaConstruccion();
            if(plantaEnConstruccion.construccionTerminada()){
                //El numeroTanque de las plantas se genera secuencialmente a partir de 0
                int numeroNuevaPlanta = plantasSeparadorasCompletadas.size();
                PlantaSeparadora nuevaPlanta = new PlantaSeparadora(numeroNuevaPlanta, capacidadNuevasPlantas, logger);
                plantasEnConstruccionIterator.remove();
                plantasSeparadorasCompletadas.add(nuevaPlanta);
            }
        }
    }

}
