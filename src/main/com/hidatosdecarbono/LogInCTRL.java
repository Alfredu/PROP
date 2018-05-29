package com.hidatosdecarbono;

import java.security.InvalidParameterException;

public class LogInCTRL {
    private PersistenciaCTRL persistencia;
    private Jugador jugador;

    public LogInCTRL(PersistenciaCTRL persistencia){
        this.persistencia = persistencia;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void creaJugador(String username, String pass) throws InvalidUserException {
        jugador = persistencia.obtenJugador(username,pass);

        if(jugador == null){
            jugador = new Jugador(username,pass);
            persistencia.guardaJugador(jugador);
        }

    }
}
