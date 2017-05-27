import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PoliticaExtraerPozosAleatoriosTest {

    @Test
    public void testPoliticaExtraccion(){
        //Setup de politica, pozos, plantas y tanques
        int numeroPlantas = 2;
        int numeroTanquesGas = 1;
        int numeroTanquesAgua = 1;
        List<Pozo> pozosCompletados = new LinkedList<Pozo>();
        pozosCompletados.add(new Pozo(1, 150, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(2, 150, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        pozosCompletados.add(new Pozo(3, 150, new CalculadorPresionPorReinyeccionImpl(), new Logger()));
        ReguladorPozo reguladorPozo = new ReguladorPozo(pozosCompletados, new LinkedList<PozoEnExcavacion>());
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new Fixtures().reguladorPlantaCon(numeroPlantas);
        ReguladorTanque reguladorTanqueGas = new Fixtures().reguladorTanqueCon(numeroTanquesGas, TipoDeProducto.GAS);
        ReguladorTanque reguladorTanqueAgua = new Fixtures().reguladorTanqueCon(numeroTanquesAgua, TipoDeProducto.AGUA);
        Reservorio reservorio = new Reservorio(0.2, 0.3, 0.5, 1000);
        Logger logger = new Logger();
        Simulador simulador = new Simulador(reguladorTanqueAgua, reguladorTanqueGas, reguladorPozo,
                reguladorPlantaSeparadora, reservorio, logger);

        PoliticaExtraerPozosAleatorios politicaExtraccion = new PoliticaExtraerPozosAleatorios(2);

        List<Pozo> pozosNoUsados = new LinkedList<Pozo>(pozosCompletados);
        assert(politicaExtraccion.numeroPozosAHabilitar(simulador) == numeroPlantas);

        //Extraccion pozo 1
        Optional<Pozo> pozoSiguiente1 = politicaExtraccion.siguiente(pozosNoUsados, simulador);
        assert(pozoSiguiente1.isPresent());
        double volExtraccionPozo1 = politicaExtraccion.volumenAExtraer(1140, pozoSiguiente1.get().potencialDeVolumenDiario(numeroPlantas));
        assert(volExtraccionPozo1 == pozoSiguiente1.get().potencialDeVolumenDiario(numeroPlantas));
        pozosNoUsados.remove(pozoSiguiente1.get());

        //Extraccion pozo 2
        Optional<Pozo> pozoSiguiente2 = politicaExtraccion.siguiente(pozosNoUsados, simulador);
        assert(pozoSiguiente2.isPresent());
        double volExtraccionPozo2 = politicaExtraccion.volumenAExtraer(1140, pozoSiguiente2.get().potencialDeVolumenDiario(numeroPlantas));
        assert(volExtraccionPozo2 == pozoSiguiente2.get().potencialDeVolumenDiario(numeroPlantas));
    }

}