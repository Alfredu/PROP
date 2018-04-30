package com.hidatosdecarbono;

import java.util.Stack;

public class Partida {
    private Hidato hidatoJugado;
    private Graph grafoHidato;
    private Celda[][] tablero;
    private Celda[][] tableroSolucion;
    private Node[][] nodes;
    private Stack<Movimiento> movimientos;
    private int n = 2;
    private int filaActual;
    private int colActual;

    public Partida(Hidato hidato){
        this.hidatoJugado = hidato;

        tablero = new Celda[hidatoJugado.getNumFilas()][hidatoJugado.getNumColumnas()];
        tableroSolucion = new Celda[hidatoJugado.getNumFilas()][hidatoJugado.getNumColumnas()];
        nodes = new Node[hidatoJugado.getNumFilas()][hidatoJugado.getNumColumnas()];
        grafoHidato = new Graph();

        hidatoJugado.copiaTablero(tablero);
        hidatoJugado.copiaTablero(tableroSolucion);

        hidatoJugado.creaGrafo(grafoHidato,nodes,tableroSolucion);
        movimientos = new Stack<Movimiento>();
        inicializarFilaColumna();
        clearTableroSolucion();

        checkSiguiente();

    }

    public Hidato getHidatoJugado(){
        return hidatoJugado;
    }

    public Celda[][] getTablero(){
        return tablero;
    }

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
    public boolean mueve(int i, int j){
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

    public boolean acabado(){
        return (n == grafoHidato.size()+1);
    }



}
