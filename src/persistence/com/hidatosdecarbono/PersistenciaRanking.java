package com.hidatosdecarbono;

import org.json.JSONObject;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class PersistenciaRanking extends Persistencia{

    public void guardaEnTxt(String json, String nombreFichero) {
        super.guardaEnTxt(json, nombreFichero, false);
    }

    public String obtenRanking(String nombreFichero) throws NoSuchFileException {
        ArrayList<JSONObject> JSON = obtenDeTxt(nombreFichero);
        if(JSON == null){
            throw new NoSuchFileException("No existeix el ranking");
        }
        else return JSON.toString();
    }

}
