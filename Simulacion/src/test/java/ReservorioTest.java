import org.junit.Test;

public class ReservorioTest {

    //FIXME: Test de prueba, crear tests mejores
    @Test
    public void testReservorioVacio() {
        double volumenInicialReservorio = 1000;
        Reservorio reservorio = new Reservorio(0.1, 0.2, 0.7, volumenInicialReservorio);
        assert(reservorio.volumenActual() == volumenInicialReservorio);
    }

}