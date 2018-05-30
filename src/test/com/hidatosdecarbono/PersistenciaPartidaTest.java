package com.hidatosdecarbono;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PersistenciaPartidaTest {
    private PersistenciaCTRL persistenciaCTRL;
    private Hidato test;
    private Jugador jugadorAJugar;
    private Partida partida;

    @Before
    public void setUp() {
        this.persistenciaCTRL = new PersistenciaCTRL();
        test = new HidatoCuadrado(3, 4, TipoAdyacencia.LADOYVERTICE);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);
        test.decideDificultad();
        jugadorAJugar = new Jugador("Test", "1234");
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("persistencia/partidas.txt");
        file.delete();
    }

    @Test
    public void guardaPartidaCorrectamente() {
        partida = new Partida(test, jugadorAJugar);
        Celda[][] tauler = partida.getTablero();

        //ponemos el 2 al lado del 1 en la casilla 1,3
        partida.mueve(1, 3);
        //ponemos al 3 en la casilla adyacente al 2 situada en 0,2
        partida.mueve(0, 2);
        //ponemos el 4 en la casilla al lado del 3 situada en 0,1
        partida.mueve(0, 1);
        persistenciaCTRL.guardaPartida(partida);
        Partida partidaRestaurada = persistenciaCTRL.obtenPartida(0);
        partidaRestaurada.moonwalk();
        assertTrue(partidaRestaurada.mueve(0, 1));
        assertEquals(4, partidaRestaurada.getTablero()[0][1].getValor());
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