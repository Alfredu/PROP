package com.hidatosdecarbono;

public class HidatoHexagonal extends Hidato{

    public HidatoHexagonal(int fila, int col, TipoAdjacencia tipo) throws IllegalArgumentException{
        if(tipo.equals(TipoAdjacencia.LADOYVERTICE)){
            throw new IllegalArgumentException("Solo se permite adyacencia de lado en un hidato hexagonal");
        }
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }
}
