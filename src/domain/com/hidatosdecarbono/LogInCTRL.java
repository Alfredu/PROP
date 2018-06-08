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

    /**
     * Autentifica el jugador en el sistema si el username i la contraseña coinciden con las de algun usuario dado de alta
     * Devuelve true si el username i la contraseña coinciden, false en el caso de que el usuario exista pero la constraseña sea erronea
     * y lanza una exepcion si ese username no pertence a ningún jugador
     * @param username El username del jugador
     * @param pass La contraseña de ese jugador
     * @return True en caso que se autentifique correctamente, false si la contraseña introducida no es la misma que la guardada
     * @throws InvalidUserException Se lanza si el username no coincide con el de ningún jugador dado de alta
     */
    
    public boolean logIn(String username, String pass) throws InvalidUserException{
        Jugador login = persistencia.obtenJugador(username);

        if(login.getPassword().equals(pass)){
            jugador = login;
            return true;
        }
        return false;
    }
}
