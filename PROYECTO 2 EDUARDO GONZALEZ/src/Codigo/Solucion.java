/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codigo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula la lógica para el análisis de ADN.
 * @author edusye
 */
public class Solucion {
    private Adn adn;
    private ArbolBinario arbolPatrones;
    private HashTable<String, ArbolBinario.Nodo> tablaHashPatrones;


    /**
     * Constructor de la clase Solucion.
     * 
     * @param adn La instancia de la clase Adn que contiene la secuencia de ADN cargada.
     */
    public Solucion(Adn adn) {
        this.adn = adn;
        this.arbolPatrones = new ArbolBinario();
        this.tablaHashPatrones = new HashTable<>();
    }

    /**
     * Extrae todos las tripletas de la secuencia de ADN y los almacena en el árbol binario.
     *  
     * @return Una lista de nodos del árbol (patrones con sus frecuencias y posiciones).
     */
    public List<ArbolBinario.Nodo> OrdenadosPorFrecuencia() {
        arbolPatrones.reiniciarExtremos();    
        if (!adn.tieneSecuenciaCargada()) {
            System.err.println("Error: No hay secuencia de ADN cargada.");
            return new ArrayList<>();
        }

        String secuencia = adn.getSecuenciaCompleta();
        final int LONGITUD_PATRON_FIJA = 3;

        if (secuencia.length() < LONGITUD_PATRON_FIJA) {
            System.err.println("Error: La longitud de la secuencia de ADN no es una tripleta (3).");
            return new ArrayList<>();
        }

        arbolPatrones = new ArbolBinario();
        tablaHashPatrones = new HashTable<>();

        int posicionTripleta = 1;  
        for (int i = 0; i <= secuencia.length() - LONGITUD_PATRON_FIJA; i += LONGITUD_PATRON_FIJA) {
            String patron = secuencia.substring(i, i + LONGITUD_PATRON_FIJA);
            ArbolBinario.Nodo nodoExistente = tablaHashPatrones.get(patron);

            if (nodoExistente == null) {
                arbolPatrones.insertar(patron, posicionTripleta);
                ArbolBinario.Nodo nuevoNodo = arbolPatrones.buscar(patron);
                tablaHashPatrones.put(patron, nuevoNodo);
            } else {
                arbolPatrones.insertar(patron, posicionTripleta);
            } 
            posicionTripleta++;
        }

        return arbolPatrones.OrdenadosPorFrecuencia();
    }

    
     /**
     * Busca un patrón específico utilizando la HashTable para mayor eficiencia.
     *
     * @param patron El patrón de ADN a buscar.
     * @return El Nodo que contiene la información del patrón (frecuencia, posiciones).
     */
    public ArbolBinario.Nodo buscarPatron(String patron) {
        if (tablaHashPatrones == null || tablaHashPatrones.isEmpty()) {
            System.err.println("Error (Solucion): No hay patrones cargados en la tabla hash. Carga una secuencia primero.");
            return null;
        }
        return tablaHashPatrones.get(patron);
    }
    
    /**
    * Obtiene todos los patrones únicos ordenados por el primer carácter para llenar la lista en Ventana3.
    *
    * @return Lista de patrones ordenados alfabéticamente
    */
    public List<String> OrdenadosAlfabeticamente() {
        if (tablaHashPatrones == null || tablaHashPatrones.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> patrones = new ArrayList<>();
        for (String patron : tablaHashPatrones.claves()) {
            patrones.add(patron);
        }

        patrones.sort(String::compareTo);
        return patrones;
    }

    /**
     * Verifica si hay patrones cargados
     * 
     * @return true si hay patrones, false en caso contrario.
     */
    public boolean tienePatronesCargados() {
        return tablaHashPatrones != null && !tablaHashPatrones.isEmpty();
    }
    
    /**
     * Obtiene el patrón más frecuente en tiempo O(log n) promedio.
     * Usa el método optimizado del árbol binario.
     * 
     * @return El nodo con el patrón más frecuente, o null si no hay patrones
     */
    public ArbolBinario.Nodo getPatronMasFrecuente() {
        if (!tienePatronesCargados()) {
            return null;
        }
        return arbolPatrones.patronMasFrecuente();
    }

   /**
    * Obtiene el patrón menos frecuente en tiempo O(log n) promedio.
    * Usa el método optimizado del árbol binario.
    * 
    * @return El nodo con el patrón menos frecuente, o null si no hay patrones
    */
    public ArbolBinario.Nodo getPatronMenosFrecuente() {
        if (!tienePatronesCargados()) {
            return null;
        }
        return arbolPatrones.patronMenosFrecuente();
    }
}