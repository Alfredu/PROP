package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoTriangular extends Hidato {

    public HidatoTriangular(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.inicializaHidato(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    public HidatoTriangular(int totalCaselles, TipoAdyacencia adyacencia){
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
     * Devuelve una lista de Nodos adyacente a la Celda de la posicion i,j
     * @param i Integer con la fila de la Celda
     * @param j Integer con la columna de la Celda
     * @param nodes Matriz de Nodos donde se comprueban las adyacencias.
     * @return ArrayList con los Nodos adyacentes a esa Celda.
     */
    @Override
    public ArrayList<Node> getAdyacentes(int i, int j, Node[][] nodes) {
        /*
        Una Celda triangular apunta hacia arriba si se encuentra en una fila par y columna par Ó
        si se encuentra en una fila impar i una columna impar

        Una Celda triangular apunta hacia abajo si se encuentra en una fila par y columna impar Ó
        si se encuentra en una fila impar y columna par.

        Por lo tanto sabemos que apunta hacia arriba si la paridad de fila y columna son distintas y viceversa
         */
        ArrayList<Node> lista = new ArrayList<Node>();
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        boolean haciaArriba = ((i%2) == (j%2));

        //Caso 1: Misma fila, casilla de la izquierda
        if(j>0 && nodes[i][j-1].noEsVacio()) lista.add(nodes[i][j-1]);

        //Caso 2: Misma fila, casilla de la derecha
        if(j<nCols-1 && nodes[i][j+1].noEsVacio()) lista.add(nodes[i][j+1]);

        //Adyacencias de LADO
        if(haciaArriba){
            if(i<nFilas-1 && nodes[i+1][j].noEsVacio()) lista.add(nodes[i+1][j]);
        }
        else{
            if(i>0 && nodes[i-1][j].noEsVacio()) lista.add(nodes[i-1][j]);
        }
        if(this.getAdyacencia() == TipoAdyacencia.LADOYVERTICE){
            //TODO no necesario para la primera entrega. Si sobra tiempo.
        }
        return lista;
    }


}
