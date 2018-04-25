package com.hidatosdecarbono;

import java.util.ArrayList;

public class HidatoCuadrado extends Hidato {

    private Graph grafo;

    public HidatoCuadrado(int numFilas, int numColumnas, TipoAdyacencia adjacencia){
        this.setTablero(numFilas,numColumnas);
        this.setAdyacencia(adjacencia);
    }

    @Override
    public ArrayList<Celda> getAdyacentes(int i, int j){
        TipoAdyacencia adyacencia = this.getAdyacencia();
        ArrayList<Celda> lista = new ArrayList<Celda>();
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        if(i > 0 && this.getCeldaSolucion(i-1,j).esValida()) lista.add(this.getCeldaSolucion(i-1,j));
        if(i < nFilas-1 && this.getCeldaSolucion(i+1,j).esValida()) lista.add(this.getCeldaSolucion(i+1,j));
        if(j > 0 && this.getCeldaSolucion(i,j-1).esValida()) lista.add(this.getCeldaSolucion(i,j-1));
        if(j < nCols-1 && this.getCeldaSolucion(i,j+1).esValida()) lista.add(this.getCeldaSolucion(i,j+1));

        if(adyacencia.equals(TipoAdyacencia.LADOYVERTICE)){
            if(i > 0 && j > 0 && this.getCeldaSolucion(i-1,j-1).esValida()) lista.add(this.getCeldaSolucion(i-1,j-1));
            if(i < nFilas-1 && j > 0 && this.getCeldaSolucion(i+1,j-1).esValida()) lista.add(this.getCeldaSolucion(i+1,j-1));
            if(j < nCols-1 &&  i > 0 && this.getCeldaSolucion(i-1,j+1).esValida()) lista.add(this.getCeldaSolucion(i-1,j+1));
            if(j < nCols-1 && i < nFilas-1 && this.getCeldaSolucion(i+1,j+1).esValida()) lista.add(this.getCeldaSolucion(i+1,j+1));
        }

        return lista;
    }

    @Override
    public boolean tieneSolucion() {
        return false;
    }

    @Override
    public void creaGrafo() {
        copiaTablero();
        int id = 2;
        grafo = new Graph();

    }
}
