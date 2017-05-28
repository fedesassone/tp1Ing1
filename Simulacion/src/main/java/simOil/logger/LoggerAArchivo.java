package simOil.logger;

import java.io.File;
import java.io.PrintWriter;

public class LoggerAArchivo implements Logger {

    PrintWriter writer;

    public LoggerAArchivo(String pathArchivo) {
        File file = new File(pathArchivo);
        if(file.exists() || file.getParentFile() == null || file.getParentFile().mkdirs()){
            try{
                this.writer = new PrintWriter(pathArchivo);
            } catch (Exception e) {
                System.out.println("No se pudo abrir el archivo");
                writer.close();
            }
        }else {
            System.out.println("Archivo no creado y fallo su creacion");
        }
    }

    public void loguear(String unMensaje){
        try {
            writer.println(unMensaje);
            writer.flush();
        } catch (Exception e) {
            System.out.println("Fallo la escritura al archivo");
            writer.close();
        }
    }
}
