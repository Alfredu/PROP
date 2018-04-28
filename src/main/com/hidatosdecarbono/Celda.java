package com.hidatosdecarbono;

public class Celda {
    private int valor;
    private TipoCelda tipo;

    Celda(){}

    /**
     * Constructora para una Celda vacía. El tipo de la Celda debe ser distinto a FIJA
     * @param type Un enum TipoCelda que contiene el tipo de la Celda a crear.
     * @throws IllegalArgumentException si se intenta crear una Celda vacia con tipo FIJA.
     */
    Celda(TipoCelda type) throws IllegalArgumentException{
        if (type.equals(TipoCelda.FIJA)) throw new IllegalArgumentException("Una celda fija necesita valor");
        else this.tipo = type;
    }

    /**
     * Constructora para una Celda con valor. El tipo de la Celda debe ser FIJA o VARIABLE para poder asignarle valor.
     * @param type Un enum TipoCelda que contiene el tipo de la Celda a crear.
     * @param valor Un integer con el valor a asignar a la casilla.
     * @throws IllegalArgumentException si se intenta crear una Celda con valor con type distinto a FIJA o VARIABLE.
     */
    Celda(TipoCelda type, int valor) throws IllegalArgumentException{
        if(type.equals(TipoCelda.FIJA) || type.equals(TipoCelda.VARIABLE)) {
            this.valor = valor;
            this.tipo = type;
        }
        else throw new IllegalArgumentException("La celda no necesita valor");
    }

    /**
     * Devuelve el valor de la cielda.
     * @return Un integer que contiene el valor de la Celda. Devuelve 0 si la Celda es vacia o un agujero, o
     * el valor si lo tiene.
     *
     */
    public int getValor(){
        return valor;
    }

    /**
     * Devuelve el tipo de la Celda.
     * @return Un enum TipoCelda que contiene el tipo de la Celda.
     */
    public TipoCelda getTipo() {
        return tipo;
    }

    /**
     * Cambia el valor de la Celda. Se debe comprobar primero que la Celda es de tipo VARIABLE
     * @param v Un integer con el valor a añadir a la Celda.
     *
     */
    public void setValor(int v){
        valor = v;
    }

    /**
     * Devuelve true si la Celda es una celda variable y no tiene valor asignado.
     * @return Un booleano que indica si la Celda es vacia
     */
    public boolean esVacia(){
        return (this.tipo.equals(TipoCelda.VARIABLE) && this.valor == 0);
    }

    /**
     * Cambia el tipo de la Celda
     * @param tipo Un enum TipoCelda que contiene el nuevo tipo de la Celda
     */
    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve true si la Celda tiene valor (no es vacia).
     * @return Un booleano que indica si la Celda tiene valor
     */
    public boolean tieneValor(){
        return (valor != 0);
    }

    /**
     * Devuelve true si la Celda es valida (es de tipo FIJA o VARIABLE)
     * @return Un booleano que indica si la Celda es valida
     */
    public boolean esValida(){
        return (tipo.equals(TipoCelda.FIJA) || tipo.equals(TipoCelda.VARIABLE));
    }

    /**
     * Crea una <i>Copia profunda</i> de la Celda.
     * @return La copia de la Celda
     */
    public Celda copiaCelda(){
        Celda c = new Celda();
        c.setTipo(this.tipo);
        c.setValor(this.valor);
        return c;
    }

}
