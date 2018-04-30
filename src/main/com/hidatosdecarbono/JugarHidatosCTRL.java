package com.hidatosdecarbono;

public class JugarHidatosCTRL {
    private Partida actual;

    public void inicializa(Hidato hidato, Jugador jugadorAJugar){
        actual = new Partida(hidato, jugadorAJugar);
    }

    public boolean mueve(int i, int j){
        return actual.mueve(i,j);
    }

    public boolean acabada(){
        return actual.acabado();
    }

    public boolean retroceder(){
        return actual.moonwalk();
    }


    public void printTablero(){
        Celda[][] tablero = actual.getTablero();
        int files = tablero.length;
        int columnes = tablero[0].length;
        for (int i = 0; i < files; i++) {
            String celes = "";
            for (int j = 0; j < columnes; j++) {
                Celda c = tablero[i][j];
                celes = celes.concat(celdaToString(c));
                celes = celes.concat(" ");
            }
            System.out.println(celes);
        }
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
