package simOil.politicas;

import simOil.Simulador;

/**
 * Politica: Diariamente se vende un porcentaje fijado del gas almacenado en los tanques ese dia
 */
public class PoliticaVenderPorcentajeGas implements PoliticaVentaDeGas {

    private double porcentajeVentaGas;

    public PoliticaVenderPorcentajeGas(double porcentajeVentaGas) {
        this.porcentajeVentaGas = porcentajeVentaGas;
    }

    public void realizarVentaDeGas(Simulador unSimulador){
        double cantidadGasAVender = unSimulador.reguladorTanqueGas.capacidadEnUsoTotal() * porcentajeVentaGas;
        if(cantidadGasAVender > 0){
            unSimulador.reguladorTanqueGas.extraer(cantidadGasAVender);
            unSimulador.venderGas(cantidadGasAVender);
        }
    }

}
