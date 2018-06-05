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

    /**
     * Inicializa la partida a jugar a partir de el hidato y el jugador que se pasan por parametros
     * @param hidato Objeto de tipo Hidato que se quiere jugar
     * @param jugadorAJugar Objecto de tipo Jugador que representa el jugador actual
     */
    public void inicializa(Hidato hidato, Jugador jugadorAJugar){
        partida = new Partida(hidato,jugadorAJugar);
        hidatoJugado = hidato;
    }

    /**
     * Inicializa la partida a jugar a partir de una partida pasada por parametro
     * @param partida Objeto de tipo partida que representa la partida a jugar
     */
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

    /**
     * Actualiza la partida realizando un movimiento a la posición del tablero indicada por los parametros i y j en caso que
     * este movimiento sea posible, en caso que no lo sea la partida se mantiene en el mismo estado
     * @param i
     * @param j
     * @return True si el movimiento se realiza con exito, false en caso de que no se pueda realizar
     */

    public boolean mueve(int i, int j){
        return partida.mueve(i,j);
    }

    /**
     * Comprueva si la partida en curso esta acabada
     * @return True en caso afirmativo, false si no esta acabada
     */

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


    /**
     * Prueva de retroceder un movimiento hecho sobre la partida actual, si se puede la partida se actualiza con el movimiento
     * retrocedido, sino devuelve false y la partida se queda en el mismo estado
     * @return True en caso de que se pueda retroceder, false sino
     */
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


    public ArrayList<String> ranking(){
        ArrayList<String> rankingRepr = new ArrayList<>();

        ArrayList<EntradaRanking> entradasRanking = hidatoJugado.getEntradasRanking();
        int n = 1;
        for(EntradaRanking entrada : entradasRanking) {
            String fila = "";
            fila = fila.concat(String.valueOf(n));
            fila = fila.concat("- ");
            fila = fila.concat((entrada.getUsername()));
            fila = fila.concat(": ");
            fila = fila.concat(String.valueOf(entrada.getPuntuacion()));
            rankingRepr.add(fila);
            n++;
        }

        return rankingRepr;
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
