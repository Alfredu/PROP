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
}
