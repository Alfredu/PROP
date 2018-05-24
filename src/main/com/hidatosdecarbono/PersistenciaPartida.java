package com.hidatosdecarbono;

import org.json.JSONObject;

import java.util.ArrayList;

public class PersistenciaPartida extends Persistencia{

    public void guardaEnTxt(String json, String nombreFichero) {
        super.guardaEnTxt(json, nombreFichero, true);
    }

    public JSONObject obtenPartida(int id, String nombreFichero){
        ArrayList<JSONObject> array = obtenDeTxt(nombreFichero);
        if(id >= array.size()) throw new IndexOutOfBoundsException();
        return array.get(id);
    }

}
