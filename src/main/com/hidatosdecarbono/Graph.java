package com.hidatosdecarbono;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase con la representación interna del tablero en forma de grafo que se usa para
 * solucionar los Hidatos. Se usa una representación en forma de Lista de Adyacencias.
 */
public class Graph {
    private HashMap<Integer,Node> graph;
    private int cont = 0;

    /**
     * Creadora por defecto. Crea un Grafo vacío.
     */
    public Graph(){
        graph = new HashMap<Integer, Node>();
    }

    /**
     * Añade un nodo al Grafo
     * @param node Nodo a añadir al Grafo
     */
    public void addNode(Node node){
        graph.put(node.getId(),node);
    }

    /**
     * Devuelve el tamaño del Grafo
     * @return Integer con el tamaño del Grafo
     */
    public int size(){
        return graph.size();
    }

    /**
     * Imprime las adyacencias del grafo por pantalla. Útil para debugging.
     */
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

    /**
     * Indica si el Hidato tiene solución o no.
     * @return true si el Hidato tenía solución. false si no.
     */
    public boolean esSolucionable(){
        int n = graph.values().size();
        Node primerNodo = graph.get(1);
        int i=1;
        //Para hacer mas facil la correspondencia valor<->posicion vector porque valor empieza a 1.
        boolean[] visitados = new boolean[n+1];

        return hacerMovimiento(primerNodo, i, n, visitados);
    }

    public boolean esCreable(){
        int n = graph.values().size();
        Node primerNodo = graph.get(1);
        int i=1;
        //Para hacer mas facil la correspondencia valor<->posicion vector porque valor empieza a 1.
        boolean[] visitados = new boolean[n+1];

        return hacerMovimientoCrear(primerNodo, i, n, visitados);
    }

    private boolean hacerMovimiento(Node node, int nActual, int nMax, boolean[] visitados){

        //Primero comprovamos que no hayamos ya visitado ese nodo.
        if(visitados[node.getId()]) return false;


            Celda celdaNodo = node.getCelda();
            int ultimoValor = celdaNodo.getValor();
            visitados[node.getId()] = true;
        /*Condiciones de finalización:
            -Solo queda una casilla para llenar
            -La ultima casilla a visitar ya tiene el valor que tocaba llenar.
         */
            if (nActual == nMax && (celdaNodo.esVacia())) {
                celdaNodo.setValor(nActual);
                return true;
            } else if (nActual == nMax && celdaNodo.getValor() == nActual) {
                return true;
            }
            //Parte recursiva del algoritmo
            else {

                //Si el nodo visitado es vacío establecemos el valor.
                if (celdaNodo.esVacia()) celdaNodo.setValor(nActual);

                if (checkDistancias(node)) {

                    boolean found = false;
                    //Si found vale true sabemos que nodoEncontrado tiene valor.
                    //Este null es para saltarnos el error del compilador.

                    //buscar si i+1 ja esta colocat
                    Node nodoEncontrado = null;
                    for (Node nodo : node.getAdyacentes()) {
                        if (nodo.getCelda().getValor() == nActual + 1) {
                            found = true;
                            nodoEncontrado = nodo;
                            break;
                        }
                    }
                    if (found) {
                        boolean res = hacerMovimiento(nodoEncontrado, nActual + 1, nMax, visitados);
                        if (res) return true;
                    }

                    //si no, intentar colocarlo a tots els adjacents buits
                    else {
                        for (Node nodo : node.getAdyacentes()) {
                            if (nodo.getCelda().esVacia()) {
                                boolean res = hacerMovimiento(nodo, nActual + 1, nMax, visitados);
                                if (res) return true;
                            }
                        }
                    }
                }
            }
            visitados[node.getId()] = false;
            celdaNodo.setValor(ultimoValor);
        return false;

    }

    private boolean hacerMovimientoCrear(Node node, int nActual, int nMax, boolean[] visitados){
        Celda celdaNodo = node.getCelda();
        int ultimoValor = celdaNodo.getValor();
        visitados[node.getId()] = true;
        /*Condiciones de finalización:
            -Solo queda una casilla para llenar
            -La ultima casilla a visitar ya tiene el valor que tocaba llenar.
         */
        if (nActual == nMax && (celdaNodo.esVacia())) {
            celdaNodo.setValor(nActual);
            return true;
        } else if (nActual == nMax && celdaNodo.getValor() == nActual) {
            return true;
        }
        //Parte recursiva del algoritmo
        else {

            //Si el nodo visitado es vacío establecemos el valor.
            if (celdaNodo.esVacia()) celdaNodo.setValor(nActual);

            if(checkConectividad(node)) {
                ArrayList<Node> adyacentes = node.getAdyacentes();
                int size = adyacentes.size();
                int rand = ThreadLocalRandom.current().nextInt(0, size);
                for (int it = 0; it < size; it++) {
                    int iterador = (it+rand)%size;
                    Node nodo = adyacentes.get(iterador);
                    if (nodo.getCelda().esVacia() && !visitados[nodo.getId()]) {
                        boolean res = hacerMovimientoCrear(nodo, nActual + 1, nMax, visitados);
                            if (res) return true;
                    }
                }
            }
        }

        visitados[node.getId()] = false;
        celdaNodo.setValor(ultimoValor);
        return false;

    }

    /**
     * Comprueba si dos Nodos son adyacentes
     * @param n1 Nodo sobre el que se va a buscar
     * @param n2 Nodo a buscar en n1
     * @return true si n2 estaba en la lista de adyacencias de n1. false si no.
     */
    public boolean sonAdyacentes(Node n1, Node n2){
        Node comprovar = graph.get(n1.getId());
        for (Node nodo : comprovar.getAdyacentes()){
            if (nodo.getId() == n2.getId()) return true;
        }
        return false;
    }

    /**
     * Comprueba si una casilla fija con un valor determinado es adyacente a un nodo
     * @param n1 Nodo sobre el que se va a buscar el valor
     * @param n Integer con el valor que se quiere comprobar si es adyacente a n1
     * @return true si n1 tiene una casilla fija adyacente con el valor n. false si no
     */
    public boolean checkValorAdyacente(Node n1, int n){
        for (Node nodo : n1.getAdyacentes()) {
            if(nodo.getCelda().getValor() == n && nodo.getCelda().getTipo().equals(TipoCelda.FIJA)) return true;
        }
        return false;
    }

    public boolean checkConectividad(Node primerNodo){
        int n = graph.values().size();
        boolean[] visitado = new boolean[n+1];

        //Marquem el primer node com a visitat i l'afegim a la cua
        visitado[primerNodo.getId()] = true;
        LinkedList<Node> cola = new LinkedList<>();
        cola.add(primerNodo);

        while (!cola.isEmpty()){
            /*agafem el primer node de la cua i busquem
             si té nodes adjacents buits i no visitats*/
            Node nodoActual = cola.pollFirst();

            for (Node nodo : nodoActual.getAdyacentes()){
                if(!visitado[nodo.getId()] && nodo.getCelda().esVacia()){
                    visitado[nodo.getId()] = true;
                    cola.add(nodo);
                }
            }

        }
        for(int i = 1; i < n+1; i++){
            Celda actual = graph.get(i).getCelda();
            if(actual.esVacia()){
                if (!visitado[i]) return false;
            }
        }
        return true;
    }

    private boolean checkDistancias(Node node){
        int n = graph.values().size();
        int[] distancias = new int[n+1];
        Arrays.fill(distancias,-1);

        int valorOriginal = node.getCelda().getValor();
        distancias[node.getId()] = 0;
        int d = 0;
        LinkedList<Node> cola = new LinkedList<Node>();
        cola.add(node);

        while(!cola.isEmpty()){
            Node actual = cola.pollFirst();
            //la distancia als nodes adjacents sera la distancia d'aquest node + 1
            d = distancias[actual.getId()] + 1;

            for (Node nodo : actual.getAdyacentes()){
                /*si el node no te una distancia inciada es que no s'ha visitat, per tant li assignem
                  la distancia del node actual + 1 i l'afegim a la cua
                */
                if(distancias[nodo.getId()] == -1 &&(nodo.getCelda().esVacia() || nodo.getCelda().getValor() > valorOriginal)){
                    distancias[nodo.getId()] = d;
                    cola.add(nodo);
                    //comprovem que la cela original i la cela actual estan a una distancia plausible
                    int valorCelaActual = nodo.getCelda().getValor();
                    if (valorCelaActual != 0) {
                        if (valorCelaActual - valorOriginal < d) return false;
                    }
                }
            }
        }
        return true;
    }
}
