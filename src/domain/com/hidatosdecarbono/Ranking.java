package com.hidatosdecarbono;

import org.omg.PortableInterceptor.DISCARDING;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {

    private ArrayList<EntradaRanking> entradasRanking;

    /**
     * Constructora por defecto
     */
    public Ranking() {
        entradasRanking = new ArrayList<EntradaRanking>();
    }

    /**
     * Devuelve todas entradas del ranking.
     * @return Un arrayList que contiene todas las entradas del ranking
     */
    public ArrayList<EntradaRanking> getEntradasRanking() {
        return entradasRanking;
    }

    /**
     * Añade una nueva EntradaRanking al Ranking y las reordena por puntuacion
     * @param
     * @param username Un string que contiene el username del jugador
     * @param tiempo Un integer que contiene el tiempo que el jugador ha tardado en resolver el hidato
     * @param penalizacion
     */
    public void addEntradaRanking(String username, int tiempo , int penalizacion, Dificultad dificultad) {
        EntradaRanking entradaRanking = new EntradaRanking(username,tiempo,penalizacion, dificultad);
        this.entradasRanking.add(entradaRanking);
        Collections.sort(entradasRanking);
    }

    /**
     * Devuelve el numero de EntradasRanking que tiene el ranking
     * @return Un integer que contiene el numero de entradas del ranking
     */
    public int getNumEntradasRanking(){
        return entradasRanking.size();
    }

}
