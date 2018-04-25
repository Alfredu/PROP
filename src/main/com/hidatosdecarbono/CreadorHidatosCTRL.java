package com.hidatosdecarbono;

import java.util.ArrayList;

public class CreadorHidatosCTRL {

    public boolean creaHidatoPropuesto(TipoHidato tipoHidato, int numFilas, int numColumnas, TipoAdjacencia tipoAdj, ArrayList <String> celdas){
        if(tipoHidato == TipoHidato.CUADRADO){
            HidatoQuadrado hq = new HidatoQuadrado(numFilas, numColumnas, tipoAdj);
            añadirCeldasHidato(hq,celdas);
            printHidato(hq);
        }
        return true; //TODO: return true si tiene solucion
    }

    /*public Hidato creaHidatoAleatorioParams(TipoHidato tipoHidato,int nCeldas,int nCeldasFijas,int nCeldasAgujero, TipoAdjacencia tipoAdjacencia){
        if(tipoHidato == TipoHidato.CUADRADO){
        }

    }*/

    private void añadirCeldasHidato(Hidato h, ArrayList <String> celdas) {
        int files = h.getFiles();
        int columnes = h.getColumnes();
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                String celda = celdas.get(0);
                celdas.remove(0);
                TipoCelda tipus = stringToCelda(celda);
                if(!tipus.equals(TipoCelda.FIJA)){
                    h.nuevaCelda(tipus,i,j);
                }
                else{
                    h.nuevaCelda(tipus,i,j,Integer.parseInt(celda));
                }
            }
        }
    }

    private TipoCelda stringToCelda(String s){
        if(s.equals("#")) return TipoCelda.AGUJERO;
        else if(s.equals("-")) return TipoCelda.INVISIBLE;
        else if(s.equals("?")) return TipoCelda.VARIABLE;
        else return TipoCelda.FIJA;
    }

    private void printHidato(Hidato h){
        int files = h.getFiles();
        int columnes = h.getColumnes();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = h.getCelda(i, j);
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
            if(c.hasValor()) return String.valueOf(c.getValor());
            else return "?";
        }
        else return String.valueOf(c.getValor());
    }


}