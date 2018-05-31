package com.hidatosdecarbono;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class PersistenciaJugadorCTRLTest {
    private PersistenciaCTRL persistenciaCTRL;
    @Before
    public void setUp() {
     this.persistenciaCTRL = new PersistenciaCTRL();
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("persistencia/jugadores.txt");
        file.delete();
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
}