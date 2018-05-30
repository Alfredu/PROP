package com.hidatosdecarbono;

import org.json.JSONObject;

import java.util.ArrayList;

public class PersistenciaPartida extends Persistencia{

    public void guardaEnTxt(String json, String nombreFichero) {
        super.guardaEnTxt(json, nombreFichero, false);
    }

    public JSONObject obtenPartida(String nombreFichero){
        ArrayList<JSONObject> array = obtenDeTxt(nombreFichero);
        eliminaTxt(nombreFichero);
        return array.get(0);
    }

}
