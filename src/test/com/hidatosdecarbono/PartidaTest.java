package com.hidatosdecarbono;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PartidaTest {
    Hidato test;
    Jugador jugadorAJugar;
    Partida partida;

    @Before
    public void setup() {
        test = new HidatoCuadrado(3, 4, TipoAdyacencia.LADOYVERTICE);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);

        jugadorAJugar = new Jugador("Test", "1234");

    }

    @Test
    public void movimientosPosiblesSeMuestranYVuelvenTrue() {
        partida = new Partida(test, jugadorAJugar);
        Celda[][] tauler = partida.getTablero();

        //ponemos el 2 al lado del 1 en la casilla 1,3
        assertTrue(partida.mueve(1, 3));
        assertEquals(2, tauler[1][3].getValor());

        //ponemos al 3 en la casilla adyacente al 2 situada en 0,2
        assertTrue(partida.mueve(0, 2));
        assertEquals(3, tauler[0][2].getValor());

        //ponemos el 4 en la casilla al lado del 3 situada en 0,1
        assertTrue(partida.mueve(0, 1));
        assertEquals(4, tauler[0][1].getValor());
    }

    @Test
    public void movimientosImposiblesDevuelvenFalse() {
        partida = new Partida(test, jugadorAJugar);

        //intentamos poner el 2 en una casilla no adyacente al 1
        assertTrue(!partida.mueve(1, 0));

        //colocamos el 2 bien
        assertTrue(partida.mueve(1, 3));

        //intentamos poner el 3 en la casilla ya ocupada por el 2
        assertTrue(!partida.mueve(1, 3));

        //colocamos bien el 3
        assertTrue(partida.mueve(0, 2));

        //colocamos el 4 en una posicion incorrecta (casilla invisible)
        assertTrue(!partida.mueve(0, 3));
    }

    @Test
    public void retrocederPermiteRetrocederHastaLlegarAlInicio() {
        partida = new Partida(test, jugadorAJugar);
        Celda[][] tauler = partida.getTablero();

        //avanzamos unos cuantos movimientos correctos
        assertTrue(partida.mueve(1, 3));
        assertTrue(partida.mueve(0, 2));

        //en la posicion 0,2 hay un 3 antes de retroceder
        assertEquals(3, tauler[0][2].getValor());

        //se puede retroceder y en la casilla ocupada por un 3 encontraremos un 0
        assertTrue(partida.moonwalk());
        assertEquals(0, tauler[0][2].getValor());

        //se puede retroceder y en la casilla ocupada por un 2 encontramos un 0
        assertTrue(partida.moonwalk());
        assertEquals(0, tauler[1][3].getValor());

        //no se puede retroceder mas porque estamos en el origen
        assertTrue(!partida.moonwalk());
    }

    @Test
    public void pedirPistaNosDaUnaCasillaCorrectaSiExisteSolucion() {
        partida =new Partida(test, jugadorAJugar);
        Celda[][] tauler = partida.getTablero();

        //avanzamos un movimiento correcto
        assertTrue(partida.mueve(1, 3));

        //pedir pista devolvera true y pondra un 3 en 0,2
        assertTrue(partida.pidePista());
        assertEquals(3, tauler[0][2].getValor());

        //pedir pista devuelve true porque estamos en un camino con solucion
        assertTrue(partida.pidePista());
    }

    @Test
    public void pedirPistaDevuelveFalseSiNoExisteSolucion(){
        partida =new Partida(test, jugadorAJugar);
        Celda[][] tauler = partida.getTablero();

        //avanzamos un movimiento que no tiene solucion
        assertTrue(partida.mueve(0, 2));

        //pedir pista devolvera false entonces, porque no existe casilla siguiente ya que el camino no tiene solucion
        assertTrue(!partida.pidePista());
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