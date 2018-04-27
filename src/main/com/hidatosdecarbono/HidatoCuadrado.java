package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoCuadrado extends Hidato {




    public HidatoCuadrado(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.setTablero(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    /**
     * Devuelve una lista con todas las adyacencias de una casilla de posicion i,j (TODO Eloi aixo es correcte?)
     * @param i
     * @param j
     * @return
     */
    @Override
    public ArrayList<Node> getAdyacentes(int i, int j){
        TipoAdyacencia adyacencia = this.getAdyacencia();
        ArrayList<Node> lista = new ArrayList<Node>();
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();


        if(i > 0 && this.getNodo(i-1,j).noEsVacio()) lista.add(this.getNodo(i-1,j));
        if(i < nFilas-1 && this.getNodo(i+1,j).noEsVacio()) lista.add(this.getNodo(i+1,j));
        if(j > 0 && this.getNodo(i,j-1).noEsVacio()) lista.add(this.getNodo(i,j-1));
        if(j < nCols-1 && this.getNodo(i,j+1).noEsVacio()) lista.add(this.getNodo(i,j+1));

        if(adyacencia.equals(TipoAdyacencia.LADOYVERTICE)){
            if(i > 0 && j > 0 && this.getNodo(i-1,j-1).noEsVacio()) lista.add(this.getNodo(i-1,j-1));
            if(i < nFilas-1 && j > 0 && this.getNodo(i+1,j-1).noEsVacio()) lista.add(this.getNodo(i+1,j-1));
            if(i > 0 && j < nCols-1 && this.getNodo(i-1,j+1).noEsVacio()) lista.add(this.getNodo(i-1,j+1));
            if(i < nFilas-1 && j < nCols-1 && this.getNodo(i+1,j+1).noEsVacio()) lista.add(this.getNodo(i+1,j+1));
        }

        return lista;
    }








}
