import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
//FIXME: El manejo de las compras es siempre igual y nunca va a cambiar para este y ReguladorPlantas
//       Abstraer esto de alguna forma?
public class ReguladorTanque implements Regulador {

    //Obtenemos los valores de las constantes para la construccion de plantas separadoras
    private final double capacidadNuevosTanques = new ParametrosSimulacion().capacidadNuevosTanques;
    private final int diasConstruccionTanques = new ParametrosSimulacion().diasConstruccionTanques;

    private List<Tanque> tanquesCompletados;
    private List<TanqueEnConstruccion> tanquesEnConstruccion;
    private TipoDeProducto tipoDeProducto;

    public ReguladorTanque(TipoDeProducto tipoDeProducto) {
        this.tanquesCompletados = new LinkedList<Tanque>();
        this.tanquesEnConstruccion = new LinkedList<TanqueEnConstruccion>();
        this.tipoDeProducto = tipoDeProducto;
    }

    public List<Tanque> dameTanquesCompletados(){
        return tanquesCompletados;
    }

    //El reparto del almacenamiento en los tanques se hace en el orden en el que estas se construyeron
    //Se almacena lo mas que se puede con cada tanque hasta que no hay mas material para guardar
    public void almacenar(double unVolumen){
        double volumenPorAlmacenar = unVolumen;
        Iterator<Tanque> tanquesCompletadosIterator = tanquesCompletados.iterator();
        while(tanquesCompletadosIterator.hasNext() && volumenPorAlmacenar > 0) {
            Tanque tanqueAUsar = tanquesCompletadosIterator.next();
            if(tanqueAUsar.capacidadLibre() > 0){
                double volumenPorAlmacenarEnTanque = Math.min(volumenPorAlmacenar, tanqueAUsar.capacidadLibre());
                tanqueAUsar.almacenar(volumenPorAlmacenarEnTanque);
                volumenPorAlmacenar -= volumenPorAlmacenarEnTanque;
            }
        }
    }

    //El reparto de la extraccion en los tanques se hace en el orden en el que estas se construyeron
    //Se extrae lo mas que se puede de cada tanque hasta que no hay mas material para extraer
    public void extraer(double unVolumen){
        double volumenPorExtraer = unVolumen;
        Iterator<Tanque> tanquesCompletadosIterator = tanquesCompletados.iterator();
        while(tanquesCompletadosIterator.hasNext() && volumenPorExtraer > 0) {
            Tanque tanqueAUsar = tanquesCompletadosIterator.next();
            if(tanqueAUsar.espacioUtilizado > 0){
                double volumenPorExtraerDeTanque = Math.min(volumenPorExtraer, tanqueAUsar.espacioUtilizado);
                tanqueAUsar.extraer(volumenPorExtraerDeTanque);
                volumenPorExtraer -= volumenPorExtraerDeTanque;
            }
        }
    }

    public void comprarTanque(){
        //FIXME: Se necesita el logger para loggear la compra del tanque?
        //FIXME: De donde se obtiene el precio del tanque comprado?
        tanquesEnConstruccion.add(new TanqueEnConstruccion(diasConstruccionTanques));
    }

    public double capacidadDeAlmacenamientoTotal(){
        double capacidadTotal = 0;
        for(Tanque tanque : tanquesCompletados){
            capacidadTotal += tanque.capacidadLibre();
        }
        return capacidadTotal;
    }

    public void avanzarDiaConstrucciones(){
        Iterator<TanqueEnConstruccion> tanquesEnConstruccionIterator =
                tanquesEnConstruccion.iterator();
        while(tanquesEnConstruccionIterator.hasNext()){
            TanqueEnConstruccion tanqueEnConstruccion = tanquesEnConstruccionIterator.next();
            tanqueEnConstruccion.avanzarDiaConstruccion();
            if(tanqueEnConstruccion.construccionTerminada()){
                //El numeroTanque de los tanques se genera secuencialmente a partir de 0
                int numeroNuevoTanque = tanquesCompletados.size();
                Tanque nuevoTanque = new Tanque(numeroNuevoTanque, capacidadNuevosTanques, tipoDeProducto, new Logger());
                tanquesEnConstruccionIterator.remove();
                tanquesCompletados.add(nuevoTanque);
            }
        }
    }

}
