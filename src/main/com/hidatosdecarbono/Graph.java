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



    /* Para debugar el grafo
    public void muestraGrafo(){
        for(int i = 1; i < graph.size()+1; i++){
            System.out.print(graph.get(i).getId());
            System.out.print(" amb valor:");
            System.out.print(graph.get(i).getCelda().getValor());
            ArrayList<Node> adyacentes = graph.get(i).getAdyacentes();
            System.out.print(" - te els nodes adjacents: ");
            for(int j = 0; j < adyacentes.size(); j++){
                System.out.print(adyacentes.get(j).getId());
                System.out.print(" ");
            }
            System.out.println();
        }
      }
    */
}
