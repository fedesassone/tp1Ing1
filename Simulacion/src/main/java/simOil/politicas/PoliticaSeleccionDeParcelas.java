package simOil.politicas;

import simOil.Simulador;

/**
 * Created by federico on 28/05/17.
 */
public interface PoliticaSeleccionDeParcelas extends Politica {
    void aplicarPolitica(Simulador simulador);
}
