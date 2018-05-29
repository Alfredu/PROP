package com.hidatosdecarbono;

public class DomainFactory {
    private PersistenciaCTRL persistenciaCTRL;
    private LogInCTRL logInCTRL;
    private CreadorHidatosCTRL creadorHidatosCTRL;
    private JugarHidatosCTRL jugarHidatosCTRL;
    private ConsultarRankingCTRL rankingCTRL;


    public DomainFactory(){
        persistenciaCTRL = new PersistenciaCTRL();
    }

    public LogInCTRL getLogInCTRL(){
        logInCTRL = new LogInCTRL(persistenciaCTRL);
        return logInCTRL;
    }

    public CreadorHidatosCTRL getControladorCreador() {
        creadorHidatosCTRL = new CreadorHidatosCTRL(persistenciaCTRL);
        return creadorHidatosCTRL;
    }

    public JugarHidatosCTRL getControladorJugar(String s){
        if(s == "Creado") {
            jugarHidatosCTRL = new JugarHidatosCTRL(persistenciaCTRL);
            jugarHidatosCTRL.inicializa(creadorHidatosCTRL.getHidatoCreado(), logInCTRL.getJugador());
            return jugarHidatosCTRL;
        }
        return new JugarHidatosCTRL(persistenciaCTRL);
    }

    public ConsultarRankingCTRL getRankingCTRL(Dificultad dificultad) {
        rankingCTRL = new ConsultarRankingCTRL(persistenciaCTRL, dificultad);
        return rankingCTRL;
    }
}
