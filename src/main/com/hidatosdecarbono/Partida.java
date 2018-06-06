package com.hidatosdecarbono;

import org.omg.CORBA.TIMEOUT;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Partida {
    private Hidato hidatoJugado;
    private transient Graph grafoHidato;
    private Celda[][] tablero;
    private transient Celda[][] tableroSolucion;
    private transient Node[][] nodes;
    private Stack<Movimiento> movimientos;
    private int n = 2;
    private int filaActual;
    private int colActual;
    private ArrayList<Pista> pistas;
    private int numPistas = 0;
    private long tiempoInicial;
    private long tiempoPartida = 0;
    private Jugador jugadorPartida;

    public Partida(Hidato hidato, Jugador jugadorAJugar){
        this.tiempoInicial = System.currentTimeMillis();
        this.hidatoJugado = hidato;
        this.jugadorPartida = jugadorAJugar;

        tablero = new Celda[hidatoJugado.getNumFilas()][hidatoJugado.getNumColumnas()];
        tableroSolucion = new Celda[hidatoJugado.getNumFilas()][hidatoJugado.getNumColumnas()];
        nodes = new Node[hidatoJugado.getNumFilas()][hidatoJugado.getNumColumnas()];
        grafoHidato = new Graph();

        hidatoJugado.copiaTablero(tablero);
        hidatoJugado.copiaTablero(tableroSolucion);

        hidatoJugado.creaGrafo(grafoHidato,nodes,tableroSolucion);
        movimientos = new Stack<Movimiento>();
        pistas = new ArrayList<>();
        inicializarFilaColumna();
        clearTableroSolucion();

        checkSiguiente();

    }

    /**
     * Pide una pista para determinar el próximo movimiento y lo efectua. De no haberlo, devuelve false.
     * @return true si se ha encontrado siguiente movimiento. false si no.
     * @param tipoPista
     */
    public boolean pidePista(TipoPista tipoPista){
        numPistas++;
        clearTableroSolucion();
        boolean solucionable = grafoHidato.esSolucionable();
        if(tipoPista.equals(TipoPista.SIGUIENTE_CASILLA)) {
            pistas.add(new PistaSiguienteCasilla());
            if (solucionable) {
                //return la casella on estigui n
                int i = 0;
                for (Celda[] fila : tableroSolucion) {
                    int j = 0;
                    for (Celda celda : fila) {
                        if (celda.getValor() == n) {
                            mueve(i, j);
                            return true;
                        }
                        j++;
                    }
                    i++;
                }
            }
        }
        else if(tipoPista.equals(TipoPista.CAMINO_CORRECTO)){
            pistas.add(new PistaCaminoCorrecto());
            if(solucionable) return true;
        }
        else if(tipoPista.equals(TipoPista.FIJA_ALEATORIA)){
            pistas.add(new PistaFijaAleatoria());
            if(solucionable){
                ArrayList<Celda> vacias = new ArrayList<>();
                ArrayList<Celda> soluciones = new ArrayList<>();
                for(int i = 0; i < tablero.length; i++){
                    for(int j = 0; j < tablero[i].length; j++){
                        if(tablero[i][j].esVacia()){
                            vacias.add(tablero[i][j]);
                            soluciones.add(tableroSolucion[i][j]);
                        }
                    }
                }
                int random = ThreadLocalRandom.current().nextInt(0, vacias.size());
                int valor = soluciones.get(random).getValor();
                vacias.get(random).setValor(valor);
                vacias.get(random).setTipo(TipoCelda.FIJA);
                checkSiguiente();
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el Hidato que se está jugando
     * @return El Hidato en juego.
     */
    public Hidato getHidatoJugado(){
        return hidatoJugado;
    }

    /**
     * Devuelve el tablero en juego.
     * @return Una matriz de Celdas que contiene el tablero en juego.
     */
    public Celda[][] getTablero(){
        return tablero;
    }

    /**
     * Devuelve el tablero que contiene la solución
     * @return Una matriz de Celdas que contiene el tablero solucionado
     */
    public Celda[][] getTableroSolucion() {
        return tableroSolucion;
    }

    private void clearTableroSolucion(){
        for(int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero[i].length; j++){
                tableroSolucion[i][j].setValor(tablero[i][j].getValor());
            }
        }

    }

    /* comprueva si se puede realizar el movimiento en la posicion indicada y en caso de ser posible pon el valor
     de la celda a n, actualiza filaActual y colActual con la posicion de la celda, empila el movimiento, augmenta n en 1
     y llama a checkSiguiente() para comprovar si hay una casilla adyacente a esta con valor n
     */

    /**
     * Realiza un movimiento en la posicion indicada. Indica si ha sido posible.
     * @param i Un Integer con la fila de la Celda donde se quiere hacer el movimiento.
     * @param j Un Integer con la columna de la Celda donde se quiere hacer el movimiento.
     * @return true si se ha hecho el movimiento con éxito. false si no.
     */
    public boolean mueve(int i, int j){
        if(i < 0 || i >= tablero.length || j < 0 || j >= tablero[i].length) return false;
        if(tablero[i][j].esVacia() && grafoHidato.sonAdyacentes(nodes[i][j],nodes[filaActual][colActual])) {
            Movimiento m = new Movimiento(filaActual, colActual);
            movimientos.push(m);
            tablero[i][j].setValor(n);
            filaActual = i;
            colActual = j;
            n++;
            
            checkSiguiente();
            return true;
        }
        return false;
    }

    /**
     * Deshace el último movimiento.
     * @return true si se ha podido deshacer. false si no.
     */
    public boolean moonwalk(){
        if(movimientos.empty()){
            return false;
        }
        else{
            Movimiento m = movimientos.pop();
            if(tablero[filaActual][colActual].getTipo().equals(TipoCelda.VARIABLE)){
                tablero[filaActual][colActual].setValor(0);
                filaActual = m.getI();
                colActual = m.getJ();
                n--;
            }
            else if (tablero[filaActual][colActual].getTipo().equals(TipoCelda.FIJA)){
                filaActual = m.getI();
                colActual = m.getJ();
                n--;
                moonwalk();
            }
            return true;
        }
    }

    //busca en el tablero la celda con valor 1 para iniciar la fila y columna actuales en esa posicion
    private void inicializarFilaColumna(){
        for(int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j].getValor() == 1) {
                    filaActual = i;
                    colActual = j;
                }
            }
        }
    }

    /*comprueva si la celda con posicion filaActual y colActual es adyacente a una celda fija con valor n+1
      y si es asi augmenta n, actualiza filaActual y colActual con la posicion de esta celda adyacente y vuelve a
      llamarse para comprovar para la nueva filaActual y colActual
     */
    private void checkSiguiente(){
        Node actual = nodes[filaActual][colActual];
        if(grafoHidato.checkValorAdyacente(actual,n)){
            movimientos.push(new Movimiento(filaActual,colActual));
            for (int i = 0; i < tablero.length; i++){
                for (int j = 0; j < tablero[i].length; j++){
                    if (tablero[i][j].getValor() == n){
                        filaActual = i;
                        colActual = j;
                    }
                }
            }
            n++;
            checkSiguiente();
        }
    }

    /**
     * Indica si la partida ha sido completada.
     * @return true si la partida ha acabado. false si no.
     */
    public boolean acabado(){
        if(n == grafoHidato.size()+1){
            tiempoPartida = tiempoPartida + (System.currentTimeMillis() - tiempoInicial);
            int time = Math.toIntExact(tiempoPartida);
            hidatoJugado.entraRanking(jugadorPartida.getUsername(),time,pistas);
            return true;
        }
        return false;
    }

    public void pausar(){
        tiempoPartida = tiempoPartida + (System.currentTimeMillis() - tiempoInicial);
    }


    public void setTablero(Celda[][] tablero) {
        this.tablero = tablero;
    }

    public void setMovimientos(Stack<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setFilaActual(int filaActual) {
        this.filaActual = filaActual;
    }

    public void setColActual(int colActual) {
        this.colActual = colActual;
    }

    public void setNumPistas(int numPistas) {
        this.numPistas = numPistas;
    }

    public void setTiempoInicial(long tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    public void setTiempoPartida(long tiempoPartida) {
        this.tiempoPartida = tiempoPartida;
    }

    public long getTiempoPartida(){return System.currentTimeMillis() - this.tiempoInicial;}

}
