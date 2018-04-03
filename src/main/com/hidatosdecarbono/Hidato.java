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
        if(tipo.equals(TipoCelda.FIJA)){
            tablero[fila][columna] = new Celda(tipo,valor);
        }
        else{
            throw new IllegalArgumentException("La celda no puede tener valor");
        }
    }

    public void nuevaCelda(TipoCelda tipo, int fila, int columna) throws IllegalArgumentException{
        if(tipo.equals(TipoCelda.FIJA)){
            throw new IllegalArgumentException("Una celda fija tiene que tener valor");
        }
        else{
            tablero[fila][columna] = new Celda(tipo);
        }
    }


    public abstract boolean tieneSolucion();

}
