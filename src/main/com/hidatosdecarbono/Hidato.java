package com.hidatosdecarbono;

public abstract class Hidato {
    private int id;
    private TipoAdjacencia adjacencia;
    private Celda[][] tablero;


    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    void setAdjacencia(TipoAdjacencia adjacencia) {
        this.adjacencia = adjacencia;
    }

    public TipoAdjacencia getAdjacencia() {
        return adjacencia;
    }

    void setTablero(int fila, int col){
        tablero = new Celda[fila][col];
    }

    public void nuevaCelda(TipoCelda tipo, int valor, int fila, int columna) throws IllegalArgumentException{
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


    public abstract boolean tieneSolucion();

}
