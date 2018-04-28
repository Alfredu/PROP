package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoTriangular extends Hidato {

    public HidatoTriangular(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.setTablero(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    @Override
    public ArrayList<Node> getAdyacentes(int i, int j, Node[][] nodo) {
        return null;
    }


}
