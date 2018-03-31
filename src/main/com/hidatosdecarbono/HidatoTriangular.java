package com.hidatosdecarbono;

public class HidatoTriangular extends Hidato {

    public HidatoTriangular(int fila, int col, TipoAdjacencia adjacencia){
        this.setTablero(fila,col);
        this.setAdjacencia(adjacencia);
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }
}
