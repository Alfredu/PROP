package com.hidatosdecarbono;

import java.util.ArrayList;

public class Node {
    private int id;
    private Celda celda;
    private ArrayList<Node> adyacentes;

    public Node(){}

    public Node(int id, Celda celda){
        this.id = id;
        this.celda = celda;
        adyacentes = new ArrayList<Node>();
    }

    public ArrayList<Node> getAdyacentes() {
        return adyacentes;
    }

    public Celda getCelda() {
        return celda;
    }

    public int getId() {
        return id;
    }

    public void addListaAdyacencias(ArrayList<Node> adyacentes){
        this.adyacentes = adyacentes;
    }

    public boolean noEsVacio(){
        return (id != 0);
    }



}
