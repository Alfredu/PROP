package com.hidatosdecarbono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SeleccionarHidatosCTRL {
    private PersistenciaCTRL persistenciaCTRL;
    private JugarHidatosCTRL jugarHidatosCTRL;
    private LogInCTRL logInCTRL;
    private HashMap<Integer, Hidato> hidatos;
    private Integer[] keys;

    public SeleccionarHidatosCTRL(PersistenciaCTRL ctrl, JugarHidatosCTRL jugarHidatosCTRL, LogInCTRL logInCTRL){
        this.persistenciaCTRL = ctrl;
        this.jugarHidatosCTRL = jugarHidatosCTRL;
        this.logInCTRL = logInCTRL;
    }

    public ArrayList<HidatoRep> getHidatosDificultad(Dificultad dificultad){
        ArrayList<HidatoRep> hidatosDificultat = new ArrayList<>();

        hidatos = persistenciaCTRL.obtenColeccionHidatos(dificultad);
        if(hidatos != null) {
            keys = hidatos.keySet().toArray(new Integer[0]);

            //iterem sobre els hidatos i en generem la representació de cadascun d'ells
            for (int it = 0; it < keys.length; it++) {
                HidatoRep hidatoActualRep = new HidatoRep();
                Hidato hidatoActual = hidatos.get(keys[it]);
                int nColumnes = hidatoActual.getNumColumnas();
                int nFiles = hidatoActual.getNumFilas();

                hidatoActualRep.setParams(keys[it], nColumnes, nFiles,
                        hidatoActual.getTipoHidato(), hidatoActual.getAdyacencia());

                for (int i = 0; i < nFiles; i++) {
                    for (int j = 0; j < nColumnes; j++) {
                        hidatoActualRep.tablero[i][j] = celdaToString(hidatoActual.getCeldaTablero(i, j));
                    }
                }
                hidatosDificultat.add(hidatoActualRep);
            }
        }
        return hidatosDificultat;
    }

    public JugarHidatosCTRL getControladorPartida(int id) throws IllegalArgumentException{
        if(hidatos.keySet().contains(id)) {
            jugarHidatosCTRL.inicializa(hidatos.get(id), logInCTRL.getJugador());
            return jugarHidatosCTRL;
        }
        else throw new IllegalArgumentException("No existeix aquest id");
    }

    private String celdaToString(Celda c){
        if(c.getTipo().equals(TipoCelda.AGUJERO)) return "*";
        else if(c.getTipo().equals(TipoCelda.INVISIBLE)) return "#";
        else if(c.getTipo().equals(TipoCelda.VARIABLE)){
            if(c.tieneValor()) return String.valueOf(c.getValor());
            else return "?";
        }
        else return String.valueOf(c.getValor());
    }
}
