package com.hidatosdecarbono;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Hidato {
    private int id;
    private TipoAdyacencia adyacencia;
    private Celda[][] tablero;
    private Celda[][] tableroSolucion;
    private Node[][] nodo;
    private Graph grafo;
    private Ranking ranking;

    /**
     *
     * @param id Un integer que contiene el id
     *           del Hidato
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @return Un integer que contiene el id del hidato
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return Un integer que contiene el numero de filas del hidato
     */
    public int getNumFilas(){
        return tablero.length;
    }

    /**
     *
     * @return Un integer que contiene el numero de columnas del hidato
     */
    public int getNumColumnas(){
        return tablero[0].length;
    }

    /**
     *
     * @param adyacencia Una enumeracion con el tipo de adyacencia que se quiere usar en el hidato
     */
    void setAdyacencia(TipoAdyacencia adyacencia) {
        this.adyacencia = adyacencia;
    }

    /**
     *
     * @return Una enumeracion con el tipo de adyacencia que usa el hidato
     */
    public TipoAdyacencia getAdyacencia() {
        return adyacencia;
    }


    /**
     *
     * @param numFilas Un integer con el numero de filas del tablero
     * @param numColumnas Un integer con el numero de columnas del tablero
     */
    void inicializaHidato(int numFilas, int numColumnas){
        tablero = new Celda[numFilas][numColumnas];
        tableroSolucion = new Celda[numFilas][numColumnas];
        nodo = new Node[numFilas][numColumnas];
        grafo = new Graph();
        ranking = new Ranking();
    }

    public void nuevaCelda(TipoCelda tipo, int fila, int columna, int valor) throws IllegalArgumentException{
        try{
            tablero[fila][columna] = new Celda(tipo, valor);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    public void nuevaCelda(TipoCelda tipo, int fila, int columna) throws IllegalArgumentException{
        try{
            tablero[fila][columna] = new Celda(tipo);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    public Celda getCeldaTablero(int fila, int col){
        return tablero[fila][col];
    }

    public Celda getCeldaTableroSolucion(int fila, int col){
        return tableroSolucion[fila][col];
    }


    public void copiaTablero(Celda[][] newTablero){
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[i].length; j++){
                newTablero[i][j] = tablero[i][j].copiaCelda();
            }
        }
    }

    public void copiaTableroSolucion(Celda[][] newTablero){
        for(int i = 0; i < tableroSolucion.length; i++){
            for(int j = 0; j < tableroSolucion[i].length; j++){
                newTablero[i][j] = tableroSolucion[i][j].copiaCelda();
            }
        }
    }

    public abstract ArrayList<Node> getAdyacentes(int i, int j, Node[][] nodo);

    public boolean tieneSolucion(){
        copiaTablero(tableroSolucion);
        creaGrafo(grafo, nodo, tableroSolucion);
        return grafo.esSolucionable();
    }

    public void entraRanking(String username, int tiempo, int numPistas){
        ranking.addEntradaRanking(username,tiempo,numPistas);
    }

    public ArrayList<EntradaRanking> getRanking(){
        return ranking.getEntradasRanking();
    }

    public void creaGrafo(Graph grafoSolucion, Node[][] nodo, Celda[][] solucion) {
        creaNodos(nodo, solucion);
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        for(int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nCols; j++) {
                if(nodo[i][j].noEsVacio()){
                    nodo[i][j].addListaAdyacencias(getAdyacentes(i,j, nodo));
                    grafoSolucion.addNode(nodo[i][j]);
                }
            }
        }
    }

    public void creaNodos(Node[][] tableroNodos, Celda[][] solucion) {
        int id = 2;
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        for(int i = 0; i < nFilas; i++){
            for (int j = 0; j < nCols; j++){
                Celda actual = solucion[i][j];
                if(actual.esValida()){
                    if (actual.getValor() == 1){
                        tableroNodos[i][j] = new Node(1,actual);
                    }
                    else{
                        tableroNodos[i][j] = new Node(id,actual);
                        id++;
                    }
                }
                else tableroNodos[i][j] = new Node();
            }
        }
    }

    public void generaTableroAleatorio(int totalCasillas, int agujeros, int fijas){

        //inicialitzem el tauler solucio amb caselles variables a 0
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[i].length; j++){
                tableroSolucion[i][j] = new Celda(TipoCelda.VARIABLE);
            }
        }

        //coloco les caselles invisibles pels extrems
        int nVacias = ((tablero.length*tablero[0].length) - totalCasillas);
        int filaInvi = 0;
        int colInvi = 1;
        int iterador = 0;
        while(iterador < nVacias){
            if(iterador%2 == 0) {
                tableroSolucion[filaInvi][0].setTipo(TipoCelda.INVISIBLE);
                filaInvi++;
            }
            else{
                tableroSolucion[0][colInvi].setTipo(TipoCelda.INVISIBLE);
                colInvi++;
            }
            iterador++;
        }


        //coloco els forats
        iterador = 0;
        while(iterador < agujeros){
            int i =  ThreadLocalRandom.current().nextInt(0, tablero.length);
            int j =  ThreadLocalRandom.current().nextInt(0, tablero[i].length);
            if (tableroSolucion[i][j].getTipo().equals(TipoCelda.VARIABLE)){
                tableroSolucion[i][j].setTipo(TipoCelda.AGUJERO);
                iterador++;

            }
        }

        //coloco el numero 1 en una posicio aleatoria
        boolean end = false;
        while (!end) {
            int fila1 = ThreadLocalRandom.current().nextInt(0, tablero.length);
            int col1 = ThreadLocalRandom.current().nextInt(0, tablero[0].length);
            if(tableroSolucion[fila1][col1].getTipo().equals(TipoCelda.VARIABLE)){
                end = true;
                tableroSolucion[fila1][col1].setValor(1);
                tableroSolucion[fila1][col1].setTipo(TipoCelda.FIJA);
            }
        }

        creaGrafo(grafo,nodo,tableroSolucion);

        //si es solucionable, les celes del tauler Solucio tenen una configuracio amb l'hidato resolt
        if(grafo.esSolucionable()){
            iterador = 0;
            //posem un numero aleatori de celes que eren variables a fixes (tenen valor segur ja que l'hidato ha quedat resolt)
            while(iterador < fijas){
                int i = ThreadLocalRandom.current().nextInt(0, tablero.length);
                int j = ThreadLocalRandom.current().nextInt(0, tablero[0].length);
                if (tableroSolucion[i][j].getTipo().equals(TipoCelda.VARIABLE)){
                    tableroSolucion[i][j].setTipo(TipoCelda.FIJA);
                    iterador++;
                }
            }
            //posem les celes variables que han quedat a 0
            for(int i= 0; i < tablero.length; i++){
                for(int j = 0; j < tablero[i].length; j++){
                    if(tableroSolucion[i][j].getTipo().equals(TipoCelda.VARIABLE)){
                        tableroSolucion[i][j].setValor(0);
                    }
                }
            }
            //copiem aquest taulerSolucio al tauler que representa el hidato
            copiaTableroSolucion(tablero);
        }
        else{
            generaTableroAleatorio(totalCasillas,agujeros,fijas);
        }

    }



}
