package simOil.politicas;

import simOil.Simulador;

//FIXME: No seria politica alquiler de RIGs
public interface PoliticaCompraDeRIGs extends Politica {
    void aplicarPolitica(Simulador simulador);
}
