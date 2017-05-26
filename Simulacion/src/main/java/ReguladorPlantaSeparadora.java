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

    public ReguladorPlantaSeparadora() {
        this.plantasSeparadorasCompletadas = new LinkedList<PlantaSeparadora>();
        this.plantasSeparadorasEnConstruccion = new LinkedList<PlantaSeparadoraEnConstruccion>();
    }

    public List<PlantaSeparadora> damePlantasCompletadas(){
        return plantasSeparadorasCompletadas;
    }

    //El reparto de la separacion en las plantas se hace en el orden en el que estas se construyeron
    //Separando lo mas que se puede con cada planta hasta que no hay mas material para separar
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

    public void comprarPlantaSeparadora(){
        //FIXME: Se necesita el logger para loggear la compra de la planta separadora?
        //FIXME: De donde se obtiene el precio de la planta separadora comprada?
        plantasSeparadorasEnConstruccion.add(new PlantaSeparadoraEnConstruccion(diasConstruccionPlantas));
    }

    public double capacidadDeSeparacionTotal(){
        double capacidadTotal = 0;
        for(PlantaSeparadora planta : plantasSeparadorasCompletadas){
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
                //El numero de las plantas se genera secuencialmente a partir de 0
                int numeroNuevaPlanta = plantasSeparadorasCompletadas.size();
                PlantaSeparadora nuevaPlanta = new PlantaSeparadora(new Logger(), numeroNuevaPlanta, capacidadNuevasPlantas);
                plantasEnConstruccionIterator.remove();
                plantasSeparadorasCompletadas.add(nuevaPlanta);
            }
        }
    }

}
