package com.hidatosdecarbono;

public class HidatoHexagonal extends Hidato{

    public HidatoHexagonal(int numFila, int numColumnas, TipoAdyacencia tipo) throws IllegalArgumentException{
        if(tipo.equals(TipoAdyacencia.LADOYVERTICE)){
            throw new IllegalArgumentException("Solo se permite adyacencia de lado en un hidato hexagonal");
        }
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }
}
