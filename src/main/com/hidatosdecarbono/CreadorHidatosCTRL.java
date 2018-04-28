package com.hidatosdecarbono;

import java.util.ArrayList;

public class CreadorHidatosCTRL {

    private Hidato hidatoCreado;

    public Hidato getHidatoCreado(){
        return hidatoCreado;
    }

    public boolean creaHidatoPropuesto(TipoHidato tipoHidato, int numFilas, int numColumnas, TipoAdyacencia tipoAdj, ArrayList <String> celdas){
        if(tipoHidato == TipoHidato.CUADRADO) {
            hidatoCreado = new HidatoCuadrado(numFilas, numColumnas, tipoAdj);
            añadirCeldasHidato(celdas);
        }
        return hidatoCreado.tieneSolucion();

    }

    /*public Hidato creaHidatoAleatorioParams(TipoHidato tipoHidato,int nCeldas,int nCeldasFijas,int nCeldasAgujero, TipoAdjacencia tipoAdjacencia){
        if(tipoHidato == TipoHidato.CUADRADO){
        }

    }*/

    private void añadirCeldasHidato(ArrayList <String> celdas) {
        int files = hidatoCreado.getNumFilas();
        int columnes = hidatoCreado.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String fila = celdas.get(i);
            String[] celda = fila.split(",");
            for (int j = 0; j < columnes; j++) {

                TipoCelda tipus = stringToCelda(celda[j]);
                if(!tipus.equals(TipoCelda.FIJA)){
                    hidatoCreado.nuevaCelda(tipus,i,j);
                }
                else{
                    hidatoCreado.nuevaCelda(tipus,i,j,Integer.valueOf(celda[j]));
                }
            }
        }
    }

    private TipoCelda stringToCelda(String s){
        if(s.equals("*")) return TipoCelda.AGUJERO;
        else if(s.equals("#")) return TipoCelda.INVISIBLE;
        else if(s.equals("?")) return TipoCelda.VARIABLE;
        else return TipoCelda.FIJA;
    }

    public void printHidato(){
        int files = hidatoCreado.getNumFilas();
        int columnes = hidatoCreado.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = hidatoCreado.getCeldaTablero(i, j);
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(" ");
            }
            System.out.println(celes);
        }
    }

    public void printSolucion(){
        int files = hidatoCreado.getNumFilas();
        int columnes = hidatoCreado.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = hidatoCreado.getCeldaTableroSolucion(i, j);
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(" ");
            }
            System.out.println(celes);
        }
    }

    private String celdaToString(Celda c){
        if(c.getTipo().equals(TipoCelda.AGUJERO)) return "#";
        else if(c.getTipo().equals(TipoCelda.INVISIBLE)) return "-";
        else if(c.getTipo().equals(TipoCelda.VARIABLE)){
            if(c.tieneValor()) return String.valueOf(c.getValor());
            else return "?";
        }
        else return String.valueOf(c.getValor());
    }


}
