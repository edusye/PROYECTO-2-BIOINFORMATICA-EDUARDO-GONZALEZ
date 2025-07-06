/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codigo;


import java.util.ArrayList;
import java.util.List;

/**
 * Clase para la implementacion de un Árbol Binario de Búsqueda para el analisis de ADN.
 * @author edusye
 */
public class ArbolBinario {
    private Nodo raiz;
    private Nodo patronMasFrecuente;
    private Nodo patronMenosFrecuente;
    
    /**
     * Clase interna que representa un nodo del árbol
     */
    public class Nodo {
        public String patron;
        public int frecuencia;
        public List<Integer> posiciones;
        Nodo izquierdo, derecho;
        
        /**
         * Constructor del nodo.
         * 
         * @param patron el patrón de ADN a almacenar.
         * @param posicionTripleta la posición inicial donde se encontró el patrón
         */
        Nodo(String patron, int posicionTripleta) {
            this.patron = patron;
            this.frecuencia = 1;
            this.posiciones = new ArrayList<>();
            this.posiciones.add(posicionTripleta);
        }
    }
    
    /**
     * Inserta un patrón en el árbol o incrementa su frecuencia si ya existe.
     * 
     * @param patron el patrón de ADN a insertar
     * @param posicionTripleta la posición donde se encontró el patrón
     */
    public void insertar(String patron, int posicionTripleta) {
        raiz = insertar(raiz, patron, posicionTripleta);
    }
    
    /**
     * Método auxiliar recursivo para insertar un patrón.
     * 
     * @param nodo el nodo actual en la recursión
     * @param patron el patrón a insertar
     * @param valor la posición del patrón
     * @return el nodo actualizado
     */
    private Nodo insertar(Nodo nodo, String patron, int valor) {
        if (nodo == null) {
            Nodo nuevoNodo = new Nodo(patron, valor);
            actualizarExtremos(nuevoNodo);
            return nuevoNodo;
        }

        int comp = patron.compareTo(nodo.patron);
        if (comp < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, patron, valor);
        } else if (comp > 0) {
            nodo.derecho = insertar(nodo.derecho, patron, valor);
        } else {
            nodo.frecuencia++;
            nodo.posiciones.add(valor);
            actualizarExtremos(nodo);
        }
        return nodo;
    }
    
    /**
     * Busca un patrón específico en el árbol.
     * 
     * @param patron el patrón a buscar
     * @return el nodo que contiene el patrón, o null si no se encuentra
     */
    public Nodo buscar(String patron) {
        return buscar(raiz, patron);
    }
    
    /**
     * Método auxiliar recursivo para buscar un patrón.
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
     * Obtiene todos los patrones ordenados por frecuencia.
     * 
     * @return lista de nodos ordenados por frecuencia de mayor a menor
     */
    public List<Nodo> OrdenadosPorFrecuencia() {
        List<Nodo> patrones = new ArrayList<>();
        recorrerEnOrden(raiz, patrones);
        patrones.sort((n1, n2) -> Integer.compare(n2.frecuencia, n1.frecuencia));
        return patrones;
    }
    
    /**
     * Obtiene todos los patrones ordenados alfabéticamente.
     * 
     * @return lista de nodos ordenados alfabéticamente
     */
    public List<Nodo> OrdenadosAlfabeticamente() {
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
     * Obtiene el patrón más frecuente.
     * Mantiene la referencia actualizada durante las inserciones.
     * 
     * @return el nodo con el patrón más frecuente, o null si el árbol está vacío
     */
    public Nodo patronMasFrecuente() {
        return patronMasFrecuente;
    }

    /**
     * Obtiene el patrón menos frecuente.
     * Mantiene la referencia actualizada durante las inserciones.
     * 
     * @return el nodo con el patrón menos frecuente, o null si el árbol está vacío
     */
    public Nodo patronMenosFrecuente() {
        return patronMenosFrecuente;
    }
    
    /**
     * Actualiza las referencias a los patrones más y menos frecuentes
     */
    private void actualizarExtremos(Nodo nodo) {

        if (patronMasFrecuente == null || nodo.frecuencia > patronMasFrecuente.frecuencia) {
            patronMasFrecuente = nodo;
        }

        if (patronMenosFrecuente == null || nodo.frecuencia < patronMenosFrecuente.frecuencia) {
            patronMenosFrecuente = nodo;
        }
    }
    
    /**
     * Reinicia las referencias para una nueva carga
     */
    public void reiniciarExtremos() {
        patronMasFrecuente = null;
        patronMenosFrecuente = null;
    }   
 
}