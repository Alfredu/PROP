package com.hidatosdecarbono;

import java.util.Scanner;

public class UserInterface {

    void run() {
        System.out.println("Demo hidato");

        System.out.println("Opciones:");
        System.out.println("1- Añadir un hidato");

        Scanner reader = new Scanner(System.in);
        int opt;
        opt = reader.nextInt();

        if (opt == 1) {
            afegirHidato();
        } else {
            System.out.println("Error");
            return;
        }
    }

    private void afegirHidato() {
        Scanner reader = new Scanner(System.in);

        System.out.println("Entre el numero de filas:");
        int fila = reader.nextInt();

        System.out.println("Entre el numero de columnas:");
        int col = reader.nextInt();

        System.out.println("Entre el tipo de hidato, donde Q = quadrado, T = triangular, H = hexagonal");
        String forma = reader.next();

        System.out.println("Entre el tipo de adyacencia, donde L = lados y LV = lado y vertice");
        String adjacencia = reader.next();

        TipoAdyacencia adj = TipoAdyacencia.LADO;
        if (adjacencia.equals("L")) adj = TipoAdyacencia.LADO;
        else if (adjacencia.equals("LV")) adj = TipoAdyacencia.LADOYVERTICE;
        else {
            System.out.println("Error en la introducción de la adyacencia, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) afegirHidato();
            else return;
        }

        if (!forma.equals("Q") && !forma.equals("T") && !forma.equals("H")) {
            System.out.println("Error en la introducción de la forma, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) afegirHidato();
            else return;
        }

        System.out.println("Hidato creado, introduzca el valor de cada celda (se llenan las columnas de cada fila y se procede con la fila siguente");
        System.out.println("Una celda viene representada por el tipo de celda (- invisible, # agujero, ? variable) y por su valor si es fija");

        if (forma.equals("Q")) {
            HidatoCuadrado test = new HidatoCuadrado(fila, col, adj);
            afegirCeles(test);
            printHidato(test);
        }
        else if (forma.equals("T")) {
            HidatoTriangular test = new HidatoTriangular(fila, col, adj);
            afegirCeles(test);
            printHidato(test);
        }
        else if (forma.equals("H")) {
            try {
                HidatoHexagonal test = new HidatoHexagonal(fila, col, adj);
                afegirCeles(test);
                printHidato(test);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    private void afegirCeles(Hidato h) {
        Scanner reader = new Scanner(System.in);
        int files = h.getNumFilas();
        int columnes = h.getNumColumnas();

        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                String cela = reader.next();
                TipoCelda tipus = stringToCelda(cela);
                if(!tipus.equals(TipoCelda.FIJA)){
                    h.nuevaCelda(tipus,i,j);
                }
                else{
                    h.nuevaCelda(tipus,i,j,Integer.parseInt(cela));
                }
            }
        }
    }

    private void printHidato(Hidato h){
        int files = h.getNumFilas();
        int columnes = h.getNumColumnas();
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = h.getCelda(i, j);
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(" ");
            }
            System.out.println(celes);
        }
    }

    private TipoCelda stringToCelda(String s){
        if(s.equals("#")) return TipoCelda.AGUJERO;
        else if(s.equals("-")) return TipoCelda.INVISIBLE;
        else if(s.equals("?")) return TipoCelda.VARIABLE;
        else return TipoCelda.FIJA;
    }

    private String celdaToString(Celda c){
        if(c.getTipo().equals(TipoCelda.AGUJERO)) return "#";
        else if(c.getTipo().equals(TipoCelda.INVISIBLE)) return "-";
        else if(c.getTipo().equals(TipoCelda.VARIABLE)){
            if(c.tieneValor()) return String.valueOf(c.getValor());
            else return "?";
        }
        else return String.valueOf(c.getValor());
    }


}
