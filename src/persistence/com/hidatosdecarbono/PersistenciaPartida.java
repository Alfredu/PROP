package com.hidatosdecarbono;

import org.json.JSONObject;

import java.util.ArrayList;

public class PersistenciaPartida extends Persistencia{

    public void guardaEnTxt(String json, String nombreFichero) {
        super.guardaEnTxt(json, nombreFichero, false);
    }

    public JSONObject obtenPartida(String nombreFichero) throws IndexOutOfBoundsException{
        ArrayList<JSONObject> array = obtenDeTxt(nombreFichero);
        eliminaTxt(nombreFichero);
        if (array == null) throw new IndexOutOfBoundsException("No hay partida");
        return array.get(0);
    }

}
