package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoCuadrado extends Hidato {

    private Graph grafo;
    private Node[][] nodo;

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
    public boolean tieneSolucion() {
        return false;
    }

    @Override
    public void creaGrafo() {
        creaNodos();
        grafo = new Graph();
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        for(int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nCols; j++) {
                if(nodo[i][j].noEsVacio()){
                    nodo[i][j].addListaAdyacencias(getAdyacentes(i,j));
                    grafo.addNode(nodo[i][j]);
                }
            }
        }
    }

    @Override
    public void creaNodos() {
        copiaTablero();
        int id = 2;
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();
        nodo = new Node[nFilas][nCols];

        for(int i = 0; i < nFilas; i++){
            for (int j = 0; j < nCols; j++){
                Celda actual = this.getCeldaSolucion(i,j);
                if(actual.esValida()){
                    if (actual.getValor() == 1){
                        nodo[i][j] = new Node(1,actual);
                    }
                    else{
                        nodo[i][j] = new Node(id,actual);
                        id++;
                    }
                }
                else nodo[i][j] = new Node();
            }
        }
    }
}
