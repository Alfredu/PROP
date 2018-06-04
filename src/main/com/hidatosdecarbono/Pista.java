package com.hidatosdecarbono;

public abstract class Pista {
    private int penalitzacio;

    protected void setPenalitzacio(int penalitzacio) {
        this.penalitzacio = penalitzacio;
    }

    public int getPenalitzacio(){
        return this.penalitzacio;
    }
}
