package com.hidatosdecarbono;

import java.util.ArrayList;

public abstract class Hidato {
    private int id;
    private TipoAdyacencia adyacencia;
    private Celda[][] tablero;
    private Celda[][] solucion;
    private Node[][] nodo;
    private Graph grafo;

    /**
     *
     * @param id Un integer que contiene el id
     *           del Hidato
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @return Un integer que contiene el id del hidato
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return Un integer que contiene el numero de filas del hidato
     */
    public int getNumFilas(){
        return tablero.length;
    }

    /**
     *
     * @return Un integer que contiene el numero de columnas del hidato
     */
    public int getNumColumnas(){
        return tablero[0].length;
    }

    /**
     *
     * @param adyacencia Una enumeracion con el tipo de adyacencia que se quiere usar en el hidato
     */
    void setAdyacencia(TipoAdyacencia adyacencia) {
        this.adyacencia = adyacencia;
    }

    /**
     *
     * @return Una enumeracion con el tipo de adyacencia que usa el hidato
     */
    public TipoAdyacencia getAdyacencia() {
        return adyacencia;
    }


    /**
     *
     * @param numFilas Un integer con el numero de filas del tablero
     * @param numColumnas Un integer con el numero de columnas del tablero
     */
    void setTablero(int numFilas, int numColumnas){
        tablero = new Celda[numFilas][numColumnas];
        solucion = new Celda[numFilas][numColumnas];
        nodo = new Node[numFilas][numColumnas];
        grafo = new Graph();
    }

    public void nuevaCelda(TipoCelda tipo, int fila, int columna, int valor) throws IllegalArgumentException{
        try{
            tablero[fila][columna] = new Celda(tipo, valor);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    public void nuevaCelda(TipoCelda tipo, int fila, int columna) throws IllegalArgumentException{
        try{
            tablero[fila][columna] = new Celda(tipo);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    public Celda getCeldaTablero(int fila, int col){
        return tablero[fila][col];
    }

    public Celda getCeldaTableroSolucion(int fila, int col){
        return solucion[fila][col];
    }

    public Node getNodo(int i, int j){
        return nodo[i][j];
    }

    public void copiaTablero(Celda[][] newTablero){
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[i].length; j++){
                newTablero[i][j] = tablero[i][j].copiaCelda();
            }
        }
    }

    public abstract ArrayList<Node> getAdyacentes(int i, int j);

    public boolean tieneSolucion(){
        copiaTablero(solucion);
        creaNodos(nodo);
        creaGrafo(grafo);
        return grafo.esSolucionable();
    }

    public void creaGrafo(Graph grafoSolucion) {
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        for(int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nCols; j++) {
                if(nodo[i][j].noEsVacio()){
                    nodo[i][j].addListaAdyacencias(getAdyacentes(i,j));
                    grafoSolucion.addNode(nodo[i][j]);
                }
            }
        }
    }

    public void creaNodos(Node[][] tableroNodos) {
        int id = 2;
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        for(int i = 0; i < nFilas; i++){
            for (int j = 0; j < nCols; j++){
                Celda actual = this.getCeldaTableroSolucion(i,j);
                if(actual.esValida()){
                    if (actual.getValor() == 1){
                        tableroNodos[i][j] = new Node(1,actual);
                    }
                    else{
                        tableroNodos[i][j] = new Node(id,actual);
                        id++;
                    }
                }
                else tableroNodos[i][j] = new Node();
            }
        }
    }

}
