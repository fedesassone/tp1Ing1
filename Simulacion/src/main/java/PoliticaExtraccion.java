import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class PoliticaExtraccion implements Politica {

    //FIXME: Agregar tests
    void aplicarPolitica(Simulador unSimulador){
        //Obtenemos los parametros necesarios para la aplicacion
        List<Pozo> pozosSinExtraccionDiaria = new LinkedList<Pozo>(unSimulador.reguladorPozo.damePozosCompletados());
        double capacidadSeparacionTotal = unSimulador.reguladorPlantaSeparadora.capacidadDeSeparacionTotal();
        double capacidadAlmacenamientoGasTotal = unSimulador.reguladorTanqueGas.capacidadDeAlmacenamientoTotal();
        double capacidadAlmacenamientoAguaTotal = unSimulador.reguladorTanqueAgua.capacidadDeAlmacenamientoTotal();

        double capacidadExtraccionTotal = capacidadExtraccionMaxima(
                capacidadSeparacionTotal, capacidadAlmacenamientoGasTotal, capacidadAlmacenamientoAguaTotal,
                unSimulador.reservorio.proporcionDeGas, unSimulador.reservorio.proporcionDeAgua,
                unSimulador.reservorio.volumenActual());
        int numeroPozosAExtraer = numeroPozosAHabilitar(unSimulador);

        double volumenTotalExtraido = 0;
        while(true){
            Optional<Pozo> pozoSiguienteOpt = siguiente(pozosSinExtraccionDiaria, unSimulador);
            if(!pozoSiguienteOpt.isPresent()){
                break;
            }

            //Se obtiene el pozo siguiente
            Pozo pozoSiguiente = pozoSiguienteOpt.get();
            pozosSinExtraccionDiaria.remove(pozoSiguiente);

            //Se obtiene el volumen a extraer del pozo
            double capacidadExtraccionPozo = pozoSiguiente.potencialDeVolumenDiario(numeroPozosAExtraer);
            double volumenAExtraer = volumenAExtraer(capacidadExtraccionTotal, capacidadExtraccionPozo);
            volumenTotalExtraido += volumenAExtraer;

            //Se realiza la extraccion
            pozoSiguiente.extraer(volumenAExtraer, unSimulador.reservorio.volumenInicial(),
                    unSimulador.reservorio.volumenActual(), numeroPozosAExtraer);
            unSimulador.reservorio.extraer(volumenAExtraer);
        }

        //Se separa lo extraido
        unSimulador.reguladorPlantaSeparadora.separar(volumenTotalExtraido);

        //Se almacena el gas y agua separados
        unSimulador.reguladorTanqueAgua.almacenar(volumenTotalExtraido * unSimulador.reservorio.proporcionDeAgua);
        unSimulador.reguladorTanqueGas.almacenar(volumenTotalExtraido * unSimulador.reservorio.proporcionDeGas);

    }

    abstract int numeroPozosAHabilitar(Simulador unSimulador);

    abstract Optional<Pozo> siguiente(List<Pozo> pozos, Simulador unSimulador);

    abstract double volumenAExtraer(double unaCapacidadExtraccionTotal, double unaCapacidadExtraccion);

    private double capacidadExtraccionMaxima(double unaCapacidadSeparacionTotal,
                                             double unaCapacidadAlmacenamientoGasTotal,
                                             double unaCapacidadAlmacenamientoAguaTotal,
                                             double proporcionGas,
                                             double proporcionAgua,
                                             double volumenActual){
        double extraccionMaximaPorTanquesGas = unaCapacidadAlmacenamientoGasTotal / proporcionGas;
        double extraccionMaximaPorTanquesAgua = unaCapacidadAlmacenamientoAguaTotal / proporcionAgua;
        return Math.min(volumenActual,
                Math.min(unaCapacidadSeparacionTotal,
                  Math.min(extraccionMaximaPorTanquesAgua, extraccionMaximaPorTanquesGas)));
    }


}
