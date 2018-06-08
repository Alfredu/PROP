package com.hidatosdecarbono;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class JugarHidatosCTRL {
    private PersistenciaCTRL persistencia;
    private Partida partida;
    private Hidato hidatoJugado;
    private HidatoRep rep;

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
            p = persistencia.obtenPartida(partida.getJugadorPartida().getUsername());
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

    /**
     * Pide una pista del tipo especificado en el parametro del metodo y se le devuelve si se ha podido dar la pista
     * @param tipoPista El tipo de pista que decide el usuario
     * @return True si la pista se ha dado correctamente y false en caso de que no se pueda dar
     */

    public boolean pidePista(TipoPista tipoPista){
        return partida.pidePista(tipoPista);
    }

    public int getTiempoPartida(){return partida.getTiempoPartida();}

    /**
     * Devuelve una representación en formato String del estado del tablero de la partida actual
     * @return Una matriz de strings (String[][]) con el contenido del tablero en formato string
     */
    public String[][] getTablero(){
        Celda[][] tablero = partida.getTablero();
        int files = tablero.length;
        int columnes = tablero[0].length;
        String celes[][] = new String[tablero.length][tablero[0].length];
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                Celda c = tablero[i][j];
                celes[i][j] = celdaToString(c);

            }
        }
        return celes;
    }

    /**
     * Devuelve un ArrayList de Strings con las entradas del ranking de la dificultad del hidato que se ha terminado de jugar
     * @return Un ArrayList donde cada String representa una entrada del ranking de la dificultat del hidato jugado
     */
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
        else if(c.getTipo().equals(TipoCelda.INVISIBLE)) return "#";
        else if(c.getTipo().equals(TipoCelda.VARIABLE)){
            if(c.tieneValor()) return String.valueOf(c.getValor());
            else return "?";
        }
        else return String.valueOf(c.getValor());
    }

    public HidatoRep getRepresentacionHidato(){
        if(this.partida.getRep() == null){
            this.rep = new HidatoRep();
            this.rep.setParams(0, hidatoJugado.getNumColumnas(), hidatoJugado.getNumFilas(),
                    hidatoJugado.getTipoHidato(), hidatoJugado.getAdyacencia());

            for(int i=0;i<partida.getHidatoJugado().getNumFilas();i++){
                for(int j=0;j<partida.getHidatoJugado().getNumColumnas();j++){
                    this.rep.tablero[i][j] = celdaToString(partida.getTablero()[i][j]);
                }
            }

            return this.rep;

        }
        else{
         return partida.getRep();
        }

    }

    public void guardaRepresentacionHidato(HidatoRep rep) {
        this.partida.setRep(rep);
    }

    public Dificultad getDificultadHidatoJugado(){
        return this.hidatoJugado.getDificultad();
    }
}
