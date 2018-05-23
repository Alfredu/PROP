package com.hidatosdecarbono;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;


public class PersistenciaCTRL {

    private PersistenciaJugador persistenciaJugador;
    private PersistenciaRanking persistenciaRanking;
    private PersistenciaHidato persistenciaHidato;
    private final String ficheroJugadores = "jugadores.txt";
    private final String ficheroHidatos = "hidatos.txt";
    private final String ficheroRankingFacil = "rankingFacil.txt";
    private final String ficheroRankingMedio = "rankingMedio.txt";
    private final String ficheroRankingDificil = "rankingDificil.txt";

    /**
     * Constructora por defecto.Crea las instancias de las clases de persistencia.
     */
    public PersistenciaCTRL() {
        this.persistenciaRanking = new PersistenciaRanking();
        this.persistenciaJugador = new PersistenciaJugador();
        this.persistenciaHidato = new PersistenciaHidato();
    }

    /**
     * Guarda un nuevo jugador de forma persistente.
     * @param jugador Un Jugador que contiene el username y la contraseña
     * @throws InvalidUserException si el username del Jugador pasado por parametro ya existia.
     */
    public void guardaJugador (Jugador jugador) throws InvalidUserException {
        Gson gson = new Gson();
        String json = gson.toJson(jugador);
        if(persistenciaJugador.obtenJugador(jugador.getUsername(), ficheroJugadores) != null)
            throw new InvalidUserException("No se puede guardar el jugador, ya existe otro con el mismo username");
        persistenciaJugador.guardaEnTxt(json, ficheroJugadores);
    }

    /**
     * Obtiene un Jugador de la persistencia, a partir de su username
     * @param username Un String que contiene el username del jugador que buscamos
     * @return jugador Jugador de la persistencia que tiene el mismo username que el pasado por parametro
     */
    public Jugador obtenJugador(String username){
        Gson gson = new Gson();
        String json = persistenciaJugador.obtenJugador(username, ficheroJugadores).toString();
        Jugador jugador = gson.fromJson(json,Jugador.class);
        return jugador;
    }


    public void guardaRanking (Ranking ranking,Dificultad dificultad) {
        Gson gson = new Gson();
        String json = gson.toJson(ranking);
        System.out.println(json);
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
        System.out.println(json);
        Ranking ranking = gson.fromJson(json,Ranking.class);
        return ranking;
    }

    public void guardaHidato (Hidato hidato){
        Gson gson = new Gson();
        String json = gson.toJson(hidato);
        json = añadeTipoHidato(json,hidato.getTipoHidato());
        persistenciaHidato.guardaEnTxt(json,ficheroHidatos);
    }

    public Hidato obtenHidato (int id){
        Gson gson = new Gson();
        JSONObject json = persistenciaHidato.obtenHidato(id,ficheroHidatos);
        System.out.println(json);
        return creaHidatoDeseJSON(json);
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
