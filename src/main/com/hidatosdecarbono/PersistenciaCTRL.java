package com.hidatosdecarbono;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;


public class PersistenciaCTRL {

    private PersistenciaJugador persistenciaJugador;
    private final String nombreFicheroJugadores = "jugadores.txt";
    private final String nombreFicheroHidatos = "hidatos.txt";

    /**
     * Constructora por defecto.Crea las instancias de las clases de persistencia.
     */
    public PersistenciaCTRL() {
        this.persistenciaJugador = new PersistenciaJugador();
    }

    /**
     * Guarda un nuevo jugador de forma persistente.
     * @param jugador Un Jugador que contiene el username y la contraseña
     * @throws InvalidUserException si el username del Jugador pasado por parametro ya existia.
     */
    public void guardaJugador (Jugador jugador) throws InvalidUserException {
        Gson gson = new Gson();
        String json = gson.toJson(jugador);
        if(persistenciaJugador.obtenJugador(jugador.getUsername(),nombreFicheroJugadores) != null)
            throw new InvalidUserException("No se puede guardar el jugador, ya existe otro con el mismo username");
        persistenciaJugador.guardaEnTxt(json,nombreFicheroJugadores);
    }

    /**
     * Obtiene un Jugador de la persistencia, a partir de su username
     * @param username Un String que contiene el username del jugador que buscamos
     * @return jugador Jugador de la persistencia que tiene el mismo username que el pasado por parametro
     */
    public Jugador obtenJugador(String username){
        Gson gson = new Gson();
        String json = persistenciaJugador.obtenJugador(username,nombreFicheroJugadores).toString();
        Jugador jugador = gson.fromJson(json,Jugador.class);
        return jugador;
    }

    /*public void guardaHidato (Hidato hidato){
        Gson gson = new Gson();
        String json = gson.toJson(hidato);
        json = añadeTipoHidato(json,hidato.getTipoHidato());
        //persistenciaHidato.guardaEnTxt(json,nombreFicheroHidatos);
    }

    private String añadeTipoHidato(String json,TipoHidato tipoHidato){
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        jsonObject.addProperty("tipo",tipoHidato.toString());
        return jsonObject.toString();
    }*/

}
