package com.hidatosdecarbono;

public class HidatoQuadrado extends Hidato {

    public HidatoQuadrado(int fila, int col, TipoAdjacencia adjacencia){
        this.setTablero(fila,col);
        this.setAdjacencia(adjacencia);
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }
}