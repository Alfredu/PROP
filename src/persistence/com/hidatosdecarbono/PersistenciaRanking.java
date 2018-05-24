package com.hidatosdecarbono;

import org.json.JSONObject;

import java.util.ArrayList;

public class PersistenciaRanking extends Persistencia{

    public void guardaEnTxt(String json, String nombreFichero) {
        super.guardaEnTxt(json, nombreFichero, false);
    }

    public String obtenRanking(String nombreFichero){
        return obtenDeTxt(nombreFichero).toString();
    }

}
