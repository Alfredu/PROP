package com.hidatosdecarbono;

import java.util.ArrayList;

public class CreadorHidatosCTRL {

    public boolean creaHidatoPropuesto(TipoHidato tipoHidato, int numFilas, int numColumnas, TipoAdyacencia tipoAdj, ArrayList <String> celdas){
        if(tipoHidato == TipoHidato.CUADRADO){
            HidatoCuadrado hq = new HidatoCuadrado(numFilas, numColumnas, tipoAdj);
            añadirCeldasHidato(hq,celdas);
            printHidato(hq);

            if(hq.tieneSolucion()) {
                printSolucion(hq);
            }
        }
        return true; //TODO: return true si tiene solucion
    }

    /*public Hidato creaHidatoAleatorioParams(TipoHidato tipoHidato,int nCeldas,int nCeldasFijas,int nCeldasAgujero, TipoAdjacencia tipoAdjacencia){
        if(tipoHidato == TipoHidato.CUADRADO){
        }

    }*/

    private void añadirCeldasHidato(Hidato h, ArrayList <String> celdas) {
        int files = h.getNumFilas();
        int columnes = h.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String fila = celdas.get(i);
            for (int j = 0; j < columnes; j++) {
                char celda = fila.charAt(j);
                TipoCelda tipus = charToCelda(celda);
                if(!tipus.equals(TipoCelda.FIJA)){
                    h.nuevaCelda(tipus,i,j);
                }
                else{
                    h.nuevaCelda(tipus,i,j,Character.getNumericValue(celda));
                }
            }
        }
    }

    private TipoCelda charToCelda(char c){
        if(c == '#') return TipoCelda.AGUJERO;
        else if(c =='-') return TipoCelda.INVISIBLE;
        else if(c =='?') return TipoCelda.VARIABLE;
        else return TipoCelda.FIJA;
    }

    private void printHidato(Hidato h){
        int files = h.getNumFilas();
        int columnes = h.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = h.getCeldaTablero(i, j);
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(" ");
            }
            System.out.println(celes);
        }
    }

    private void printSolucion(Hidato h){
        int files = h.getNumFilas();
        int columnes = h.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = h.getCeldaTableroSolucion(i, j);
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
