package com.hidatosdecarbono;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CreadorHidatosCTRL {

    private Hidato hidatoCreado;
    private PersistenciaCTRL persistenciaCTRL;

    public CreadorHidatosCTRL(PersistenciaCTRL persistenciaCTRL){
        this.persistenciaCTRL = persistenciaCTRL;
    }

    /**
     * Devuelve el hidato creado.
     * @return Un Hidato
     */
    public Hidato getHidatoCreado(){
        return hidatoCreado;
    }

    /**
     *Constructora para un Hidato propuesto por el usuario.
     * @param tipoHidato Un enum TipoHidato que contiene el tipo del Hidato a crear
     * @param numFilas Un integer con el numero de filas del hidato a crear
     * @param numColumnas Un integer con el numero de columnas del hidato a crear
     * @param tipoAdyacencia Un enum TipoAdyacencia que contiene el tipo de la adyacencia del hidato a crear
     * @param celdas Un array de strings que contiene las celdas del hidato a crear
     * @return Un boolean que indica si el hidato propuesto tiene o no solución
     */
    public boolean creaHidatoPropuesto(TipoHidato tipoHidato, int numFilas, int numColumnas, TipoAdyacencia tipoAdyacencia, ArrayList <String> celdas) throws IllegalArgumentException{
        if(tipoHidato.equals(TipoHidato.CUADRADO)) {
            hidatoCreado = new HidatoCuadrado(numFilas, numColumnas, tipoAdyacencia);
            añadirCeldasHidato(celdas);
        }

        else if(tipoHidato.equals(TipoHidato.HEXGONAL)){
            hidatoCreado = new HidatoHexagonal(numFilas, numColumnas, tipoAdyacencia);
            añadirCeldasHidato(celdas);
        }
        else if(tipoHidato.equals(TipoHidato.TRIANGULAR)){
            hidatoCreado = new HidatoTriangular(numFilas, numColumnas, tipoAdyacencia);
            añadirCeldasHidato(celdas);
        }
        hidatoCreado.decideDificultad();


        return hidatoCreado.tieneSolucion();
    }

    public void creaHidatoPorDificultad(Dificultad dificultad){
        int forma = ThreadLocalRandom.current().nextInt(0, 3);
        int adj = ThreadLocalRandom.current().nextInt(0, 2);
        int celdas;
        if(dificultad.equals(Dificultad.FACIL)) {
            celdas = ThreadLocalRandom.current().nextInt(9, 40);
        }
        else{
            celdas = ThreadLocalRandom.current().nextInt(16, 50);
        }

        if(forma == 0 && adj == 0) hidatoCreado = new HidatoCuadrado(celdas, TipoAdyacencia.LADO);
        else if(forma == 0 && adj == 1) hidatoCreado = new HidatoCuadrado(celdas, TipoAdyacencia.LADOYVERTICE);
        else if(forma == 1 && adj == 0) hidatoCreado = new HidatoTriangular(celdas, TipoAdyacencia.LADO);
        else if(forma == 1 && adj == 1) hidatoCreado = new HidatoTriangular(celdas, TipoAdyacencia.LADOYVERTICE);
        else if(forma == 2) hidatoCreado = new HidatoHexagonal(celdas, TipoAdyacencia.LADO);

        hidatoCreado.generaAleatorioPorDificultad(celdas,dificultad);

    }

    public void creaHidatoAleatorio(TipoHidato tipoHidato,int numCeldas,int numCeldasFijas,int numCeldasAgujero, TipoAdyacencia tipoAdyacencia){
        if(tipoHidato == TipoHidato.CUADRADO) {
            hidatoCreado = new HidatoCuadrado(numCeldas, tipoAdyacencia);
        }

        else if(tipoHidato == TipoHidato.HEXGONAL){
            hidatoCreado = new HidatoHexagonal(numCeldas, tipoAdyacencia);
        }

        else if(tipoHidato == TipoHidato.TRIANGULAR){
            hidatoCreado = new HidatoTriangular(numCeldas, tipoAdyacencia);
        }
        hidatoCreado.generaTableroAleatorio(numCeldas,numCeldasAgujero,numCeldasFijas);
        hidatoCreado.decideDificultad();
    }

    public String adyacenciaHidato(){
        TipoAdyacencia adj = hidatoCreado.getAdyacencia();
        if(adj.equals(TipoAdyacencia.LADO)) return "Lado";
        else return "Lado y vertice";
    }

    public String formaHidato(){
        TipoHidato forma = hidatoCreado.getTipoHidato();
        if(forma.equals(TipoHidato.CUADRADO)) return "Cuadrado";
        else if(forma.equals(TipoHidato.TRIANGULAR)) return "Triangular";
        else return "Hexagonal";
    }

    /**
     * Crea las Celdas del Hidato interpretando los caracteres que codifican los distintos tipos de Celda.
     * No se comprueba que el <i>input</i> sea válido.
     *
     * @param celdas ArrayList de Strings, cada entrada contiene la codificación en carácteres de una fila de Celdas.
     */
    private void añadirCeldasHidato(ArrayList <String> celdas) {
        int files = hidatoCreado.getNumFilas();
        int columnes = hidatoCreado.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String fila = celdas.get(i);
            String[] celda = fila.split(",");
            for (int j = 0; j < columnes; j++) {

                TipoCelda tipus = stringToCelda(celda[j]);
                if(!tipus.equals(TipoCelda.FIJA)){
                    hidatoCreado.nuevaCelda(tipus,i,j);
                }
                else{
                    hidatoCreado.nuevaCelda(tipus,i,j,Integer.valueOf(celda[j]));
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

    /**
     * Devuelve un hidato en formato String
     */
    public ArrayList<String> printHidato(){
        int files = hidatoCreado.getNumFilas();
        int columnes = hidatoCreado.getNumColumnas();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = hidatoCreado.getCeldaTablero(i, j);
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(",");
            }
            result.add(celes);
        }
        return result;
    }

    /**
     * Imprime por pantalla la solución del hidato del controlador
     */
    public ArrayList<String> printSolucion(){

        int files = hidatoCreado.getNumFilas();
        int columnes = hidatoCreado.getNumColumnas();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = hidatoCreado.getCeldaTableroSolucion(i, j);
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(",");
            }
            result.add(celes);
        }
        return result;
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


}
