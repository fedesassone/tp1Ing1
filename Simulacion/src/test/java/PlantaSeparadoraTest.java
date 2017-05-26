import org.junit.Test;

public class PlantaSeparadoraTest {

    //FIXME: Test de prueba, crear tests mejores
    @Test
    public void testCrearPlantaSeparadora() {
        int numero = 1000;
        PlantaSeparadora plantaSeparadora = new PlantaSeparadora(new Logger(), numero, 1);
        assert(plantaSeparadora.numeroPlanta == numero);
    }

}