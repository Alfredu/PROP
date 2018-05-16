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
        ranking.addEntradaRanking("user1",6*60,1);
        ranking.addEntradaRanking("user2",10*60,5);
        assertEquals("El numero de entradas del ranking es 2",2,ranking.getEntradasRanking().size());
    }

    @Test
    public void encuentraMejorEntradaUsuario() throws InvalidUserException {
        ranking.addEntradaRanking("user1",15*60,1);
        ranking.addEntradaRanking("user1",10*60,6);
        assertEquals("La mejor puntuacion de user1 es 1041",10000000/(15*60+1*60),ranking.getEntradaUsuario("user1").getPuntuacion());
    }
    @Test
    public void encuentraTop3MejoresEntradas() throws InvalidParameterException {
        ranking.addEntradaRanking("user1", 10 * 60, 1);
        ranking.addEntradaRanking( "user2", 11 * 60, 1);
        ranking.addEntradaRanking( "user3", 12 * 60, 1);
        ranking.addEntradaRanking( "user1", 13 * 60, 1);
        ranking.addEntradaRanking("user1", 9 * 60, 1);
        ArrayList<EntradaRanking> topMejores = ranking.getTopEntradaUsuario(3);
        assertEquals("Primero",10000000/(9*60+60),topMejores.get(0).getPuntuacion());
        assertEquals("Segundo",10000000/(10*60+60),topMejores.get(1).getPuntuacion());
        assertEquals("Tercero",10000000/(11*60+60),topMejores.get(2).getPuntuacion());
    }

    @Test
    public void entradasCorrectamenteOrdenadasPorPuntuacion() throws InvalidUserException {
        ranking.addEntradaRanking("user1",10*60,1);
        ranking.addEntradaRanking("user2",7*60,1);
        ranking.addEntradaRanking("user2",15*60,1);
        ranking.addEntradaRanking("user1",6*60,1);
        ArrayList<EntradaRanking> entradas = ranking.getEntradasRanking();
        for (int i=0;i< entradas.size()-1;i++){
            assertTrue(entradas.get(i).getPuntuacion() > entradas.get(i+1).getPuntuacion());
        }
    }



    @Test(expected = InvalidUserException.class)
    public void lanzaInvalidUserSiNoExisteElUsuarioEnRanking() throws InvalidUserException {
        ranking.addEntradaRanking("user1", 15 * 60, 1);
        ranking.getEntradaUsuario("invalidUser");
    }

    @Test
    public void topContieneEntradasVaciasSiSePidenMasEntradasDeLasExistentes() throws InvalidParameterException {
        ranking.addEntradaRanking( "user1", 10 * 60, 1);
        ranking.addEntradaRanking( "user2", 11 * 60, 1);
        ranking.addEntradaRanking( "user1", 13 * 60, 1);
        ArrayList<EntradaRanking> topMejores = ranking.getTopEntradaUsuario(4);
        assertEquals(null,topMejores.get(3).getUsername());
    }

    @Test(expected = IllegalStateException.class)
    public void lanzaIllegalStateSiConsultaMejorPuntuacionEnRankingVacio() throws InvalidUserException {
        ranking.getMejorPuntuacionRanking();
    }


}