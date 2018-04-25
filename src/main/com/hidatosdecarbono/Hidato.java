package com.hidatosdecarbono;

public abstract class Hidato {
    private int id;
    private TipoAdyacencia adyacencia;
    private Celda[][] tablero;



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

    public Celda getCelda(int fila, int col){
        return tablero[fila][col];
    }

    public abstract boolean tieneSolucion();

    public abstract void creaGrafo();

}
