package com.hidatosdecarbono;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private HashMap<Integer,Node> graph;

    public Graph(){
        graph = new HashMap<Integer, Node>();
    }

    public void addNode(Node node){
        graph.put(node.getId(),node);
    }

    /**
     * Devuelve el numero de casillas para rellenar en el grafo.
     * @return
     */
    private int getNumeroCasillasVacias(){
        int numeroCasillasVacias = 0;
        for(Node node: graph.values()){
            if(node.getCelda().getTipo() == TipoCelda.VARIABLE && !node.getCelda().tieneValor()){
                numeroCasillasVacias++;
            }
        }
        return numeroCasillasVacias;
    }
    /* Para debugar el grafo*/
    public void muestraGrafo() {
        for (int i = 1; i < graph.size() + 1; i++) {
            System.out.print(graph.get(i).getId());
            System.out.print(" amb valor:");
            System.out.print(graph.get(i).getCelda().getValor());
            ArrayList<Node> adyacentes = graph.get(i).getAdyacentes();
            System.out.print(" - te els nodes adjacents: ");
            for (int j = 0; j < adyacentes.size(); j++) {
                System.out.print(adyacentes.get(j).getId());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean esSolucionable(){
        int n = getNumeroCasillasVacias();
        Node primerNodo = graph.get(1);
        int i=1;
        for (Node nodo : primerNodo.getAdyacentes()) {
            return hacerMovimiento(nodo, i, n);
        }
        return false;
    }

    private boolean esMovimientoCorrecto(Node nodo, int valor){
        for(Node adyacencia : nodo.getAdyacentes()){
            int valorCeldaAdyacente = adyacencia.getCelda().getValor();
            if((valor+1) == valorCeldaAdyacente || (valor-1) == valorCeldaAdyacente){
                return true;
            }
        }
        return false;
    }
    private boolean hacerMovimiento(Node node, int i, int n){
        return false;
    }
}
