package com.hidatosdecarbono;

import java.util.ArrayList;

public class ConsultarRankingCTRL {
    private PersistenciaCTRL persistenciaCTRL;
    private Ranking ranking;

    public ConsultarRankingCTRL(PersistenciaCTRL persistenciaCTRL, Dificultad dificultad){
        this.persistenciaCTRL = persistenciaCTRL;

        ranking = persistenciaCTRL.obtenRanking(dificultad);

    }

    public void getEntradasRanking() {
        ArrayList<EntradaRanking> entradasRanking = ranking.getEntradasRanking();
        int n = 1;
        for(EntradaRanking entrada : entradasRanking){
            System.out.print(n);
            System.out.print("- ");
            System.out.print(entrada.getUsername());
            System.out.print(" :  ");
            System.out.println(entrada.getPuntuacion());
            n++;
        }
    }

}
