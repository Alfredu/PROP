package com.hidatosdecarbono;

public class Celda {
    private int valor;
    private TipoCelda tipo;

    public Celda(TipoCelda type, int valor){
        this.valor = valor;
        this.tipo = type;
    }

    public int getValor(){
        return this.valor;
    }

    public void setValor(int v) throws Exception{
        if(this.tipo.equals(TipoCelda.VARIABLE)){
            this.valor = v;
        }
        else throw new Exception("Celda no variable");
    }

}
