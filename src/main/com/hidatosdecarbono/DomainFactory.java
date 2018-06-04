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
        jugarHidatosCTRL = new JugarHidatosCTRL(persistenciaCTRL);
        creadorHidatosCTRL = new CreadorHidatosCTRL(persistenciaCTRL, jugarHidatosCTRL, logInCTRL);
        return creadorHidatosCTRL;
    }


    public JugarHidatosCTRL getControladorJugarHidatoPausado() throws IndexOutOfBoundsException{
        jugarHidatosCTRL = new JugarHidatosCTRL(persistenciaCTRL);
        Partida partida = persistenciaCTRL.obtenPartida();
        jugarHidatosCTRL.setPartida(partida);
        return jugarHidatosCTRL;
    }

    public ConsultarRankingCTRL getRankingCTRL(Dificultad dificultad) {
        rankingCTRL = new ConsultarRankingCTRL(persistenciaCTRL, dificultad);
        return rankingCTRL;
    }
}
