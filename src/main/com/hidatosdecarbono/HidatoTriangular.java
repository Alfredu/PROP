package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoTriangular extends Hidato {

    public HidatoTriangular(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.setTablero(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    @Override
    public ArrayList<Node> getAdyacentes(int i, int j, Node[][] nodes) {
        /*
        Una Celda triangular apunta hacia arriba si se encuentra en una fila par y columna par Ó
        si se encuentra en una fila impar i una columna impar

        Una Celda triangular apunta hacia abajo si se encuentra en una fila par y columna impar Ó
        si se encuentra en una fila impar y columna par.
         */
        ArrayList<Node> lista = new ArrayList<Node>();
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        //Caso 1: Misma fila, casilla de la izquierda
        if(j>0 && nodes[i][j-1].noEsVacio()) lista.add(nodes[i][j-1]);

        //Caso 2: Misma fila, casilla de la derecha
        if(j<nCols-1 && nodes[i][j+1].noEsVacio()) lista.add(nodes[i][j+1]);

        return lista;
    }


}
