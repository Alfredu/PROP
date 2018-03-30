package com.hidatosdecarbono;

public class HidatoQuadrado extends Hidato {
    private Celda[][] tablero;

    public HidatoQuadrado(int files, int columnes, TipoAdjacencia adjacencia){
        tablero = new Celda[files][columnes];
        this.setAdjacencia(adjacencia);
    }

    public void nuevaCelda(TipoCelda tipo, int valor){

    }
}
