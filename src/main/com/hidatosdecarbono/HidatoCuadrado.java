package com.hidatosdecarbono;

public class HidatoCuadrado extends Hidato {

    public HidatoCuadrado(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.setTablero(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }
}
