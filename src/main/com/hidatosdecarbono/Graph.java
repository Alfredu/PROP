package com.hidatosdecarbono;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

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
        int n = graph.values().size();
        Node primerNodo = graph.get(1);
        int i=1;
        //Para hacer mas facil la correspondencia valor<->posicion vector porque valor empieza a 1.
        boolean[] visitados = new boolean[n+1];
        return hacerMovimiento(primerNodo, i, n, visitados);
    }

    /**
     *
     * @param node
     * @param i
     * @param n
     * @return
     */
    private boolean hacerMovimiento(Node node, int i, int n, boolean[] visitados){

        if(visitados[node.getId()]) return false;

        Celda celdaNodo = node.getCelda();
        int ultimoValor = celdaNodo.getValor();
        visitados[node.getId()] = true;

        if(i==n && (celdaNodo.esVacia())){
            celdaNodo.setValor(i);
            return true;
        }
        else if (i==n && celdaNodo.getValor() == i){
            return true;
        }
        //chicha
        else{
            //si estic buit em poso valor
            if(celdaNodo.esVacia()) celdaNodo.setValor(i);
            boolean found = false;
            //Si found vale true sabemos que nodoEncontrado tiene valor.
            //Este null es para saltarnos el error del compilador.

            //buscar si i+1 ja esta colocat
            Node nodoEncontrado = null;
            for(Node nodo : node.getAdyacentes()){
                if(nodo.getCelda().getValor() == i+1){
                    found = true;
                    nodoEncontrado = nodo;
                    break;
                }
            }
            if(found){
                boolean res = hacerMovimiento(nodoEncontrado, i+1, n, visitados);
                if(res) return true;
            }

            //si no, intentar colocarlo a tots els adjacents buits
            else {
                for (Node nodo : node.getAdyacentes()) {
                    if(nodo.getCelda().esVacia()) {
                        boolean res = hacerMovimiento(nodo, i + 1, n, visitados);
                        if (res) return true;
                    }
                }
            }
        }
        visitados[node.getId()] = false;
        celdaNodo.setValor(ultimoValor);
        return false;
    }
}
