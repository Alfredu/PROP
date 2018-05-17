package com.hidatosdecarbono;


import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class PersistenciaJugador extends Persistencia{


   public JSONObject obtenJugador(String username,String nombreFichero){
       ArrayList<JSONObject> texto = obtenDeTxt(nombreFichero);
       if(texto == null) return null;
       for (JSONObject jugador : texto){
           if(jugador.get("username").equals(username)) return jugador;
       }
       return null;
   }

}
