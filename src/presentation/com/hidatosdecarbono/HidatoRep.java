package com.hidatosdecarbono;

public class HidatoRep {
    public int id;
    public int nColumnas;
    public int nFilas;
    public TipoHidato forma;
    public TipoAdyacencia adyacencia;
    public String[][] tablero;
    public void setParams(int id, int nColumnas, int nFilas, TipoHidato forma, TipoAdyacencia adyacencia){
        this.id = id;
        this.nColumnas = nColumnas;
        this.nFilas = nFilas;
        this.forma = forma;
        this.adyacencia = adyacencia;
        this.tablero = new String[nFilas][nColumnas];
    }

}
