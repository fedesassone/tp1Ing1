package simOil.politicas;

import simOil.Simulador;

public class PoliticaFinalizarPorNumeroDias implements PoliticaFinalizacion {

    int numeroTotalDiasSimulacion;

    public PoliticaFinalizarPorNumeroDias(int numeroTotalDiasSimulacion) {
        this.numeroTotalDiasSimulacion = numeroTotalDiasSimulacion;
    }

    public boolean hayQueFinalizarSimulacion(Simulador simulador){
        return simulador.numeroDeDia >= numeroTotalDiasSimulacion;
    }

}
