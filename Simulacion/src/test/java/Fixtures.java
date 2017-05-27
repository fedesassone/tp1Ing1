public class Fixtures {

    private ParametrosSimulacion param = new ParametrosSimulacion();

    public ReguladorTanque reguladorTanqueCon(int unNumeroDeTanques, TipoDeProducto tipoDeProducto){
        ReguladorTanque reguladorTanque = new ReguladorTanque(tipoDeProducto);

        //Compra de tanques
        for (int i = 0; i < unNumeroDeTanques; i++) {
            reguladorTanque.comprarTanque();
        }

        //Construccion de tanques
        for (int i = 0; i < param.diasConstruccionTanques; i++) {
            reguladorTanque.avanzarDiaConstrucciones();
        }

        return reguladorTanque;
    }

    public ReguladorPlantaSeparadora reguladorPlantaCon(int unNumeroDePlantas){
        ReguladorPlantaSeparadora reguladorPlanta = new ReguladorPlantaSeparadora();

        //Compra de tanques
        for (int i = 0; i < unNumeroDePlantas; i++) {
            reguladorPlanta.comprarPlantaSeparadora();
        }

        //Construccion de tanques
        for (int i = 0; i < param.diasConstruccionPlantas; i++) {
            reguladorPlanta.avanzarDiaConstrucciones();
        }

        return reguladorPlanta;
    }

}
