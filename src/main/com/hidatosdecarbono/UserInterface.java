package com.hidatosdecarbono;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private DomainFactory domini = new DomainFactory();

    void run() {
        System.out.println("Demo hidato");

        System.out.println("Opciones:");
        System.out.println("1- Añadir un hidato por casillas");
        System.out.println("2- Añadir un hidato aleatorio");

        Scanner reader = new Scanner(System.in);
        int opt;
        opt = reader.nextInt();

        if (opt == 1) {
            añadirHidatoPorParametros();
        }
        else if(opt == 2){
            añadirHidatoAleatorio();
        }
        else {
            System.out.println("Error");
            return;
        }
    }

    private void añadirHidatoPorParametros() {
        Scanner reader = new Scanner(System.in);

        System.out.println("Entre el numero de filas:");
        int numFilas = reader.nextInt();

        System.out.println("Entre el numero de columnas:");
        int numColumnas = reader.nextInt();

        System.out.println("Entre el tipo de hidato, donde C = Cuadrado, T = triangular, H = hexagonal");
        String forma = reader.next();

        System.out.println("Entre el tipo de adyacencia, donde L = lados y LV = lado y vertice");
        String adjacencia = reader.next();

        TipoAdyacencia adj = TipoAdyacencia.LADO;
        if (adjacencia.equals("L")||adjacencia.equals("l")) adj = TipoAdyacencia.LADO;
        else if (adjacencia.equals("LV")||adjacencia.equals("lv")) adj = TipoAdyacencia.LADOYVERTICE;
        else {
            System.out.println("Error en la introducción de la adyacencia, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) añadirHidatoPorParametros();
            else return;
        }

        if (!forma.equals("C") && !forma.equals("T") && !forma.equals("H")) {
            System.out.println("Error en la introducción de la forma, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) añadirHidatoPorParametros();
            else return;
        }
        CreadorHidatosCTRL creadorHidatosCTRL = domini.getControladorCreador();

        System.out.println("Hidato creado, introduzca el valor de cada celda (se llenan las columnas de cada numFilas y se procede con la numFilas siguente");
        System.out.println("Una celda viene representada por el tipo de celda (# invisible, * agujero, ? variable) y por su valor si es fija");

        ArrayList <String> celdas = leerCeldas(numFilas);
        boolean tieneSolucion = false;
        TipoHidato tipo = TipoHidato.CUADRADO;
        if (forma.equals("C")) {
            tipo = TipoHidato.CUADRADO;
        }
        else if (forma.equals("T")) {
            tipo = TipoHidato.TRIANGULAR;
        }
        else if (forma.equals("H")) {
            tipo = TipoHidato.HEXGONAL;
        }
        try {
            tieneSolucion = creadorHidatosCTRL.creaHidatoPropuesto(tipo, numFilas, numColumnas, adj, celdas);
            creadorHidatosCTRL.printHidato();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if(tieneSolucion) muestraYJuega(creadorHidatosCTRL);

    }

    private void añadirHidatoAleatorio(){
        Scanner reader = new Scanner(System.in);

        System.out.println("Entre el tipo de hidato, donde C = Cuadrado, T = triangular, H = hexagonal");
        String forma = reader.next();

        if (!forma.equals("C") && !forma.equals("T") && !forma.equals("H")) {
            System.out.println("Error en la introducción de la forma, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) añadirHidatoAleatorio();
            else return;
        }

        TipoHidato tipo = TipoHidato.CUADRADO;
        if (forma.equals("C")) {
            tipo = TipoHidato.CUADRADO;
        }
        else if (forma.equals("T")) {
            tipo = TipoHidato.TRIANGULAR;
        }
        else if (forma.equals("H")) {
            tipo = TipoHidato.HEXGONAL;
        }

        System.out.println("Entre el tipo de adyacencia, donde L = lados y LV = lado y vertice");
        String adjacencia = reader.next();

        TipoAdyacencia adj = TipoAdyacencia.LADO;
        if (adjacencia.equals("L")||adjacencia.equals("l")) adj = TipoAdyacencia.LADO;
        else if (adjacencia.equals("LV")||adjacencia.equals("lv")) adj = TipoAdyacencia.LADOYVERTICE;
        else {
            System.out.println("Error en la introducción de la adyacencia, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) añadirHidatoAleatorio();
            else return;
        }

        System.out.println("Entre el numero total de casillas  del hidato:");
        int topo = reader.nextInt();

        System.out.println("Entre el numero de casillas con agujero del hidato:");
        int agujeros = reader.nextInt();

        System.out.println("Entre el numero de casillas fijas con valor del hidato:");
        int fijas = reader.nextInt();

        if(agujeros + fijas >= topo){
            System.out.println("Hidato imposible de representar, fijas + agujeros >= numero total de casillas, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) añadirHidatoAleatorio();
            else return;
        }

        CreadorHidatosCTRL creadorHidatosCTRL = domini.getControladorCreador();

        try {
            creadorHidatosCTRL.creaHidatoAleatorio(tipo,topo,fijas,agujeros,adj);
            creadorHidatosCTRL.printHidato();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        muestraYJuega(creadorHidatosCTRL);

    }

    private void muestraYJuega(CreadorHidatosCTRL creadorHidatosCTRL) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Hidato amb solucio, visualitzar hidato? (Y/N)");
        String opt = reader.next();
        if(opt.equals("Y") || opt.equals("y")){
            creadorHidatosCTRL.printSolucion();
        }
        System.out.println("Quiere jugar o klk? (Y/N)");
        opt = reader.next();
        if(opt.equals("Y") || opt.equals("y")){
            juegaPartida();
        }
    }

    private void juegaPartida() {
        Scanner reader = new Scanner(System.in);
        JugarHidatosCTRL jugarHidatosCTRL = domini.getControladorJugar("Creado");
        Boolean end = false;
        System.out.println("Introduzca el valor de la fila y la columna donde moverse separados por un espacio, o pulse R para retroceder una casilla");
        while (!end){
            jugarHidatosCTRL.printTablero();
            String opcio = reader.next();
            if(opcio.equals("R")||opcio.equals("r")){
                jugarHidatosCTRL.retroceder();
            }
            else {
                String fila = opcio;
                String col = reader.next();
                int i = Integer.valueOf(fila);
                int j = Integer.valueOf(col);
                if (!jugarHidatosCTRL.mueve(i, j)) {
                    System.out.println("Movimiento incorrecto");
                }
                end = jugarHidatosCTRL.acabada();
            }
        }
        System.out.println("Partida acabada!");
        jugarHidatosCTRL.printTablero();
        System.out.println("Desea consultar el ranking? (Y/N)");
        String opt = reader.next();
        if(opt.equals("Y") || opt.equals("y")){
            jugarHidatosCTRL.ranking();
        }
        System.out.println("Quiere jugar de nuevo? (Y/N)");
        opt = reader.next();
        if(opt.equals("Y") || opt.equals("y")){
            juegaPartida();
        }
    }


    private ArrayList<String> leerCeldas(int files) {
        Scanner reader = new Scanner(System.in);
        ArrayList <String> celdas = new ArrayList<>();
        for (int i = 0; i < files; i++) {
               celdas.add(reader.next());
        }
        return celdas;
    }

    private void printHidato(Hidato h){
        int files = h.getNumFilas();
        int columnes = h.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = h.getCeldaTablero(i, j);
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(" ");
            }
            System.out.println(celes);
        }
    }

    private TipoCelda stringToCelda(String s){
        if(s.equals("*")) return TipoCelda.AGUJERO;
        else if(s.equals("#")) return TipoCelda.INVISIBLE;
        else if(s.equals("?")) return TipoCelda.VARIABLE;
        else return TipoCelda.FIJA;
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
