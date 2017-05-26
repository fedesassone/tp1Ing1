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
        this.plantasSeparadorasCompletadas = new LinkedList<>();
        this.plantasSeparadorasEnConstruccion = new LinkedList<>();
    }

    public List<PlantaSeparadora> damePlantasCompletadas(){
        return plantasSeparadorasCompletadas;
    }

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
                int numeroNuevaPlanta = plantasSeparadorasCompletadas.size() + plantasSeparadorasEnConstruccion.size();
                PlantaSeparadora nuevaPlanta = new PlantaSeparadora(new Logger(), numeroNuevaPlanta, capacidadNuevasPlantas);
                plantasEnConstruccionIterator.remove();
                plantasSeparadorasCompletadas.add(nuevaPlanta);
            }
        }
    }

}
