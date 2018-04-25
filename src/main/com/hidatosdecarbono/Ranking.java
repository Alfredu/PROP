package com.hidatosdecarbono;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {

    private int mejorPuntuacion;
    private ArrayList<EntradaRanking> entradasRanking;

    /**
     * Creadora de Ranking que inicializa la mejor puntuacion a 0
     */
    public Ranking() {
        this.mejorPuntuacion = 0;
    }

    /**
     * Actuliza la mejorPuntuacion del ranking
     * @param puntuacion
     */
    public void actualizaMejorPuntuacion(int puntuacion) {
        if(puntuacion> this.mejorPuntuacion) this.mejorPuntuacion = puntuacion;
    }

    /**
     * Añade una entradaRanking al Ranking
     * @param entradaRanking
     */
    public void addEntradaRanking(EntradaRanking entradaRanking) {
        this.entradasRanking.add(entradaRanking);
    }

    /**
     * Devuelve la entradaRanking de un usuario identificado por su username
     * @param username
     * @return entradaRanking
     * @throws InvalidUserException
     */
    public EntradaRanking getEntradaUsuario(String username) throws InvalidUserException {
        for( EntradaRanking entradaRanking : entradasRanking){
            if(entradaRanking.getUsername().equals(username)) return entradaRanking;
        }
        throw new InvalidUserException("El usuario no tiene ninguna entrada en este ranking");
    }

    /**
     * Devuelve una lista con top mejores entradasRanking del Ranking
     * @param top
     * @return topMejores
     * @throws InvalidParameterException
     */
    public ArrayList<EntradaRanking> getTopEntradaUsuario(int top) throws InvalidParameterException {
        if(top > this.entradasRanking.size()) throw new InvalidParameterException();
        ArrayList<EntradaRanking> topMejores = new ArrayList<>();
        Collections.sort(entradasRanking);
        for (int i = 0; i < top; i++) {
            topMejores.add(entradasRanking.get(i));
        }
        return topMejores;
    }

}
