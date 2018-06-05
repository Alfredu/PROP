package com.hidatosdecarbono;

import com.sun.org.apache.regexp.internal.RE;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class ConsultarRankingCTRL {
    private PersistenciaCTRL persistenciaCTRL;
    private Ranking ranking;
    private Dificultad dificultad;

    public ConsultarRankingCTRL(PersistenciaCTRL persistenciaCTRL, Dificultad dificultad){
        this.persistenciaCTRL = persistenciaCTRL;
        this.dificultad = dificultad;

    }

    public ArrayList<String> getEntradasRanking() {
        ArrayList<String> rankingRepr = new ArrayList<>();
        try{
            ranking = persistenciaCTRL.obtenRanking(dificultad);
            ArrayList<EntradaRanking> entradasRanking = ranking.getEntradasRanking();
            int n = 1;
            for(EntradaRanking entrada : entradasRanking) {
                String fila = "";
                fila = fila.concat(String.valueOf(n));
                fila = fila.concat("- ");
                fila = fila.concat((entrada.getUsername()));
                fila = fila.concat(": ");
                fila = fila.concat(String.valueOf(entrada.getPuntuacion()));
                rankingRepr.add(fila);
                n++;
            }
        }
        catch(NoSuchFileException e){
            rankingRepr.add("NO HI HA CAP ENTRADA PER AQUESTA DIFICULTAT");
            }
        return rankingRepr;
    }

}
