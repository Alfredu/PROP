package com.hidatosdecarbono;

import java.util.Scanner;

public class UserInterface {

    void run(){
        System.out.println("Demo hidato");

        System.out.println("Opciones:");
        System.out.println("1- Añadir un hidato");

        Scanner reader = new Scanner(System.in);
        int opt;
        opt = reader.nextInt();

        if(opt == 1){
            afegirHidato();
        }
        else {
            System.out.println("Error");
            return;
        }
    }

    private void afegirHidato(){
        Scanner reader = new Scanner(System.in);

        System.out.println("Entre el numero de filas:");
        int fila = reader.nextInt();

        System.out.println("Entre el numero de columnas:");
        int col = reader.nextInt();

        System.out.println("Entre el tipo de hidato, donde Q = quadrado, T = triangular, H = hexagonal");
        String forma = reader.next();

        System.out.println("Entre el tipo de adyacencia, donde L = lados y LV = lado y vertice");
        String adjacencia = reader.next();

        TipoAdjacencia adj = TipoAdjacencia.LADO;
        if(adjacencia.equals("L")) adj = TipoAdjacencia.LADO;
        else if(adjacencia.equals("LV")) adj = TipoAdjacencia.LADOYVERTICE;
        else{
            System.out.println("Error en la introducción de la adyacencia, reintentar? (Y/N)");
            if(reader.next().equals("Y") || reader.next().equals("y")) afegirHidato();
            else return;
        }

        if(!forma.equals("Q") && !forma.equals("T") && !forma.equals("H")){
            System.out.println("Error en la introducción de la forma, reintentar? (Y/N)");
            if(reader.next().equals("Y") || reader.next().equals("y")) afegirHidato();
            else return;
        }

        System.out.println("Hidato creado, introduzca el valor de cada celda (se llenan las columnas de cada fila y se procede con la fila siguente");
        System.out.println("Una celda viene representada por el tipo de celda (- invisible, # agujero, ? variable) y por su valor si es fija");

        if(forma.equals("Q")){
            HidatoQuadrado test = new HidatoQuadrado(fila,col,adj);


        }





    }
}
