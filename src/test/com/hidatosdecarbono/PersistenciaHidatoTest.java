package com.hidatosdecarbono;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class PersistenciaHidatoTest {
    private PersistenciaCTRL persistenciaCTRL;
    private Hidato test;

    @Before
    public void setUp() {
        this.persistenciaCTRL = new PersistenciaCTRL();
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("persistencia/hidatosDificil.txt");
        file.delete();
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
               "\"dificultad\":\"DIFICIL\"}"};
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