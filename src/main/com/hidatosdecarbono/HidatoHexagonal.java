package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoHexagonal extends Hidato{

    public HidatoHexagonal(int numFila, int numColumnas, TipoAdyacencia tipo) throws IllegalArgumentException{
        if(tipo.equals(TipoAdyacencia.LADOYVERTICE)){
            throw new IllegalArgumentException("Solo se permite adyacencia de lado en un hidato hexagonal");
        }
    }

    @Override
    public ArrayList<Node> getAdyacentes(int i, int j) {
        return null;
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }

    @Override
    public void creaGrafo() {

    }

    @Override
    public void creaNodos() {

    }
}
