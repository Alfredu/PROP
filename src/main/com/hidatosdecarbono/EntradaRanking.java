package com.hidatosdecarbono;

public class EntradaRanking implements Comparable{
    private Ranking ranking;
    private String username;
    private int puntuacion;

    /**
     * Creadora de EntradaRanking
     * @param ranking
     * @param username
     * @param tiempo
     * @param numPistas
     */
    public EntradaRanking(Ranking ranking, String username, int tiempo ,int numPistas) {
        this.ranking = ranking;
        this.username = username;
        this.puntuacion = calculaPuntuacion(tiempo,numPistas);
        ranking.actualizaMejorPuntuacion(this.puntuacion);
    }

    /**
     * Calcula la puntuacion a partir del tiempo y el numero de pistas
     * @param tiempo
     * @param numPistas
     * @return puntuacion
     */
    private int calculaPuntuacion(int tiempo, int numPistas) {
        return tiempo - 5*numPistas;
    }

    /**
     * Obtiene la puntuacion de la entradaRanking
     * @return puntuacion
     */
    public int getPuntuacion() {
        return this.puntuacion;
    }

    /**
     * Obtiene el username de la entradaRanking
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sobreescribimos la funcion de Comparable para poder ordenar EntradasRanking por puntuacion
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        return (((EntradaRanking) o).getPuntuacion() < this.puntuacion ? -1 : (this.puntuacion == ((EntradaRanking) o).getPuntuacion() ? 0 : 1));

    }

}
