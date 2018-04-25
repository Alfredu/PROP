package com.hidatosdecarbono;

public class Celda {
    private int valor = -1;
    private TipoCelda tipo;

    Celda(TipoCelda type) throws IllegalArgumentException{
        if (type.equals(TipoCelda.FIJA)) throw new IllegalArgumentException("Una celda fija necesita valor");
        else this.tipo = type;
    }

    Celda(TipoCelda type, int valor) throws IllegalArgumentException{
        if(type.equals(TipoCelda.FIJA)) {
            this.valor = valor;
            this.tipo = type;
        }
        else throw new IllegalArgumentException("La celda no necesita valor");
    }

    /**
     *
     * @return Un integer que contiene el valor de la celda. Devuelve -1 si la celda es vacia o un agujero, o
     * el valor si lo tiene.
     *
     */
    public int getValor(){
        return valor;
    }

    /**
     *
     * @return Un enum TipoCelda que contiene el tipo de la celda.
     */
    public TipoCelda getTipo() {
        return tipo;
    }

    /**
     *
     * @param v Un integer con el valor a añadir a la celda.
     * @throws IllegalArgumentException Si el tipo de la celda no era variable
     */
    public void setValor(int v) throws IllegalArgumentException{
        if(tipo.equals(TipoCelda.VARIABLE)){
            valor = v;
        }
        else throw new IllegalArgumentException("Celda no variable");
    }


    public boolean tieneValor(){
        return (valor != -1);
    }

}
