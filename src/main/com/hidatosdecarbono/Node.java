package com.hidatosdecarbono;

import java.util.ArrayList;

public class Node {
    private int id;
    private Celda celda;
    private ArrayList<Node> adyacentes;

    public Node(){}

    public Node(int id, Celda celda, ArrayList<Node> adyacentes){
        this.id = id;
        this.celda = celda;
        this.adyacentes = adyacentes;
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

    public void addAdyacencia(Node node){
        adyacentes.add(node);
    }

    public boolean esVacio(){
        return (id == 0);
    }


}
