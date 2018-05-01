package com.hidatosdecarbono;

public class DomainFactory {
    private LogInCTRL logInCTRL;
    private CreadorHidatosCTRL creador;
    private JugarHidatosCTRL juega;

    public LogInCTRL getLogInCTRL(){
        logInCTRL = new LogInCTRL();
        return logInCTRL;
    }

    public CreadorHidatosCTRL getControladorCreador() {
        creador = new CreadorHidatosCTRL();
        return creador;
    }

    public JugarHidatosCTRL getControladorJugar(String s){
        if(s == "Creado") {
            juega = new JugarHidatosCTRL();
            juega.inicializa(creador.getHidatoCreado(), logInCTRL.getJugador());
            return juega;
        }
        return new JugarHidatosCTRL();
    }
}
