package com.hidatosdecarbono;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class JugarHidatosCTRL {
    private PersistenciaCTRL persistencia;
    private Partida partida;
    private Hidato hidatoJugado;

    public JugarHidatosCTRL(PersistenciaCTRL persistencia){
        this.persistencia = persistencia;
    }

    public void inicializa(Hidato hidato, Jugador jugadorAJugar){
        partida = new Partida(hidato,jugadorAJugar);
        hidatoJugado = hidato;
    }

    public void setPartida(Partida partida){
        this.partida = partida;
        hidatoJugado = partida.getHidatoJugado();
        Dificultad d = hidatoJugado.getDificultad();
        try {
            hidatoJugado.asociaRanking(persistencia.obtenRanking(d));
        }
        catch (NoSuchFileException e){
            Ranking r = new Ranking();
            persistencia.guardaRanking(r,d);
            hidatoJugado.asociaRanking(r);
        }
    }

    public boolean mueve(int i, int j){
        return partida.mueve(i,j);
    }

    public boolean acabada(){
        boolean result = partida.acabado();

        if(result){
            persistencia.guardaRanking(hidatoJugado.getRanking(),hidatoJugado.getDificultad());
        }
        return result;
    }

    /**
     * Comprueva si hay una partida ya pausada en el sistema
     * @return True si existe, False en caso contrario
     */

    public boolean compruebaPausada(){
        Partida p;
        try{
            p = persistencia.obtenPartida();
            persistencia.guardaPartida(p);
        }
        catch (IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    /**
     * Guarda la partida actual
     */

    public void pausa(){
        partida.pausar();
        persistencia.guardaPartida(partida);
    }



    public boolean retroceder(){
        return partida.moonwalk();
    }

    public boolean pidePista(TipoPista tipoPista){
        return partida.pidePista(tipoPista);
    }

    public long getTiempoPartida(){return partida.getTiempoPartida();}

    public void printTablero(){
        Celda[][] tablero = partida.getTablero();
        int files = tablero.length;
        int columnes = tablero[0].length;
        String celes;
        for (int i = 0; i < files; i++) {
            celes = String.valueOf(i);
            celes = celes.concat("|");
            for (int j = 0; j < columnes; j++) {
                Celda c = tablero[i][j];
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(" ");
            }
            System.out.println(celes);
        }
    }

    public void ranking(){
        ArrayList<EntradaRanking> entradasRanking = hidatoJugado.getEntradasRanking();
        int n = 1;
        for(EntradaRanking entrada : entradasRanking){
            System.out.print(n);
            System.out.print("- ");
            System.out.print(entrada.getUsername());
            System.out.print(" :  ");
            System.out.println(entrada.getPuntuacion());
            n++;
        }
    }


    private String celdaToString(Celda c){
        if(c.getTipo().equals(TipoCelda.AGUJERO)) return "*";
        else if(c.getTipo().equals(TipoCelda.INVISIBLE)) return "-";
        else if(c.getTipo().equals(TipoCelda.VARIABLE)){
            if(c.tieneValor()) return String.valueOf(c.getValor());
            else return "?";
        }
        else return String.valueOf(c.getValor());
    }
}
