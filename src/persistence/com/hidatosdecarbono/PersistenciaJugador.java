package com.hidatosdecarbono;

import org.json.JSONObject;

import java.util.ArrayList;

public class PersistenciaJugador extends Persistencia{

    public void guardaEnTxt(String json, String nombreFichero) {
        super.guardaEnTxt(json, nombreFichero, true);
    }

    /**
     * Devuelve un JSONObject que contiene la representacion en JSON del jugador correspondiente al username pasado por parametro
     * @param username Un String que contiene el username a buscar en la persistencia
     * @param nombreFichero Un String que contiene el nombre del fichero donde se encuentra la persistencia de jugadores
     * @return Un JSONObject o null si existe ningun jugador con ese username
     */
   public JSONObject obtenJugador(String username, String nombreFichero) {
       ArrayList<JSONObject> texto = obtenDeTxt(nombreFichero);
       if(texto == null) return null;
       for (JSONObject jugador : texto){
           if(jugador.get("username").equals(username)){
                return jugador;
           }

       }
       return null;
   }

}
