
package com.hidatosdecarbono;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RankingTest {

    private Ranking ranking;

    @Before
    public void setUp() throws Exception {
        ranking = new Ranking();
    }

    @Test
    public void añadeEntradaCorrectamente() {
        ranking.addEntradaRanking("user1",6*60,1, Dificultad.FACIL);
        ranking.addEntradaRanking("user2",10*60,5, Dificultad.DIFICIL);
        assertEquals("El numero de entradas del ranking es 2",2,ranking.getEntradasRanking().size());
    }

    @Test
    public void entradasCorrectamenteOrdenadasPorPuntuacion() throws InvalidUserException {
        ranking.addEntradaRanking("user1",10*60,30, Dificultad.DIFICIL);
        ranking.addEntradaRanking("user2",7*60,10, Dificultad.MEDIO);
        ranking.addEntradaRanking("user2",15*60,20,Dificultad.MEDIO);
        ranking.addEntradaRanking("user1",6*60,10, Dificultad.MEDIO);
        ArrayList<EntradaRanking> entradas = ranking.getEntradasRanking();
        for (int i=0;i< entradas.size()-1;i++){
            assertTrue(entradas.get(i).getPuntuacion() > entradas.get(i+1).getPuntuacion());
        }
    }


    @Test
    public void guardaYDevuelveEntradasRankingCorrectamente(){
        EntradaRanking entrada0 = new EntradaRanking("user1", 60,25, Dificultad.FACIL);
        EntradaRanking entrada1 = new EntradaRanking("user2", 100,10, Dificultad.FACIL);
        ranking.addEntradaRanking( "user1", 60, 25, Dificultad.FACIL);
        ranking.addEntradaRanking( "user2", 100, 10, Dificultad.FACIL);

        ArrayList<EntradaRanking> entradas = ranking.getEntradasRanking();
        assertEquals(entrada0.getUsername(),entradas.get(0).getUsername());
        assertEquals(entrada0.getPuntuacion(),entradas.get(0).getPuntuacion());

        assertEquals(entrada1.getUsername(),entradas.get(1).getUsername());
        assertEquals(entrada1.getPuntuacion(),entradas.get(1).getPuntuacion());



    }

}
