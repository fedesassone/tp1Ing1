import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

class ReguladorTanqueTest {

    @Test
    public void testConstruccionTanques() {
        ParametrosSimulacion param = new ParametrosSimulacion();

        ReguladorTanque reguladorTanques = new ReguladorTanque(TipoDeProducto.GAS);
        reguladorTanques.comprarTanque();
        reguladorTanques.comprarTanque();

        //Las plantas tardan diasConstruccionPlantas en construirse
        assert(reguladorTanques.capacidadDeAlmacenamientoTotal() == 0);
        reguladorTanques.avanzarDiaConstrucciones();
        reguladorTanques.comprarTanque();

        for (int i = 0; i < param.diasConstruccionTanques - 1 ; i++) {
            assert(reguladorTanques.capacidadDeAlmacenamientoTotal() == 0);
            reguladorTanques.avanzarDiaConstrucciones();
        }

        //Luego de pasar diasConstruccionPlantas se deben haber creado las plantas
        int numeroTanquesEsperado = 2;
        assert(reguladorTanques.dameTanquesCompletados().size() == numeroTanquesEsperado);
        assert(reguladorTanques.capacidadDeAlmacenamientoTotal() == numeroTanquesEsperado * param.capacidadNuevasPlantas);

        //El numeroTanque dado a las plantas debe ser el correcto
        Iterator<Tanque> tanquesIt = reguladorTanques.dameTanquesCompletados().iterator();
        for (int i = 0; i < numeroTanquesEsperado; i++) {
            assert(tanquesIt.next().numeroTanque == i);
        }

        //Al pasar un dia mas se tiene que tener una planta mas
        reguladorTanques.avanzarDiaConstrucciones();
        assert(reguladorTanques.dameTanquesCompletados().size() == numeroTanquesEsperado + 1);
        assert(reguladorTanques.capacidadDeAlmacenamientoTotal() == (numeroTanquesEsperado + 1) * param.capacidadNuevasPlantas);
    }

    @Test
    public void testAlmacenamientoYExtraccion(){
        ParametrosSimulacion param = new ParametrosSimulacion();
        ReguladorTanque reguladorTanques = new ReguladorTanque(TipoDeProducto.GAS);

        //Construimos 2 tanques
        reguladorTanques.comprarTanque();
        reguladorTanques.comprarTanque();
        for (int i = 0; i < param.diasConstruccionTanques ; i++) {
            reguladorTanques.avanzarDiaConstrucciones();
        }

        //Utilizamos los tanques los tanques para almacenamiento
        reguladorTanques.almacenar(1.5 * param.capacidadNuevosTanques);
        List<Tanque> tanquesDespuesAlmacenamiento = reguladorTanques.dameTanquesCompletados();
        assert(tanquesDespuesAlmacenamiento.get(0).espacioUtilizado == param.capacidadNuevosTanques);
        assert(tanquesDespuesAlmacenamiento.get(1).espacioUtilizado == 0.5 * param.capacidadNuevosTanques);

        //Utilizamos los tanques los tanques para extraccion
        reguladorTanques.extraer(1.25 * param.capacidadNuevosTanques);
        List<Tanque> tanquesDespuesExtraccion = reguladorTanques.dameTanquesCompletados();
        assert(tanquesDespuesExtraccion.get(0).espacioUtilizado == 0);
        assert(tanquesDespuesExtraccion.get(1).espacioUtilizado == 0.25 * param.capacidadNuevosTanques);
    }

}