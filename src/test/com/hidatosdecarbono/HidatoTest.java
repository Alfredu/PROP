package com.hidatosdecarbono;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HidatoTest {
    Hidato test;


    @Test
    void sePuedenAñadirCeldasAlHidato(){
        test = new HidatoCuadrado(3,3,TipoAdyacencia.LADO);
        test.nuevaCelda(TipoCelda.FIJA,0,0,5);
        test.nuevaCelda(TipoCelda.AGUJERO,0,1);

        assertEquals(5,test.getCeldaTablero(0,0).getValor());
        assertTrue(test.getCeldaTablero(0,0).getTipo().equals(TipoCelda.FIJA));
        assertTrue(test.getCeldaTablero(0,1).getTipo().equals(TipoCelda.AGUJERO));
    }

    @Test
    void unHidatoConSolucionDevuelveTrueAlLlamarEsSolucionable(){
        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);
        assertTrue(test.tieneSolucion());

        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        celdas = new ArrayList<>();
        celdas.add("#,1,?,#");
        celdas.add("8,6,?,4");
        celdas.add("9,?,?,#");
        añadirCeldasHidato(celdas);
        assertTrue(test.tieneSolucion());

        test = new HidatoCuadrado(3,3,TipoAdyacencia.LADO);
        celdas = new ArrayList<>();
        celdas.add("#,1,?");
        celdas.add("?,?,3");
        celdas.add("8,?,?");
        añadirCeldasHidato(celdas);
        assertTrue(test.tieneSolucion());
    }

    @Test
    void unHidatoSinSolucionDevuelveFalseAlLlamarEsSolucionable(){
        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,1,?,#");
        celdas.add("?,?,?,2");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);
        //no tiene solucion porque la casilla con un 2 no es adyacente a la casilla con un 1

        assertTrue(!test.tieneSolucion());

        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        celdas = new ArrayList<>();
        celdas.add("1,?,?,#");
        celdas.add("*,*,*,*");
        celdas.add("6,?,4,#");
        añadirCeldasHidato(celdas);
        //no tiene solucion porque desde la fila superior no se pude acceder a la fila inferior

        assertTrue(!test.tieneSolucion());

        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        celdas = new ArrayList<>();
        celdas.add("1,?,?,#");
        celdas.add("?,*,?,?");
        celdas.add("7,8,9,#");
        añadirCeldasHidato(celdas);
        //no tiene solucion porque es imposible colocar el 6 en una casilla adyacente al 7
        assertTrue(!test.tieneSolucion());

        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADO);
        celdas = new ArrayList<>();
        celdas.add("#,1,?,#");
        celdas.add("?,?,?,4");
        celdas.add("9,?,?,#");
        añadirCeldasHidato(celdas);
        /*no tiene solucion porque desde la casilla con un 4 no se puede colocar el 5 ya que la unica casilla adyacente a la 4
            es ocupada por un 3
         */
        assertTrue(!test.tieneSolucion());
    }

    @Test
    void sePuedeVerSolucionDeUnHidatoSolucionable(){
        test = new HidatoCuadrado(3,3,TipoAdyacencia.LADO);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,1,?");
        celdas.add("?,?,3");
        celdas.add("8,?,?");
        añadirCeldasHidato(celdas);
        test.tieneSolucion();
        /*
        S'espera el hidato seguent #,1,2
                                   7,6,3
                                   8,5,4
         */
        assertEquals(1,test.getCeldaTableroSolucion(0,1).getValor());
        assertEquals(2,test.getCeldaTableroSolucion(0,2).getValor());
        assertEquals(3,test.getCeldaTableroSolucion(1,2).getValor());
        assertEquals(4,test.getCeldaTableroSolucion(2,2).getValor());
        assertEquals(5,test.getCeldaTableroSolucion(2,1).getValor());
        assertEquals(6,test.getCeldaTableroSolucion(1,1).getValor());
        assertEquals(7,test.getCeldaTableroSolucion(1,0).getValor());
        assertEquals(8,test.getCeldaTableroSolucion(2,0).getValor());


        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        celdas = new ArrayList<>();
        celdas.add("#,1,?,#");
        celdas.add("8,6,?,4");
        celdas.add("9,?,?,#");
        añadirCeldasHidato(celdas);
        test.tieneSolucion();
        /*
         S'espera el hidato seguent #,1,2,#
                                    8,6,3,4
                                    9,7,5,#
         */
        assertEquals(1,test.getCeldaTableroSolucion(0,1).getValor());
        assertEquals(2,test.getCeldaTableroSolucion(0,2).getValor());
        assertEquals(3,test.getCeldaTableroSolucion(1,2).getValor());
        assertEquals(4,test.getCeldaTableroSolucion(1,3).getValor());
        assertEquals(5,test.getCeldaTableroSolucion(2,2).getValor());
        assertEquals(6,test.getCeldaTableroSolucion(1,1).getValor());
        assertEquals(7,test.getCeldaTableroSolucion(2,1).getValor());
        assertEquals(8,test.getCeldaTableroSolucion(1,0).getValor());
        assertEquals(9,test.getCeldaTableroSolucion(2,0).getValor());
    }




    private void añadirCeldasHidato(ArrayList<String> celdas) {
        int files = test.getNumFilas();
        int columnes = test.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String fila = celdas.get(i);
            String[] celda = fila.split(",");
            for (int j = 0; j < columnes; j++) {

                TipoCelda tipus = stringToCelda(celda[j]);
                if(!tipus.equals(TipoCelda.FIJA)){
                    test.nuevaCelda(tipus,i,j);
                }
                else{
                    test.nuevaCelda(tipus,i,j,Integer.valueOf(celda[j]));
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

}