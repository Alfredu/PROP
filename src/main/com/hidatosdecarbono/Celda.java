package com.hidatosdecarbono;

public class Celda {
    private int valor;
    private TipoCelda tipo;

    Celda(){}

    Celda(TipoCelda type) throws IllegalArgumentException{
        if (type.equals(TipoCelda.FIJA)) throw new IllegalArgumentException("Una celda fija necesita valor");
        else this.tipo = type;
    }

    Celda(TipoCelda type, int valor) throws IllegalArgumentException{
        if(type.equals(TipoCelda.FIJA) || type.equals(TipoCelda.VARIABLE)) {
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
        valor = v;
    }

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    public boolean tieneValor(){
        return (valor != 0);
    }

    public boolean esValida(){
        return (tipo.equals(TipoCelda.FIJA) || tipo.equals(TipoCelda.VARIABLE));
    }

    public Celda copiaCelda(){
        Celda c = new Celda();
        c.setTipo(this.tipo);
        c.setValor(this.valor);
        return c;
    }

}
