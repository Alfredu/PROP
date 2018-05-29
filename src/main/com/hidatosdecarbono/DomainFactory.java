package com.hidatosdecarbono;

public class DomainFactory {
    private PersistenciaCTRL persistencia;
    private LogInCTRL logInCTRL;
    private CreadorHidatosCTRL creador;
    private JugarHidatosCTRL juega;
    private RankingCTRL rankingCTRL = new RankingCTRL();


    public DomainFactory(){
        persistencia = new PersistenciaCTRL();
    }

    public LogInCTRL getLogInCTRL(){
        logInCTRL = new LogInCTRL(persistencia);
        return logInCTRL;
    }

    public CreadorHidatosCTRL getControladorCreador() {
        creador = new CreadorHidatosCTRL(rankingCTRL);
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

    public RankingCTRL getRankingCTRL() {
        return rankingCTRL;
    }
}
