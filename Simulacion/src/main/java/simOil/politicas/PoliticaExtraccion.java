package simOil.politicas;

import simOil.Pozo;
import simOil.Simulador;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class PoliticaExtraccion implements Politica {

    public void realizarExtracciones(Simulador unSimulador){
        //Obtenemos los parametros necesarios para la aplicacion
        List<Pozo> pozosSinExtraccionDiaria = new LinkedList<Pozo>(unSimulador.reguladorPozo.damePozosCompletados());

        double capacidadExtraccionMaxima = capacidadExtraccionMaxima(unSimulador);
        int numeroPozosAHabilitar = numeroPozosAHabilitar(unSimulador);

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
            double capacidadExtraccionPozo = pozoSiguiente.potencialDeVolumenDiario(numeroPozosAHabilitar);
            double volumenAExtraer = volumenAExtraer(capacidadExtraccionMaxima, capacidadExtraccionPozo);
            volumenTotalExtraido += volumenAExtraer;
            capacidadExtraccionMaxima -= volumenAExtraer;

            //Se realiza la extraccion
            pozoSiguiente.extraer(volumenAExtraer, unSimulador.reservorio.volumenInicial(),
                    unSimulador.reservorio.volumenActual(), numeroPozosAHabilitar);
            unSimulador.reservorio.extraer(volumenAExtraer);
        }

        //Se separa lo extraido
        unSimulador.reguladorPlantaSeparadora.separar(volumenTotalExtraido);

        //Se almacena el gas y agua separados
        unSimulador.reguladorTanqueAgua.almacenar(volumenTotalExtraido * unSimulador.reservorio.proporcionDeAgua);
        unSimulador.reguladorTanqueGas.almacenar(volumenTotalExtraido * unSimulador.reservorio.proporcionDeGas);

        //Se vende el petroleo separado
        unSimulador.venderPetroleo(volumenTotalExtraido * unSimulador.reservorio.proporcionDePetroleo);

    }

    abstract int numeroPozosAHabilitar(Simulador unSimulador);

    abstract Optional<Pozo> siguiente(List<Pozo> pozosNoUsados, Simulador unSimulador);

    abstract double volumenAExtraer(double unaCapacidadExtraccionTotal, double unaCapacidadExtraccion);

    private double capacidadExtraccionMaxima(Simulador simulador){
        double capacidadSeparacionTotal = simulador.reguladorPlantaSeparadora.capacidadDeSeparacionTotal();
        double capacidadAlmacenamientoGasTotal = simulador.reguladorTanqueGas.capacidadLibreTotal();
        double capacidadAlmacenamientoAguaTotal = simulador.reguladorTanqueAgua.capacidadLibreTotal();

        double extraccionMaximaPorTanquesGas = capacidadAlmacenamientoGasTotal / simulador.reservorio.proporcionDeGas;
        double extraccionMaximaPorTanquesAgua = capacidadAlmacenamientoAguaTotal / simulador.reservorio.proporcionDeAgua;
        return Math.min(simulador.reservorio.volumenActual(),
                Math.min(capacidadSeparacionTotal,
                  Math.min(extraccionMaximaPorTanquesAgua, extraccionMaximaPorTanquesGas)));
    }


}
