package simOil.politicas;

import simOil.Simulador;

public interface PoliticaCompraDeTanques extends Politica {
    void aplicarPolitica(Simulador simulador);
}
