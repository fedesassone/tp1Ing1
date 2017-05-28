package simOil.logger;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class LoggerAArchivoTest {

    @Test
    public void testEscribirAArchivo(){
        LoggerAArchivo logger = new LoggerAArchivo("prueba.txt");

        //Escribimos a archivo
        logger.loguear("Prueba1");
        logger.loguear("Prueba2");

        //Leemos del archivo
        try{
            BufferedReader in = new BufferedReader(new FileReader("prueba.txt"));
            String linea1 = in.readLine();
            assert(linea1.equals("Prueba1"));
            String linea2 = in.readLine();
            assert(linea2.equals("Prueba2"));
            assert(in.readLine() == null);

            //Borramos el archivo
            assert(new File("prueba.txt").delete());
        } catch (Exception e) {
            assert(false);
        }
    }

}