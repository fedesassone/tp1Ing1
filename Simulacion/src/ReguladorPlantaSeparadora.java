import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class ReguladorPlantaSeparadora implements Regulador {

    //TODO: Mover a un archivo con constantes
    double capacidadNuevasPlantas = 1000;
    int diasConstruccionPlantas = 10;

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
                plantasEnConstruccionIterator.remove();
                plantasSeparadorasCompletadas.add(new PlantaSeparadora(new Logger(), capacidadNuevasPlantas));
            }
        }
    }

}
