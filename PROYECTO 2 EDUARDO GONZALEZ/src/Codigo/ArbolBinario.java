/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codigo;


import java.util.ArrayList;
import java.util.List;
/**
 * Implementación  de un Árbol Binario de Búsqueda para el analisis de ADN.
 * 
 * @author edusye
 */
public class ArbolBinario {
    
    private Nodo raiz;
    
    /**
     * Clase interna que representa un nodo del árbol
     */
    class Nodo {
        String patron;
        int frecuencia;
        List<Integer> posiciones;
        Nodo izquierdo, derecho;
        
        /**
         * Constructor del nodo
         * 
         * @param patron el patrón de ADN a almacenar
         * @param posicion la posición inicial donde se encontró el patrón
         */
        Nodo(String patron, int posicion) {
            this.patron = patron;
            this.frecuencia = 1;
            this.posiciones = new ArrayList<>();
            this.posiciones.add(posicion);
        }
    }
    
    /**
     * Inserta un patrón en el árbol o incrementa su frecuencia si ya existe
     * 
     * @param patron el patrón de ADN a insertar
     * @param posicion la posición donde se encontró el patrón
     */
    public void insertar(String patron, int posicion) {
        raiz = insertar(raiz, patron, posicion);
    }
    
    /**
     * Método auxiliar recursivo para insertar un patrón
     * 
     * @param nodo el nodo actual en la recursión
     * @param patron el patrón a insertar
     * @param posicion la posición del patrón
     * @return el nodo actualizado
     */
    private Nodo insertar(Nodo nodo, String patron, int posicion) {
        if (nodo == null) return new Nodo(patron, posicion);
        
        int comp = patron.compareTo(nodo.patron);
        if (comp < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, patron, posicion);
        } else if (comp > 0) {
            nodo.derecho = insertar(nodo.derecho, patron, posicion);
        } else {
            nodo.frecuencia++;
            nodo.posiciones.add(posicion);
        }
        return nodo;
    }
    
    /**
     * Busca un patrón específico en el árbol
     * 
     * @param patron el patrón a buscar
     * @return el nodo que contiene el patrón, o null si no se encuentra
     */
    public Nodo buscar(String patron) {
        return buscar(raiz, patron);
    }
    
    /**
     * Método auxiliar recursivo para buscar un patrón
     * 
     * @param nodo el nodo actual en la recursión
     * @param patron el patrón a buscar
     * @return el nodo que contiene el patrón, o null si no se encuentra
     */
    private Nodo buscar(Nodo nodo, String patron) {
        if (nodo == null || patron.equals(nodo.patron)) return nodo;
        
        return patron.compareTo(nodo.patron) < 0 
            ? buscar(nodo.izquierdo, patron)
            : buscar(nodo.derecho, patron);
    }
    
    /**
     * Obtiene todos los patrones ordenados por frecuencia
     * 
     * @return lista de nodos ordenados por frecuencia de mayor a menor
     */
    public List<Nodo> obtenerPatronesOrdenadosPorFrecuencia() {
        List<Nodo> patrones = new ArrayList<>();
        recorrerEnOrden(raiz, patrones);
        patrones.sort((n1, n2) -> Integer.compare(n2.frecuencia, n1.frecuencia));
        return patrones;
    }
    
    /**
     * Obtiene todos los patrones ordenados alfabéticamente
     * 
     * @return lista de nodos ordenados alfabéticamente
     */
    public List<Nodo> obtenerPatronesOrdenadosAlfabeticamente() {
        List<Nodo> patrones = new ArrayList<>();
        recorrerEnOrden(raiz, patrones);
        return patrones;
    }
    
    /**
     * Método auxiliar para recorrer el árbol en orden y agregar nodos a una lista
     * 
     * @param nodo el nodo actual en la recursión
     * @param resultado la lista donde se agregan los nodos
     */
    private void recorrerEnOrden(Nodo nodo, List<Nodo> resultado) {
        if (nodo != null) {
            recorrerEnOrden(nodo.izquierdo, resultado);
            resultado.add(nodo);
            recorrerEnOrden(nodo.derecho, resultado);
        }
    }
    
    /**
     * Verifica si el árbol está vacío
     * 
     * @return true si el árbol no tiene nodos, false en caso contrario
     */
    public boolean isEmpty() { 
        return raiz == null; 
    }
    
    /**
     * Calcula la altura del árbol
     * 
     * @return la altura del árbol (número de niveles)
     */
    public int altura() { 
        return altura(raiz); 
    }
    
    /**
     * Método auxiliar recursivo para calcular la altura
     * 
     * @param nodo el nodo actual en la recursión
     * @return la altura del subárbol con raíz en el nodo dado
     */
    private int altura(Nodo nodo) {
        return nodo == null ? 0 : 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }
    
    /**
     * Obtiene el patrón con mayor frecuencia
     * 
     * @return el nodo con el patrón más frecuente, o null si el árbol está vacío
     */
    public Nodo patronMasFrecuente() {
        List<Nodo> patrones = obtenerPatronesOrdenadosPorFrecuencia();
        return patrones.isEmpty() ? null : patrones.get(0);
    }
    
    /**
     * Obtiene el patrón con menor frecuencia
     * 
     * @return el nodo con el patrón menos frecuente, o null si el árbol está vacío
     */
    public Nodo patronMenosFrecuente() {
        List<Nodo> patrones = obtenerPatronesOrdenadosPorFrecuencia();
        return patrones.isEmpty() ? null : patrones.get(patrones.size() - 1);
    }
}