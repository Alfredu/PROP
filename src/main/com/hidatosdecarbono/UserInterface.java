package com.hidatosdecarbono;

import org.omg.CORBA.TIMEOUT;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private DomainFactory domini = new DomainFactory();

    void run() {
        System.out.println("Demo hidato:");
        logIn();
        boolean end = false;
        while(!end) {
            System.out.println("Opciones:");
            System.out.println("1- Añadir un hidato por casillas");
            System.out.println("2- Añadir un hidato aleatorio");
            System.out.println("3- Añadir hidato por dificultad");
            System.out.println("4- LogIn con nuevo jugador");
            System.out.println("5- Consultar puntuaciones");
            System.out.println("6- Reanudar hidato");
            System.out.println("7- Seleccionar hidato");
            System.out.println("8- Terminar juego");

            Scanner reader = new Scanner(System.in);
            int opt;
            opt = reader.nextInt();

            if (opt == 1) {
                añadirHidatoPorParametros();
            }
            else if (opt == 2) {
                añadirHidatoAleatorio();
            }
            else if (opt == 3) añadirPorDificultad();
            else if(opt == 4) logIn();
            else if(opt == 5) consultarRankings();
            else if(opt == 6){
                try{
                    juegaPartida(domini.getControladorJugarHidatoPausado());
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println("No hi ha partida guardada");
                }
            }
            else end = true;
        }
    }

    private void logIn(){
        System.out.println("Registro en el sistema, introduzca su Username");
        Scanner reader = new Scanner(System.in);
        String username = reader.next();
        System.out.println("Registro en el sistema, introduzca su contraseña");
        String pass = reader.next();
        LogInCTRL logIn = domini.getLogInCTRL();
        boolean log = false;
        try{
            log = logIn.logIn(username,pass);
        }
        catch (InvalidUserException e){
            System.out.print("No existe el usuario");
            logIn();
        }
        if(log) {
            System.out.print("Loggeado en el sistema como: ");
            System.out.println(logIn.getJugador().getUsername());
        }
        else{
            System.out.print("La pass no coincide");
            logIn();
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
            creadorHidatosCTRL.creaHidatoPropuesto(tipo, numFilas, numColumnas, adj);
            tieneSolucion = creadorHidatosCTRL.añadirCeldasHidato(celdas);
            ArrayList<String> print = creadorHidatosCTRL.printHidato();
            for(int i = 0; i < print.size(); i++){
                System.out.println(print.get(i));
            }
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
            ArrayList<String> print = creadorHidatosCTRL.printHidato();
            for(int i = 0; i < print.size(); i++){
                System.out.println(print.get(i));
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        muestraYJuega(creadorHidatosCTRL);

    }

    private void añadirPorDificultad(){
        Scanner reader = new Scanner(System.in);

        System.out.println("Entre la dificultad del hidato, F = Facil, M = Medio y D = Dificil");
        String dificultad = reader.next();

        if (!dificultad.equals("F") && !dificultad.equals("M") && !dificultad.equals("D") &&
                !dificultad.equals("f") && !dificultad.equals("m") && !dificultad.equals("d")) {
            System.out.println("Error en la introducción de la forma, reintentar? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) añadirPorDificultad();
            else return;
        }

        Dificultad d = Dificultad.FACIL;
        if (dificultad.equals("F") || dificultad.equals("f")) {
            d = Dificultad.FACIL;
        }
        else if (dificultad.equals("M") || dificultad.equals("m")) {
            d = Dificultad.MEDIO;
        }
        else if (dificultad.equals("D") || dificultad.equals("d")) {
            d = Dificultad.DIFICIL;
        }

        CreadorHidatosCTRL creadorHidatosCTRL = domini.getControladorCreador();

        try {
            creadorHidatosCTRL.creaHidatoPorDificultad(d);
            ArrayList<String> print = creadorHidatosCTRL.printHidato();
            for(int i = 0; i < print.size(); i++){
                System.out.println(print.get(i));
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(creadorHidatosCTRL.adyacenciaHidato());
        System.out.println(creadorHidatosCTRL.formaHidato());
        muestraYJuega(creadorHidatosCTRL);


    }

    private void muestraYJuega(CreadorHidatosCTRL creadorHidatosCTRL) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Hidato amb solucio, visualitzar hidato? (Y/N)");
        String opt = reader.next();
        if(opt.equals("Y") || opt.equals("y")){
            ArrayList<String> print = creadorHidatosCTRL.printSolucion();
            for(int i = 0; i < print.size(); i++){
                System.out.println(print.get(i));
            }
        }
        System.out.println("Desea jugar el Hidato? (Y/N)");
        opt = reader.next();
        if(opt.equals("Y") || opt.equals("y")){
            juegaPartida(creadorHidatosCTRL.getControladorPartida());
        }
    }

    private void juegaPartida(JugarHidatosCTRL jugarHidatosCTRL) {
        Scanner reader = new Scanner(System.in);
        boolean end = false;
        System.out.print("Introduzca el valor de la fila y la columna donde moverse separados por un espacio,");
        System.out.println(" pulse R para retroceder una casilla, pulse P para pedir una pista (se coloca la siguiente casilla de forma automatica o pulse S para parar la partida (STOP)");
        boolean finalitzada = false;
        while (!end){
            jugarHidatosCTRL.printTablero();
            String opcio = reader.next();
            if(opcio.equals("R")||opcio.equals("r")){
                jugarHidatosCTRL.retroceder();
            }
            else if(opcio.equals("P")||opcio.equals("p")){
                System.out.println("Seleccione el tipo de pista: S = Siguiente casilla, C = comprovar camino correcto y F = celda fija aleatoria");
                opcio = reader.next();
                boolean found = false;
                if(opcio.equals("S") || opcio.equals("s")){
                    found = jugarHidatosCTRL.pidePista(TipoPista.SIGUIENTE_CASILLA);
                }
                else if(opcio.equals("C") || opcio.equals("c")){
                    found = jugarHidatosCTRL.pidePista(TipoPista.CAMINO_CORRECTO);
                }
                else if(opcio.equals("F") || opcio.equals("f")){
                    found = jugarHidatosCTRL.pidePista(TipoPista.FIJA_ALEATORIA);
                }
                if(!found){
                    System.out.println("No hay movimiento posible en este camino, prueve a retroceder");
                }
            }
            else if(opcio.equals("s") || opcio.equals("S")){
                if(jugarHidatosCTRL.compruebaPausada()){
                    System.out.println("Ya hay una partida guardada, desea sobrescribirla? (Y/N)");
                    String guardar = reader.next();
                    if(guardar.equals("Y")||guardar.equals("y")){
                        jugarHidatosCTRL.pausa();
                        end = true;
                    }
                }
                else {
                    jugarHidatosCTRL.pausa();
                    end = true;
                }

            }
            else {
                String fila = opcio;
                String col = reader.next();
                int i = Integer.valueOf(fila);
                int j = Integer.valueOf(col);
                if (!jugarHidatosCTRL.mueve(i, j)) {
                    System.out.println("Movimiento incorrecto");
                }
                end = finalitzada = jugarHidatosCTRL.acabada();
            }
        }
        if(finalitzada) {
            System.out.println("Partida acabada!");
            jugarHidatosCTRL.printTablero();
            System.out.println("Desea consultar el ranking? (Y/N)");
            String opt = reader.next();
            if (opt.equals("Y") || opt.equals("y")) {
                jugarHidatosCTRL.ranking();
            }
        }

    }

    private void consultarRankings(){
        System.out.println("Seleccione la dificultad a consultar: F = Facil, M = Media, D = Dificil");
        Scanner reader = new Scanner(System.in);
        String opt = reader.next();
        Dificultad dificultad;
        if (opt.equals("F") || opt.equals("f")) dificultad = Dificultad.FACIL;
        else if(opt.equals("M")||opt.equals("m")) dificultad = Dificultad.MEDIO;
        else dificultad = Dificultad.DIFICIL;

        ConsultarRankingCTRL rankingCTRL = domini.getRankingCTRL(dificultad);
        ArrayList<String> entrades = rankingCTRL.getEntradasRanking();
        for(String entrada : entrades){
            System.out.println(entrada);
        }
        System.out.println();
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
