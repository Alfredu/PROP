package com.hidatosdecarbono;

public abstract class Hidato {
    private int id;
    private TipoAdjacencia adjacencia;


    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAdjacencia(TipoAdjacencia adjacencia) {
        this.adjacencia = adjacencia;
    }

    public TipoAdjacencia getAdjacencia() {
        return adjacencia;
    }


}
