package com.hidatosdecarbono;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class PersistenciaCTRL {

    private PersistenciaJugador persistenciaJugador;
    private PersistenciaRanking persistenciaRanking;
    private PersistenciaHidato persistenciaHidato;
    private PersistenciaPartida persistenciaPartida;
    private final String ficheroJugadores = "jugadores.txt";
    private final String ficheroHidatosFacil = "hidatosFacil.txt";
    private final String ficheroHidatosMedio = "hidatosMedio.txt";
    private final String ficheroHidatosDificil = "hidatosDificil.txt";
    private final String ficheroRankingFacil = "rankingFacil.txt";
    private final String ficheroRankingMedio = "rankingMedio.txt";
    private final String ficheroRankingDificil = "rankingDificil.txt";
    private final String ficheroPartidas = "partidas.txt";
    /**
     * Constructora por defecto.Crea las instancias de las clases de persistencia.
     */
    public PersistenciaCTRL() {
        this.persistenciaRanking = new PersistenciaRanking();
        this.persistenciaJugador = new PersistenciaJugador();
        this.persistenciaHidato = new PersistenciaHidato();
        this.persistenciaPartida = new PersistenciaPartida();
    }

    /**
     * Guarda un nuevo jugador de forma persistente.
     * @param jugador Un Jugador que contiene el username y la contraseña
     * @throws InvalidUserException si el username del Jugador pasado por parametro ya existia.
     */
    public void guardaJugador (Jugador jugador) throws InvalidUserException, InvalidParameterException {
        Gson gson = new Gson();
        String json = gson.toJson(jugador);
        if(persistenciaJugador.obtenJugador(jugador.getUsername(), jugador.getPassword(), ficheroJugadores) != null)
            throw new InvalidUserException("No se puede guardar el jugador, ya existe otro con el mismo username");
        persistenciaJugador.guardaEnTxt(json, ficheroJugadores);
    }

    /**
     * Obtiene un Jugador de la persistencia, a partir de su username
     * @param username Un String que contiene el username del jugador que buscamos
     * @return jugador Jugador de la persistencia que tiene el mismo username que el pasado por parametro
     */
    public Jugador obtenJugador(String username, String password) throws InvalidUserException{
        Gson gson = new Gson();
        JSONObject jsonObj = persistenciaJugador.obtenJugador(username, password, ficheroJugadores);
        String json = null;
        if(jsonObj != null) json = jsonObj.toString();
        if(json == null) return null;
        return gson.fromJson(json,Jugador.class);
    }


    public void guardaRanking (Ranking ranking,Dificultad dificultad) {
        Gson gson = new Gson();
        String json = gson.toJson(ranking);
        switch (dificultad){
            case FACIL:
                persistenciaRanking.guardaEnTxt(json, ficheroRankingFacil);
                break;
            case MEDIO:
                persistenciaRanking.guardaEnTxt(json, ficheroRankingMedio);
                break;
            case DIFICIL:
                persistenciaRanking.guardaEnTxt(json, ficheroRankingDificil);
                break;
        }
    }

    public Ranking obtenRanking(Dificultad dificultad){
        Gson gson = new Gson();
        String json = null;
        switch (dificultad){
            case FACIL:
               json = persistenciaRanking.obtenRanking(ficheroRankingFacil);
                break;
            case MEDIO:
                json = persistenciaRanking.obtenRanking(ficheroRankingMedio);
                break;
            case DIFICIL:
                json = persistenciaRanking.obtenRanking(ficheroRankingDificil);
                break;
        }
        json = json.substring(1,json.length()-1); //feo pero efectivo -> Patron adaptacion ;D
        Ranking ranking = gson.fromJson(json,Ranking.class);
        return ranking;
    }

    public void guardaHidato (Hidato hidato){
        Gson gson = new Gson();
        String json = gson.toJson(hidato);
        json = añadeTipoHidato(json,hidato.getTipoHidato());
        Dificultad dificultad = hidato.getDificultad();
        switch (dificultad){
            case FACIL:
                persistenciaHidato.guardaEnTxt(json,ficheroHidatosFacil);
                break;
            case MEDIO:
                persistenciaHidato.guardaEnTxt(json,ficheroHidatosMedio);
                break;
            case DIFICIL:
                persistenciaHidato.guardaEnTxt(json,ficheroHidatosDificil);
                break;
        }
    }

    public Hidato obtenHidato (int id,Dificultad dificultad){
        Gson gson = new Gson();
        JSONObject json = null;
        switch (dificultad){
            case FACIL:
                json = persistenciaHidato.obtenHidato(id,ficheroHidatosFacil);
                break;
            case MEDIO:
                json = persistenciaHidato.obtenHidato(id,ficheroHidatosMedio);
                break;
            case DIFICIL:
                json = persistenciaHidato.obtenHidato(id,ficheroHidatosDificil);
                break;
        }
        System.out.println(json);
        return creaHidatoDeseJSON(json);
    }

    public HashMap<Integer, Hidato> obtenColeccionHidatos (Dificultad dificultad){
        Gson gson = new Gson();
        ArrayList<JSONObject> jsonArray = null;
        HashMap<Integer, Hidato> hashMap = new HashMap<>();
        switch (dificultad){
            case FACIL:
                jsonArray = persistenciaHidato.obtenColeccionHidatos(ficheroHidatosFacil);
                break;
            case MEDIO:
                jsonArray = persistenciaHidato.obtenColeccionHidatos(ficheroHidatosMedio);
                break;
            case DIFICIL:
                jsonArray = persistenciaHidato.obtenColeccionHidatos(ficheroHidatosDificil);
                break;
        }
        if(jsonArray!= null && !jsonArray.isEmpty()){
            for (int i = 0; i < jsonArray.size(); i++) {
                hashMap.put(i,creaHidatoDeseJSON(jsonArray.get(i)));
            }
            return hashMap;
        }
        return null;
    }

    public void guardaPartida (Partida partida){
        Gson gson = new Gson();
        String json = gson.toJson(partida);
        JSONObject jsonObject = new JSONObject(json);
        String jsonHidato =jsonObject.getJSONObject("hidatoJugado").toString();
        jsonHidato = añadeTipoHidato(jsonHidato,partida.getHidatoJugado().getTipoHidato());
        jsonObject.put("hidatoJugado",new JSONObject(jsonHidato));
        json = jsonObject.toString();
        System.out.println(json);
        persistenciaPartida.guardaEnTxt(json,ficheroPartidas);
    }

    public Partida obtenPartida (){
        Gson gson = new Gson();
        JSONObject json = persistenciaPartida.obtenPartida(ficheroPartidas);
        System.out.println(json);
        JSONObject hidatoJson = json.getJSONObject("hidatoJugado");
        System.out.println(hidatoJson);
        Hidato hidatoJugado = creaHidatoDeseJSON(hidatoJson);
        Celda [][] tablero = obtenerTablero(json.getJSONArray("tablero"));
        Partida partida = creaPartida(hidatoJugado,tablero,json);
        return partida;
    }

    private Partida creaPartida(Hidato hidatoJugado, Celda[][] tablero, JSONObject json) {
        Gson gson = new Gson();
        Jugador jugadorPartida = gson.fromJson(json.get("jugadorPartida").toString(),Jugador.class);
        Partida partida = new Partida(hidatoJugado,jugadorPartida);
        partida.setColActual(json.getInt("colActual"));
        partida.setFilaActual(json.getInt("filaActual"));
        partida.setTiempoPartida(json.getInt("tiempoPartida"));
        partida.setTiempoInicial(json.getInt("tiempoInicial"));
        partida.setTablero(tablero);
        partida.setNumPistas(json.getInt("numPistas"));
        partida.setN(json.getInt("n"));
        Stack<Movimiento> movimientos = new Stack<>();
        JSONArray arrayMovimientos = json.getJSONArray("movimientos");
        for (int i = 0; i < arrayMovimientos.length() ; i++) {
            Movimiento m = new Movimiento(arrayMovimientos.getJSONObject(i).getInt("i"),arrayMovimientos.getJSONObject(i).getInt("j"));
            movimientos.push(m);
        }
        partida.setMovimientos(movimientos);
        return partida;
    }


    private String añadeTipoHidato(String json,TipoHidato tipoHidato){
        JSONObject jsonObject = new JSONObject(json);
        jsonObject.put("tipo",tipoHidato);
        return jsonObject.toString();
    }

    private Hidato creaHidatoDeseJSON(JSONObject jsonObject){
        TipoHidato tipoHidato =  jsonObject.getEnum(TipoHidato.class,"tipo");
        TipoAdyacencia tipoAdyacencia = jsonObject.getEnum(TipoAdyacencia.class,"adyacencia");
        Dificultad dificultad = jsonObject.getEnum(Dificultad.class,"dificultad");
        Celda[][] tablero = obtenerTablero(jsonObject.getJSONArray("tablero"));
        int numFilas = tablero.length;
        int numColumnas = tablero[0].length;
        Hidato hidato = null;
        switch (tipoHidato){
            case TRIANGULAR:
                hidato = new HidatoTriangular(numFilas,numColumnas,tipoAdyacencia);
                break;
            case CUADRADO:
                hidato = new  HidatoCuadrado(numFilas,numColumnas,tipoAdyacencia);
                break;
            case HEXGONAL:
                hidato = new HidatoHexagonal(numFilas,numColumnas,tipoAdyacencia);
                break;
        }
        hidato.setDificultad(dificultad);
        hidato.setTablero(tablero);
        return hidato;
    }

    private Celda[][] obtenerTablero(JSONArray jsonArray){
        int nFilas = jsonArray.length();
        int nColumnas = jsonArray.getJSONArray(0).length();
        Celda tablero[][] = new Celda[nFilas][nColumnas];
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                Celda celda = new Celda();
                JSONObject jsonCelda = jsonArray.getJSONArray(i).getJSONObject(j);
                celda.setValor(jsonCelda.getInt("valor"));
                celda.setTipo(jsonCelda.getEnum(TipoCelda.class,"tipo"));
                tablero[i][j] = celda;
            }
        }
        return tablero;
    }




}
