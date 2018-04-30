package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoHexagonal extends Hidato{

    public HidatoHexagonal(int numFila, int numColumnas, TipoAdyacencia tipo) throws IllegalArgumentException{
        if(tipo.equals(TipoAdyacencia.LADOYVERTICE)){
            throw new IllegalArgumentException("Solo se permite adyacencia de lado en un hidato hexagonal");
        }
        this.inicializaHidato(numFila, numColumnas);
        this.setAdyacencia(tipo);
    }

    public HidatoHexagonal(int totalCaselles, TipoAdyacencia adyacencia){
        if(adyacencia.equals(TipoAdyacencia.LADOYVERTICE)){
            throw new IllegalArgumentException("Solo se permite adyacencia de lado en un hidato hexagonal");
        }
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

    @Override
    public ArrayList<Node> getAdyacentes(int i, int j, Node[][] nodes) {
        ArrayList<Node> lista = new ArrayList<Node>();
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        //Caso 1: Misma fila, casilla de la izquierda
        if(j>0 && nodes[i][j-1].noEsVacio()) lista.add(nodes[i][j-1]);
        //Caso 2: Misma fila, casilla de la derecha
        if(j<nCols-1 && nodes[i][j+1].noEsVacio()) lista.add(nodes[i][j+1]);

        if(i%2==0){
            //Filas pares del hexagono

            //Caso 3: Fila de debajo, casilla justo debajo en el tablero, casilla de la derecha en el dibujo
            if(i<nFilas-1 && nodes[i+1][j].noEsVacio()) lista.add(nodes[i+1][j]);
            //Caso 4: Fila de debajo, casilla de la izquierda en el tablero, casilla de la izquierda en el dibujo
            if(i<nFilas-1 && j>0 && nodes[i+1][j-1].noEsVacio()) lista.add(nodes[i+1][j-1]);
            //Caso 5: Fila de arriba, casilla justo arriba en el tablero, casilla de la derecha en el dibujo
            if(i>0 && nodes[i-1][j].noEsVacio()) lista.add(nodes[i-1][j]);
            //Caso 6: Fila de arriba, casilla de la izquierda en el tablero, casilla de la izquierda en el dibujo
            if(i>0 && j>0 && nodes[i-1][j-1].noEsVacio()) lista.add(nodes[i-1][j-1]);

        }
        else{
            //Filas impares del hexagono (desplazadas a la derecha una posicion)

            //Caso 3: Fila de debajo, casilla justo debajo en el tablero, casilla de la izquierda en el dibujo
            if(i<nFilas-1 && nodes[i+1][j].noEsVacio()) lista.add(nodes[i+1][j]);
            //Caso 4: Fila de debajo, casilla de la izquierda en el tablero, casilla de la derecha en el dibujo
            if(i<nFilas-1 && j<nFilas-1 && nodes[i+1][j+1].noEsVacio()) lista.add(nodes[i+1][j+1]);
            //Caso 5: Fila de arriba, casilla justo arriba en el tablero, casilla de la izquierda en el dibujo
            if(i>0 && nodes[i-1][j].noEsVacio()) lista.add(nodes[i-1][j]);
            //Caso 6: Fila de arriba, casilla de la izquierda en el tablero, casilla de la derecha en el dibujo
            if(i>0 && j<nFilas-1 && nodes[i-1][j+1].noEsVacio()) lista.add(nodes[i-1][j+1]);

        }

        return lista;
    }


}