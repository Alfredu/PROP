package com.hidatosdecarbono;

public class LogInCTRL {
    private Jugador jugador;

    public Jugador getJugador() {
        return jugador;
    }

    public void creaJugador(String username, String pass){
        jugador = new Jugador(username,pass);
    }
}
