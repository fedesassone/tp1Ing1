import org.junit.jupiter.api.Test;

public class SimuladorTest {

    @Test
    public void testAvanzarDiaConstrucciones(){
        ParametrosSimulacion params = new ParametrosSimulacion();
        Reservorio reservorio = new Reservorio(0.3, 0.2, 0.5, 1000);
        Simulador simulador = new Simulador(reservorio);

        //Comenzamos construccion de tanques y plantas separadoras
        simulador.reguladorTanque.comprarTanque();
        simulador.reguladorTanque.comprarTanque();

        simulador.reguladorPlantaSeparadora.comprarPlantaSeparadora();
        simulador.reguladorPlantaSeparadora.comprarPlantaSeparadora();
        simulador.reguladorPlantaSeparadora.comprarPlantaSeparadora();

        int tiempoConstruccionElementos = Math.max(params.diasConstruccionPlantas, params.diasConstruccionTanques);
        for (int i = 0; i < tiempoConstruccionElementos + 1; i++) {
            if(i > params.diasConstruccionTanques){
                assert(simulador.reguladorTanque.dameTanquesCompletados().size() == 2);
            } else {
                assert(simulador.reguladorTanque.dameTanquesCompletados().size() == 0);
            }

            if(i > params.diasConstruccionPlantas){
                assert(simulador.reguladorPlantaSeparadora.damePlantasCompletadas().size() == 3);
            } else {
                assert(simulador.reguladorPlantaSeparadora.damePlantasCompletadas().size() == 0);
            }
        }
    }

}