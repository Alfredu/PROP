package com.hidatosdecarbono;

public class Jugador{
    private String username;
    private String password;

    /**
     * Constructora para un jugador, el username debe ser diferente a los ya existentes.
     * @param username Un string que contiene el nombre con el que se identificara al jugador
     * @param password  Un string que contiene la contraseña para autentificar al jugador
     */
    public Jugador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Devuelve el username del jugador
     * @return  Un string que contiene el username del jugador
     */
    public String getUsername() {
        return username;
    }

    /**
     * Devuelve la password del jugador
     * @return Un string que contiene la password del jugador
     */
    public String getPassword() {
        return password;
    }

    /**
     * Cambia el username del usuario.Se debe comprovar que el nuevo username no identifica a otro jugador
     * @param username Un string con el nuevo username a modificar
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Cambia la password del usuario.
     * @param password Un string que contiene la nueva contraseña del jugador
     */
    public void setPassword(String password) {
        this.password = password;
    }
}