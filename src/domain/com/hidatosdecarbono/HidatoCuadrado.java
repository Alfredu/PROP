package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoCuadrado extends Hidato {




    public HidatoCuadrado(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.inicializaHidato(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    public HidatoCuadrado(int totalCaselles, TipoAdyacencia adyacencia){
        int numFilas = 2;
        int numColumnas = 2;
        int iteracio = 0;
        while(numColumnas*numFilas < totalCaselles){
            if(iteracio%2 == 0) numFilas++;
            else numColumnas++;
            iteracio++;
        }
        this.inicializaHidato(numFilas,numColumnas);
        this.setAdyacencia(adyacencia);
    }

    /**
     * Devuelve una lista con todas las adyacencias de un Nodo de posicion i,j.
     * @param i Integer con la fila de la Celda.
     * @param j Integer con la columna de la Celda.
     * @return Un ArrayList con todos los Nodos adyacentes a la Celda.
     */
    @Override
    public ArrayList<Node> getAdyacentes(int i, int j, Node nodo[][]){
        TipoAdyacencia adyacencia = this.getAdyacencia();
        ArrayList<Node> lista = new ArrayList<Node>();
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();


        if(i > 0 && nodo[i-1][j].noEsVacio()) lista.add(nodo[i-1][j]);
        if(i < nFilas-1 && nodo[i+1][j].noEsVacio()) lista.add(nodo[i+1][j]);
        if(j > 0 && nodo[i][j-1].noEsVacio()) lista.add(nodo[i][j-1]);
        if(j < nCols-1 && nodo[i][j+1].noEsVacio()) lista.add(nodo[i][j+1]);

        if(adyacencia.equals(TipoAdyacencia.LADOYVERTICE)){
            if(i > 0 && j > 0 && nodo[i-1][j-1].noEsVacio()) lista.add(nodo[i-1][j-1]);
            if(i < nFilas-1 && j > 0 && nodo[i+1][j-1].noEsVacio()) lista.add(nodo[i+1][j-1]);
            if(i > 0 && j < nCols-1 && nodo[i-1][j+1].noEsVacio()) lista.add(nodo[i-1][j+1]);
            if(i < nFilas-1 && j < nCols-1 && nodo[i+1][j+1].noEsVacio()) lista.add(nodo[i+1][j+1]);
        }

        return lista;
    }


    @Override
    public TipoHidato getTipoHidato() {
        return TipoHidato.CUADRADO;
    }
}
