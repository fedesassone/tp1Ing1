//import org.junit.Test;
import org.junit.jupiter.api.Test;

public class ReservorioTest {

    @Test
    public void testVolumenesDelReservorio(){
        double volumenInicialReservorio = 1000;
        Reservorio reservorio = new Reservorio(0.3, 0.2, 0.5, volumenInicialReservorio);

        assert(reservorio.volumenInicial() == volumenInicialReservorio);
        assert(reservorio.volumenActual() == volumenInicialReservorio);
        assert(reservorio.volumenExtraido() == 0);

        //Se hace una extraccion del reservorio
        double volExtraccion = 300;
        reservorio.extraer(volExtraccion);
        assert(reservorio.volumenInicial() == volumenInicialReservorio);
        assert(reservorio.volumenActual() == volumenInicialReservorio - volExtraccion);
        assert(reservorio.volumenExtraido() == volExtraccion);

        //Se hace una reinyeccion al reservorio
        double volReinyeccion = 250;
        reservorio.reinyectar(volReinyeccion, "Gas");
        assert(reservorio.volumenInicial() == volumenInicialReservorio);
        assert(reservorio.volumenActual() == volumenInicialReservorio - volExtraccion + volReinyeccion);
        assert(reservorio.volumenExtraido() == volExtraccion);

        //Se hace una extraccion del resto del reservorio
        double volExtraccion2 = reservorio.volumenActual();
        reservorio.extraer(volExtraccion2);
        assert(reservorio.volumenInicial() == volumenInicialReservorio);
        assert(reservorio.volumenActual() == 0);
        assert(reservorio.volumenExtraido() == volExtraccion + volExtraccion2);
    }

    @Test
    public void testProporcionDespuesReinyeccion(){
        double volumenInicialReservorio = 1000;
        Reservorio reservorio = new Reservorio(0.3, 0.2, 0.5, volumenInicialReservorio);

        double volExtraccion = 500;
        reservorio.extraer(volExtraccion);

        //Se hace una reinyeccion de agua al reservorio
        double volReinyeccion = 350;
        reservorio.reinyectar(volReinyeccion, "Agua");
        assert(reservorio.proporcionDeGas == 0.17647058823529413);
        assert(reservorio.proporcionDeAgua == 0.7058823529411765);
        assert(reservorio.proporcionDePetroleo == 0.11764705882352941);

        //Se hace una reinyeccion de gas al reservorio
        double volReinyeccion2 = 100;
        reservorio.reinyectar(volReinyeccion2, "Gas");
        assert(reservorio.proporcionDeGas == 0.2631578947368421);
        assert(reservorio.proporcionDeAgua == 0.631578947368421);
        assert(reservorio.proporcionDePetroleo == 0.10526315789473684);

    }

}