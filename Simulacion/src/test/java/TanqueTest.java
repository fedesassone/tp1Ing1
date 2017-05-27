import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TanqueTest {

    @Test
    public void testAlmacenamiento(){
        Tanque tanque = new Tanque(2, 1000, new Logger());

        //Utilizamos parte del tanque para almacenamiento
        tanque.almacenar(400);
        assert(tanque.espacioUtilizado == 400);
        assert(tanque.capacidadLibre() == 600);
        assert(tanque.capacidadTotal == 1000);

        //Extraemos del tanque
        tanque.extraer(150);
        assert(tanque.espacioUtilizado == 250);
        assert(tanque.capacidadLibre() == 750);
        assert(tanque.capacidadTotal == 1000);
    }

}