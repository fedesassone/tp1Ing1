class SimulacionApp {
    //FIXME: Completar
    public static void main(String[] args) {
        //Obtenemos los reguladores
        ReguladorPozo reguladorPozo = new ReguladorPozo();
        ReguladorPlantaSeparadora reguladorPlantaSeparadora = new ReguladorPlantaSeparadora();
        ReguladorTanque reguladorTanquesGas = new ReguladorTanque();
        ReguladorTanque reguladorTanquesAgua = new ReguladorTanque();

        //Obtenemos las politicas

        Simulador simulador = new Simulador();
    }
}
