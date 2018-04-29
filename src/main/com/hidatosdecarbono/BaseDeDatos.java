package com.hidatosdecarbono;

import com.hidatosdecarbono.Hidato;
import java.util.HashMap;
import java.util.Map;

class InfoUsuario{
    public String password;
    public Map<String,Hidato> mapHidatos = new HashMap<>();
}

public class BaseDeDatos {
    private Map<String,InfoUsuario> usersMap;

    public BaseDeDatos() {
        this.usersMap = new HashMap<>();
    }

    public Map<String, InfoUsuario> getUsersMap() {
        return usersMap;
    }

}