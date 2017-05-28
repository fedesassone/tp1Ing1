package simOil.politicas;

import simOil.Parcela;
import simOil.Simulador;
import simOil.logger.Logger;
import simOil.logger.LoggerAConsola;

import java.util.List;

/**
 * Created by federico on 28/05/17.
 */
public class PoliticaSeleccionDeParcelasSeleccionarLaPrimeraDisponible implements PoliticaSeleccionDeParcelas {
    @Override
    public void aplicarPolitica(Simulador simulador) {
        Logger logger = new LoggerAConsola();
        Parcela parcela = simulador.parcelasNoExcavadas.get(0);
        simulador.parcelasExcavacionEmpezada.add(parcela);
        simulador.parcelasNoExcavadas.remove(0);

        logger.loguear("Una parcela pasó a estar en excavacion");
    }
}
