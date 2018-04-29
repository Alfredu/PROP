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
        entradasRanking = new ArrayList<>();
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
     * @param entradaRanking Una EntradaRanking que contiene la nueva entrada a añadir en el ranking
     */
    public void addEntradaRanking(EntradaRanking entradaRanking) {
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
     * @return topMejores Un ArrayList que contiene las mejores entradas del ranking
     * @throws InvalidParameterException si se intenta obtener una lista de mejores entradas mayor que el numero de entradas totales
     */
    public ArrayList<EntradaRanking> getTopEntradaUsuario(int top) throws InvalidParameterException {
        if(top > this.entradasRanking.size()) throw new InvalidParameterException();
        ArrayList<EntradaRanking> topMejores = new ArrayList<>();
        for (int i = 0; i < top; i++) {
            topMejores.add(entradasRanking.get(i));
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

}
