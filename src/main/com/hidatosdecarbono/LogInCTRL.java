package com.hidatosdecarbono;

public class LogInCTRL {
    private PersistenciaCTRL persistencia;
    private Jugador jugador;

    public LogInCTRL(PersistenciaCTRL persistencia){
        this.persistencia = persistencia;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public boolean altaJugador(String username, String pass) {
        Jugador nou = new Jugador(username,pass);
        try{
            persistencia.guardaJugador(nou);
        }
        catch (InvalidUserException e) {
            return false;
        }
        return true;
        
    }
    
    public boolean logIn(String username, String pass) throws InvalidUserException{
        jugador = persistencia.obtenJugador(username);

        if(!jugador.getPassword().equals(pass)){
            jugador = null;
            return false;
        }
        return true;
    }
}
