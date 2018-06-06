package com.hidatosdecarbono;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PersistenciaCTRLTest {
    private PersistenciaCTRL persistenciaCTRL;
    private Hidato test;
    private Jugador jugadorAJugar;
    private Partida partida;

    @Before
    public void setUp() {
     this.persistenciaCTRL = new PersistenciaCTRL();
    }

    @After
    public void tearDown() throws Exception {
        File fileJugadores = new File("persistencia/jugadores.txt");
        File fileHidatoDificil = new File("persistencia/hidatosDificil.txt");
        File filePartidas = new File("persistencia/partidas.txt");
        File fileRankingFacil = new File("persistencia/rankingFacil.txt");
        File fileRankingMedio= new File("persistencia/rankingMedio.txt");
        File fileRankingDificil = new File("persistencia/rankingDificil.txt");
        fileJugadores.delete();
        fileHidatoDificil.delete();
        //filePartidas.delete();
        fileRankingFacil.delete();
        fileRankingMedio.delete();
        fileRankingDificil.delete();
    }

    @Test
    public void guardaTresJugadoresCorrectamente() throws InvalidUserException, Exception{
        Jugador jugador1 = new Jugador("user1","1");
        Jugador jugador2 = new Jugador("user2","2");
        Jugador jugador3 = new Jugador("user3","3");
        persistenciaCTRL.guardaJugador(jugador1);
        persistenciaCTRL.guardaJugador(jugador2);
        persistenciaCTRL.guardaJugador(jugador3);
        String text[] = {"{\"username\":\"user1\",\"password\":\"1\"}"
                            ,"{\"username\":\"user2\",\"password\":\"2\"}"
                            ,"{\"username\":\"user3\",\"password\":\"3\"}"};
        assertTrue(mismoTextoEnFichero(text,"jugadores.txt"));
    }


    @Test
    public void encuentraJugadorExistente() throws InvalidUserException, Exception{
        Jugador jugador1 = new Jugador("user1","1");
        Jugador jugador2 = new Jugador("user2","2");
        Jugador jugador3 = new Jugador("user3","3");
        persistenciaCTRL.guardaJugador(jugador1);
        persistenciaCTRL.guardaJugador(jugador2);
        persistenciaCTRL.guardaJugador(jugador3);
        Jugador jugadorObtenido = persistenciaCTRL.obtenJugador(jugador2.getUsername());
        assertEquals("Ha encontrado el user2",jugador2.getUsername(),jugadorObtenido.getUsername());
    }

    @Test (expected = InvalidUserException.class)
    public void lanzaExcepcionSiJugadorYaExiste() throws InvalidUserException {
        Jugador jugador1 = new Jugador("user1","1");
        Jugador jugador2 = new Jugador("user2","2");
        Jugador jugador3 = new Jugador("user1","3");
        persistenciaCTRL.guardaJugador(jugador1);
        persistenciaCTRL.guardaJugador(jugador2);
        persistenciaCTRL.guardaJugador(jugador3);
    }


    @Test
    public void guardaHidatoCorrectamente() {
        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        test.setDificultad(Dificultad.DIFICIL);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);
        persistenciaCTRL.guardaHidato(test);
        String[] texto = {"{\"tipo\":\"CUADRADO\"," +
                "\"adyacencia\":\"LADOYVERTICE\"," +
                "\"id\":0," +
                "\"tablero\":[[{\"tipo\":\"INVISIBLE\",\"valor\":0},{\"tipo\":\"VARIABLE\",\"valor\":0},{\"tipo\":\"VARIABLE\",\"valor\":0},{\"tipo\":\"INVISIBLE\",\"valor\":0}],[{\"tipo\":\"VARIABLE\",\"valor\":0},{\"tipo\":\"VARIABLE\",\"valor\":0},{\"tipo\":\"FIJA\",\"valor\":1},{\"tipo\":\"VARIABLE\",\"valor\":0}],[{\"tipo\":\"FIJA\",\"valor\":7},{\"tipo\":\"VARIABLE\",\"valor\":0},{\"tipo\":\"FIJA\",\"valor\":9},{\"tipo\":\"INVISIBLE\",\"valor\":0}]]," +
                "\"nFilas\":3," +
                "\"dificultad\":\"DIFICIL\"," +
                "\"nColumnas\":4}"};
        assertTrue(mismoTextoEnFichero(texto,"hidatosDificil.txt"));
    }

    @Test
    public void obtieneHidatoDificilCorrectamente() {
        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        test.setDificultad(Dificultad.DIFICIL);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);
        test.tieneSolucion();
        persistenciaCTRL.guardaHidato(test);
        Hidato hidato = persistenciaCTRL.obtenHidato(0,Dificultad.DIFICIL);
        assertTrue(hidato.tieneSolucion());
    }

    @Test
    public void obtieneColeccionHidatosDificilCorrectamente() {
        //Hidato 1
        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        test.setDificultad(Dificultad.DIFICIL);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);
        test.tieneSolucion();
        persistenciaCTRL.guardaHidato(test);

        //Hidato 2
        test = new HidatoCuadrado(3,4,TipoAdyacencia.LADOYVERTICE);
        test.setDificultad(Dificultad.DIFICIL);
        celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("9,?,7,#");
        añadirCeldasHidato(celdas);
        test.tieneSolucion();
        persistenciaCTRL.guardaHidato(test);

        HashMap<Integer, Hidato> hashMapHidatos = persistenciaCTRL.obtenColeccionHidatos(Dificultad.DIFICIL);
        assertTrue(hashMapHidatos.get(0).tieneSolucion());
        assertTrue(hashMapHidatos.get(1).tieneSolucion());
    }


    @Test
    public void guardaRankingFacilOrdenadoCorrectamente() {
        Ranking ranking = new Ranking();
        ranking.addEntradaRanking("User1",350,2*60);
        ranking.addEntradaRanking("User2",355,2*60);
        ranking.addEntradaRanking("User3",100,4*60);
        persistenciaCTRL.guardaRanking(ranking,Dificultad.FACIL);
        String[] texto = {"{\"entradasRanking\":[{\"username\":\"User3\",\"puntuacion\":29411},{\"username\":\"User1\",\"puntuacion\":21276},{\"username\":\"User2\",\"puntuacion\":21052}]}"};
        assertTrue("Se ha guardado el ranking facil corectamente",mismoTextoEnFichero(texto,"rankingFacil.txt"));
    }

    @Test
    public void guardaRankingMedioOrdenadoCorrectamente() {
        Ranking ranking = new Ranking();
        ranking.addEntradaRanking("User11",350,2*60);
        ranking.addEntradaRanking("User22",355,2*60);
        ranking.addEntradaRanking("User33",100,4*60);
        persistenciaCTRL.guardaRanking(ranking,Dificultad.MEDIO);
        String[] texto = {"{\"entradasRanking\":[{\"username\":\"User33\",\"puntuacion\":29411},{\"username\":\"User11\",\"puntuacion\":21276},{\"username\":\"User22\",\"puntuacion\":21052}]}"};
        assertTrue("Se ha guardado el ranking medio corectamente",mismoTextoEnFichero(texto,"rankingMedio.txt"));
    }

    @Test
    public void guardaRankingDificillOrdenadoCorrectamente() {
        Ranking ranking = new Ranking();
        ranking.addEntradaRanking("User100",350,120);
        ranking.addEntradaRanking("User200",355,120);
        ranking.addEntradaRanking("User300",100,240);
        persistenciaCTRL.guardaRanking(ranking,Dificultad.DIFICIL);
        String[] texto = {"{\"entradasRanking\":[{\"username\":\"User300\",\"puntuacion\":29411},{\"username\":\"User100\",\"puntuacion\":21276},{\"username\":\"User200\",\"puntuacion\":21052}]}"};
        assertTrue("Se ha guardado el ranking facil corectamente",mismoTextoEnFichero(texto,"rankingDificil.txt"));
    }

    @Test
    public void obtieneRankingDificillDesdeFicheroCorrectamente() throws NoSuchFileException {
        Ranking ranking = new Ranking();
        ranking.addEntradaRanking("User100",350,2);
        ranking.addEntradaRanking("User200",355,2);
        ranking.addEntradaRanking("User300",100,4);
        persistenciaCTRL.guardaRanking(ranking,Dificultad.DIFICIL);
        Ranking retRanking = persistenciaCTRL.obtenRanking(Dificultad.DIFICIL);
        boolean sameRanking = false;
        if(ranking.getNumEntradasRanking() == retRanking.getNumEntradasRanking()) {
            sameRanking = true;
            for (int i = 0; i < ranking.getNumEntradasRanking(); i++) {
                if (!ranking.getEntradasRanking().get(i).getUsername().equals(retRanking.getEntradasRanking().get(i).getUsername()) ||
                        ranking.getEntradasRanking().get(i).getPuntuacion() != retRanking.getEntradasRanking().get(i).getPuntuacion()) {
                    sameRanking = false;
                    break;
                }
            }
        }
        assertTrue(sameRanking);
    }


    @Test
    public void obtinePartidaGuardadaCorrectamente() {
        test = new HidatoCuadrado(3, 4, TipoAdyacencia.LADOYVERTICE);
        ArrayList<String> celdas = new ArrayList<>();
        celdas.add("#,?,?,#");
        celdas.add("?,?,1,?");
        celdas.add("7,?,9,#");
        añadirCeldasHidato(celdas);
        test.decideDificultad();
        jugadorAJugar = new Jugador("Test", "1234");

        partida = new Partida(test, jugadorAJugar);
        Celda[][] tauler = partida.getTablero();

        //ponemos el 2 al lado del 1 en la casilla 1,3
        partida.mueve(1, 3);
        //ponemos al 3 en la casilla adyacente al 2 situada en 0,2
        partida.mueve(0, 2);
        //ponemos el 4 en la casilla al lado del 3 situada en 0,1
        partida.mueve(0, 1);
        persistenciaCTRL.guardaPartida(partida);
        Partida partidaRestaurada = persistenciaCTRL.obtenPartida();
        partidaRestaurada.moonwalk();
        assertTrue(partidaRestaurada.mueve(0, 1));
        assertEquals(4, partidaRestaurada.getTablero()[0][1].getValor());
    }

    private boolean mismoTextoEnFichero(String[] text,String fichero){
        try {
            File file = new File("persistencia/"+fichero);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i=0;
            while ((line = bufferedReader.readLine()) != null) {
                if(i>=text.length || !line.equals(text[i])) return false;
                i++;
            }
            fileReader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void añadirCeldasHidato(ArrayList<String> celdas) {
        int files = test.getNumFilas();
        int columnes = test.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String fila = celdas.get(i);
            String[] celda = fila.split(",");
            for (int j = 0; j < columnes; j++) {

                TipoCelda tipus = stringToCelda(celda[j]);
                if(!tipus.equals(TipoCelda.FIJA)){
                    test.nuevaCelda(tipus,i,j);
                }
                else{
                    test.nuevaCelda(tipus,i,j,Integer.valueOf(celda[j]));
                }
            }
        }
    }
    private TipoCelda stringToCelda(String s){
        if(s.equals("*")) return TipoCelda.AGUJERO;
        else if(s.equals("#")) return TipoCelda.INVISIBLE;
        else if(s.equals("?")) return TipoCelda.VARIABLE;
        else return TipoCelda.FIJA;
    }
}