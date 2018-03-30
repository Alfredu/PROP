package com.hidatosdecarbono;

public class Celda {
    private int valor;
    private TipoCelda tipo;

    public Celda(TipoCelda type) throws IllegalArgumentException{
        if (type.equals(TipoCelda.FIJA)) throw new IllegalArgumentException("Una celda fija necesita valor");
        else this.tipo = type;
    }

    public Celda(TipoCelda type, int valor) throws IllegalArgumentException{
        if(type.equals(TipoCelda.FIJA)) {
            this.valor = valor;
            this.tipo = type;
        }
        else throw new IllegalArgumentException("La celda no necesita valor");
    }

    public int getValor(){
        return valor;
    }

    public TipoCelda getTipo() {
        return tipo;
    }

    public void setValor(int v) throws Exception{
        if(tipo.equals(TipoCelda.VARIABLE)){
            valor = v;
        }
        else throw new Exception("Celda no variable");
    }

}
