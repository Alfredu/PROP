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

    public boolean mueve(int i, int j){
        if(tablero[i][j].esVacia() && grafoHidato.sonAdyacentes(nodes[i][j],nodes[filaActual][colActual])) {
            Movimiento m = new Movimiento(i, j);
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

    private void inicializarFilaColumna(){
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[i].length; j++){
                if(tablero[i][j].getValor() == 1){
                    filaActual = i;
                    colActual = j;
                }
            }
        }
    }

    private void checkSiguiente(){
        Node actual = nodes[filaActual][colActual];
        if(grafoHidato.checkValorAdyacente(actual,n)){
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
