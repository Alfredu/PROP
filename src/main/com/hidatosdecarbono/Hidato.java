package com.hidatosdecarbono;

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
     * Asigna un id al Hidato
     * @param id Un integer que contiene el id
     *           del Hidato
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Devuelve el id del Hidato
     * @return Un integer que contiene el id del hidato
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el numero de filas del Hidato.
     * @return Un integer que contiene el numero de filas del hidato
     */
    public int getNumFilas(){
        return tablero.length;
    }

    /**
     * Devuelve el numero de columnas.
     * @return Un integer que contiene el numero de columnas del hidato
     */
    public int getNumColumnas(){
        return tablero[0].length;
    }

    /**
     * Asigna un tipo de adyacencia al Hidato.
     * @param adyacencia Una enumeracion con el tipo de adyacencia que se quiere usar en el hidato
     */
    void setAdyacencia(TipoAdyacencia adyacencia) {
        this.adyacencia = adyacencia;
    }

    /**
     * Devuelve el tipo de adyacencia del Hidato.
     * @return Una enumeracion con el tipo de adyacencia que usa el hidato
     */
    public TipoAdyacencia getAdyacencia() {
        return adyacencia;
    }


    /**
     * Inicializa el Hidato.
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

    /**
     * Crea una nueva Celda en el tablero de juego con valor
     * @param tipo TipoCelda de la Celda a crear
     * @param fila Integer con la fila de la Celda en el tablero
     * @param columna Integer con la columna de la Celda en el tablero
     * @param valor Valor a añadir en la Celda
     * @throws IllegalArgumentException si el tipo de la Celda no corresponde.
     */
    public void nuevaCelda(TipoCelda tipo, int fila, int columna, int valor) throws IllegalArgumentException{
        try{
            tablero[fila][columna] = new Celda(tipo, valor);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    /**
     * Crea una Celda en el tablero de juego sin valor
     * @param tipo TipoCelda de la Celda a crear
     * @param fila Integer con la fila de la Celda en el tablero
     * @param columna Integer con la columna de la Celda en el tablero
     * @throws IllegalArgumentException si el tipo de la Celda no corresponde.
     */
    public void nuevaCelda(TipoCelda tipo, int fila, int columna) throws IllegalArgumentException{
        try{
           tablero[fila][columna] = new Celda(tipo);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    /**
     * Devuelve una Celda del tablero
     * @param fila Integer con la fila de la Celda
     * @param col Integer con la columna de la Celda
     * @return La Celda correspondiente a la posicion fila,col del tablero
     */
    public Celda getCeldaTablero(int fila, int col){
        return tablero[fila][col];
    }

    /**
     * Devuelve una Celda del tablero de solucion
     * @param fila Integer con la fila de la Celda
     * @param col Integer con la columna de la Celda
     * @return La Celda correspondiente a la posicion fila,col del tablero de solución
     */
    public Celda getCeldaTableroSolucion(int fila, int col){
        return tableroSolucion[fila][col];
    }


    /**
     * Copia el tablero de juego al tablero por parámetros.
     * @param newTablero Matriz de Celdas donde se copiara el tablero de juego.

     */
    public void copiaTablero(Celda[][] newTablero){
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[i].length; j++){
                newTablero[i][j] = tablero[i][j].copiaCelda();
            }
        }
    }

    /**
     * Copia el tablero con la solucion al tablero por parámetros.
     * @param newTablero Matriz de Celdas donde se copiara el tablero
     */
    public void copiaTableroSolucion(Celda[][] newTablero){
        for(int i = 0; i < tableroSolucion.length; i++){
            for(int j = 0; j < tableroSolucion[i].length; j++){
                newTablero[i][j] = tableroSolucion[i][j].copiaCelda();
            }
        }
    }

    /**
     * Metodo abstracto. Devuelve todas las adyacencias de una casilla
     * @param i Integer con la fila de la Celda
     * @param j Integer con la columna de la Celda
     * @param nodo Matriz de Nodos creada a partir del tablero de Celdas (output).
     * @return Una ArrayList con todas los Nodos adyacentes a la casilla i,j
     */
    public abstract ArrayList<Node> getAdyacentes(int i, int j, Node[][] nodo);

    /**
     * Indica si el Hidato tiene solucion
     * @return true si el Hidato tiene solucion. false si no.
     */
    public boolean tieneSolucion(){
        copiaTablero(tableroSolucion);
        creaGrafo(grafo, nodo, tableroSolucion);
        return grafo.esSolucionable();
    }

    /**
     * Añade una entrada al Ranking de ese hidato
     * @param username String con el Nombre de usuario del jugador de la entrada
     * @param tiempo Integer Tiempo que ha tardado el jugador en acabar la partida
     * @param numPistas Integer con el Numero de Pistas que ha usado
     */
    public void entraRanking(String username, int tiempo, int numPistas){
        ranking.addEntradaRanking(username,tiempo,numPistas);
    }

    /**
     * Devuelve las entradas del Ranking del Hidato
     * @return Un ArrayList con todas las EntradaRanking perteneciente al Hidato
     */
    public ArrayList<EntradaRanking> getRanking(){
        return ranking.getEntradasRanking();
    }

    /**
     * Crea un grafo a partir del tablero
     * @param grafoSolucion Grafo de salida creado a partir del tablero (output).
     * @param nodo Matriz de Nodos a partir del cual se genera el Grafo (output)
     * @param solucion Matriz de Celdas a partir de la cual se genera la matriz de Nodos.
     */
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

    private void creaNodos(Node[][] tableroNodos, Celda[][] solucion) {
        int id = 2;
        int nFilas = this.getNumFilas();
        int nCols = this.getNumColumnas();

        for(int i = 0; i < nFilas; i++){
            for (int j = 0; j < nCols; j++){
                Celda actual = solucion[i][j];
                if(actual.esFijaOVariable()){
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

    /**
     * Genera un tablero aleatorio con los parámetros indicados
     * @param totalCasillas Integer con el numero de Celdas totales del hidato
     * @param agujeros Integer con el numero de Celdas del Hidato que seran agujeros
     * @param fijas Integer con el numero de Celdas del Hidato con el numero ya fijado.
     */
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
            while(iterador < fijas-1){
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
