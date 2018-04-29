package com.hidatosdecarbono;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RankingTest {

    private Ranking ranking;

    @Before
    public void setUp() throws Exception {
        ranking = new Ranking();
    }

    @Test
    public void añadeEntradaCorrectamente() {
        EntradaRanking entradaRanking1 = new EntradaRanking(ranking,"user1",6*60,1);
        EntradaRanking entradaRanking2 = new EntradaRanking(ranking,"user2",10*60,5);
        ranking.addEntradaRanking(entradaRanking1);
        ranking.addEntradaRanking(entradaRanking2);
        assertEquals("El numero de entradas del ranking es 2",2,ranking.getEntradasRanking().size());
    }

    @Test
    public void encuentraMejorEntradaUsuario() throws InvalidUserException {
        EntradaRanking entradaRanking1 = new EntradaRanking(ranking,"user1",15*60,1);
        EntradaRanking entradaRanking2 = new EntradaRanking(ranking,"user1",10*60,6);
        ranking.addEntradaRanking(entradaRanking1);
        ranking.addEntradaRanking(entradaRanking2);

        assertEquals("La mejor puntuacion de user1 es 1041",1000000/(15*60+1*60),ranking.getEntradaUsuario("user1").getPuntuacion());
    }
}