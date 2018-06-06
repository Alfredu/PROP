package com.hidatosdecarbono;

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
    public void addEntradaRanking(String username, int tiempo , int penalizacion) {
        EntradaRanking entradaRanking = new EntradaRanking(username,tiempo,penalizacion);
        this.entradasRanking.add(entradaRanking);
        Collections.sort(entradasRanking);
    }

    /**
     * Devuelve una lista de tamaño indicado por el parametro que contiene las mejores entradasRanking del Ranking
     * @param top Un integer que contiene el tamaño de la lista a devolver
     * @return topMejores Un ArrayList que contiene las mejores entradas del ranking, o entradas vacias(username =null) hasta llegar al tamaño indicado
     */
    public ArrayList<EntradaRanking> getTopEntradaUsuario(int top) {
        ArrayList<EntradaRanking> topMejores = new ArrayList<>();
        EntradaRanking entradaVacia = new EntradaRanking(null,-1, 0);
        for (int i = 0; i < top; i++) {
            if(i<entradasRanking.size()) topMejores.add(entradasRanking.get(i));
            else topMejores.add(entradaVacia);
        }
        return topMejores;
    }

    /**
     * Devuelve el numero de EntradasRanking que tiene el ranking
     * @return Un integer que contiene el numero de entradas del ranking
     */
    public int getNumEntradasRanking(){
        return entradasRanking.size();
    }

}
