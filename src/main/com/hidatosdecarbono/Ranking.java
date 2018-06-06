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
     * @param pistas
     */
    public void addEntradaRanking(String username, int tiempo , int pistas) {
        EntradaRanking entradaRanking = new EntradaRanking(username,tiempo,pistas);
        this.entradasRanking.add(entradaRanking);
        Collections.sort(entradasRanking);
    }

    /**
     * Devuelve la mejor entradaRanking de un usuario identificado por su username
     * @param username Un string que contiene el username del jugador
     * @return entradaRanking Una EntradaRanking que contiene la mejor entrada del usuario en el ranking
     * @throws InvalidUserException si se intenta buscar una entrada para un username que no tiene entradas en el ranking
     */
    public EntradaRanking getEntradaUsuario(String username) throws InvalidUserException {
        for( EntradaRanking entradaRanking : entradasRanking){
            if(entradaRanking.getUsername().equals(username)) return entradaRanking;
        }
        throw new InvalidUserException("El usuario no tiene ninguna entrada en este ranking");
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
     * Devuelve la mejor puntuacion del ranking
     * @return  Un integer que contiene la puntuacion mas alta del ranking
     * @throws IllegalStateException si no hay entradas en el ranking
     */
    public int getMejorPuntuacionRanking() throws IllegalStateException {
        if(entradasRanking.size()>0) return entradasRanking.get(0).getPuntuacion();
        throw new IllegalStateException("No hay entradas en el ranking");
    }

    /**
     * Devuelve el numero de EntradasRanking que tiene el ranking
     * @return Un integer que contiene el numero de entradas del ranking
     */
    public int getNumEntradasRanking(){
        return entradasRanking.size();
    }

}
