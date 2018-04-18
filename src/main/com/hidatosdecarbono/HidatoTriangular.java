package com.hidatosdecarbono;

public class HidatoTriangular extends Hidato {

    public HidatoTriangular(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.setTablero(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }
}
