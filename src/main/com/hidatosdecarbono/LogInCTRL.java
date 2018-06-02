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

    /**
     * Da de alta el jugador en el sistema
     * @param username
     * @param pass
     * @return Devuelve true si se da de alta al jugador, false si ya existe un jugador con el mismo username registrado
     */
    public boolean altaJugador(String username, String pass) {
        Jugador nou = new Jugador(username,pass);
        try{
            persistencia.guardaJugador(nou);
        }
        catch (InvalidUserException e) {
            return false;
        }
        jugador = nou;
        return true;
        
    }
    
    public boolean logIn(String username, String pass) throws InvalidUserException{
        Jugador login = persistencia.obtenJugador(username);

        if(login.getPassword().equals(pass)){
            jugador = login;
            return true;
        }
        return false;
    }
}
