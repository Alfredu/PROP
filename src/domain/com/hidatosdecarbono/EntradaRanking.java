package com.hidatosdecarbono;

import java.util.ArrayList;

public class EntradaRanking implements Comparable{
    private String username;
    private int puntuacion;

    /**
     * Constructora para una EntradaRanking
     * @param username Un string que contiene el username del jugador de la nueva entradaRanking
     * @param tiempo Un integer que contiene el tiempo que el jugador ha tardado en resolver el hidato
     * @param penalizacion Un integer que contiene la penalizacion acumulada por el jugador durante la partida
     */
    public EntradaRanking(String username, int tiempo , int penalizacion) {
        this.username = username;
        this.puntuacion = calculaPuntuacion(tiempo, penalizacion);
    }

    /**
     * Devuelve la puntuacion calculada a partir del tiempo y el numero de pistas
     * @param tiempo Un integer que contiene el tiempo que el jugador ha tardado en resolver el hidato
     * @param penalizacion Un integer que contiene la penalizacion total de las pistas utilizdas
     * @return puntuacion Un integer que contiene la puntuacion correspondiente a la nueva entradaRanking
     */
    private int calculaPuntuacion(int tiempo, int penalizacion) {
        return  Math.floorDiv(10000000,(tiempo+penalizacion));
    }


    /**
     * Devuelve la puntuación de la entradaRanking
     * @return puntuacion Un integer que contiene la puntuacion correspondiente a la nueva entradaRanking
     */
    public int getPuntuacion() {
        return this.puntuacion;
    }

    /**
     * Devuelve el username de la entradaRanking
     * @return username Un string que contiene el username del jugador de la entradaRanking
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Devuelve si el objeto pasado por parametro es mayor,menor o igual que la entradaRanking.El objeto debe ser una EntradaRanking
     * @param o Un objeto que contiene la EntradaRanking del elemento a comparar
     * @return Un integer con valor -1 si es menor, 0 si es igual y 1 si es mayor la EntradaRanking pasada por parametro
     */
    @Override
    public int compareTo(Object o) {
        return (((EntradaRanking) o).getPuntuacion() < this.puntuacion ? -1 : (this.puntuacion == ((EntradaRanking) o).getPuntuacion() ? 0 : 1));

    }

}
