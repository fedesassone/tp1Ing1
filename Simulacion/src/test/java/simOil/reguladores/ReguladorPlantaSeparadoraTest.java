package simOil.reguladores;

import org.junit.jupiter.api.Test;
import simOil.ParametrosSimulacion;
import simOil.PlantaSeparadora;
import simOil.logger.LoggerAConsola;

import java.util.Iterator;

public class ReguladorPlantaSeparadoraTest {

    @Test
    public void testConstruccionPlantasSeparadoras() {
        ParametrosSimulacion param = new ParametrosSimulacion();

        ReguladorPlantaSeparadora reguladorPlantas = new ReguladorPlantaSeparadora(new LoggerAConsola());
        reguladorPlantas.comprarPlantaSeparadora();
        reguladorPlantas.comprarPlantaSeparadora();

        //Las plantas tardan diasConstruccionPlantas en construirse
        assert(reguladorPlantas.capacidadDeSeparacionTotal() == 0);
        reguladorPlantas.avanzarDiaConstrucciones();
        reguladorPlantas.comprarPlantaSeparadora();

        for (int i = 0; i < param.diasConstruccionPlantas - 1 ; i++) {
            assert(reguladorPlantas.capacidadDeSeparacionTotal() == 0);
            reguladorPlantas.avanzarDiaConstrucciones();
        }

        //Luego de pasar diasConstruccionPlantas se deben haber creado las plantas
        int numeroPlantasEsperado = 2;
        assert(reguladorPlantas.damePlantasCompletadas().size() == numeroPlantasEsperado);
        assert(reguladorPlantas.capacidadDeSeparacionTotal() == numeroPlantasEsperado * param.capacidadNuevasPlantas);

        //El numeroTanque dado a las plantas debe ser el correcto
        Iterator<PlantaSeparadora> plantasIt = reguladorPlantas.damePlantasCompletadas().iterator();
        for (int i = 0; i < numeroPlantasEsperado; i++) {
            assert(plantasIt.next().numeroPlanta == i);
        }

        //Al pasar un dia mas se tiene que tener una planta mas
        reguladorPlantas.avanzarDiaConstrucciones();
        assert(reguladorPlantas.damePlantasCompletadas().size() == numeroPlantasEsperado + 1);
        assert(reguladorPlantas.capacidadDeSeparacionTotal() == (numeroPlantasEsperado + 1) * param.capacidadNuevasPlantas);
    }

}