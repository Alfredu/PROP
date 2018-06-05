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

    /**
     Devuelve las entradas del ranking asociado a ese controlador en el caso de que haya alguna, sino devuelve una excepcion
     * @return ArrayList de Strings donde cada string representa una entrada del ranking si existen
     * @throws NoSuchFileException Excepcion que se lanza si el ranking no contiene ninguna entrada
     */
    public ArrayList<String> getEntradasRanking() throws NoSuchFileException {
        ArrayList<String> rankingRepr = new ArrayList<>();

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

        return rankingRepr;
    }

}
