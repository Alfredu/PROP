package com.hidatosdecarbono;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.assertTrue;

public class PersistenciaRankingTest {
    private PersistenciaCTRL persistenciaCTRL;
    @Before
    public void setUp() {
     this.persistenciaCTRL = new PersistenciaCTRL();
    }

    @After
    public void tearDown() throws Exception {
        File file1 = new File("persistencia/rankingFacil.txt");
        File file2 = new File("persistencia/rankingMedio.txt");
        File file3 = new File("persistencia/rankingDificil.txt");
        file1.delete();
        file2.delete();
        file3.delete();
    }

    @Test
    public void guardaRankingFacilOrdenadoCorrectamente() {
       Ranking ranking = new Ranking();
       ranking.addEntradaRanking("User1",350,2);
       ranking.addEntradaRanking("User2",355,2);
       ranking.addEntradaRanking("User3",100,4);
       persistenciaCTRL.guardaRanking(ranking,Dificultad.FACIL);
       String[] texto = {"{\"entradasRanking\":[{\"username\":\"User3\",\"puntuacion\":29411},{\"username\":\"User1\",\"puntuacion\":21276},{\"username\":\"User2\",\"puntuacion\":21052}]}"};
       assertTrue("Se ha guardado el ranking facil corectamente",mismoTextoEnFichero(texto,"rankingFacil.txt"));
    }

    @Test
    public void guardaRankingMedioOrdenadoCorrectamente() {
        Ranking ranking = new Ranking();
        ranking.addEntradaRanking("User11",350,2);
        ranking.addEntradaRanking("User22",355,2);
        ranking.addEntradaRanking("User33",100,4);
        persistenciaCTRL.guardaRanking(ranking,Dificultad.MEDIO);
        String[] texto = {"{\"entradasRanking\":[{\"username\":\"User33\",\"puntuacion\":29411},{\"username\":\"User11\",\"puntuacion\":21276},{\"username\":\"User22\",\"puntuacion\":21052}]}"};
        assertTrue("Se ha guardado el ranking medio corectamente",mismoTextoEnFichero(texto,"rankingMedio.txt"));
    }

    @Test
    public void guardaRankingDificillOrdenadoCorrectamente() {
        Ranking ranking = new Ranking();
        ranking.addEntradaRanking("User100",350,2);
        ranking.addEntradaRanking("User200",355,2);
        ranking.addEntradaRanking("User300",100,4);
        persistenciaCTRL.guardaRanking(ranking,Dificultad.DIFICIL);
        String[] texto = {"{\"entradasRanking\":[{\"username\":\"User300\",\"puntuacion\":29411},{\"username\":\"User100\",\"puntuacion\":21276},{\"username\":\"User200\",\"puntuacion\":21052}]}"};
        assertTrue("Se ha guardado el ranking facil corectamente",mismoTextoEnFichero(texto,"rankingDificil.txt"));
    }

    @Test
    public void obtieneRankingDificillDesdeFicheroCorrectamente(){
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