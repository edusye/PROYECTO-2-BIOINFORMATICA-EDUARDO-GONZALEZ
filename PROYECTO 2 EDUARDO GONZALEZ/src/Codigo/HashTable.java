/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codigo;

import java.util.ArrayList;

/**
 * Clase que implementa un HashTable simple con tamaño variable y manejo básico de colisiones
 *
 * @param <K> Tipo de datos para las claves
 * @param <V> Tipo de datos para los valores
 * @author edusye
 */
public class HashTable<K, V> {
    private Object[] tabla; /** Array que contiene los nodos de la tabla hash */
    private int tamaño; /** Tamaño actual de la tabla */
    private int elementos; /** Número de elementos almacenados en la tabla */

    /**
     * Clase interna que representa un nodo en la tabla hash
     * Cada nodo contiene una clave, un valor y una referencia al siguiente nodo
     */
    class Nodo {
        K clave; /** La clave del nodo */
        V valor; /** El valor asociado a la clave */
        Nodo siguiente; /** Referencia al siguiente nodo en caso de colisión */

        /**
         * Constructor para crear un nuevo nodo.
         *
         * @param clave La clave del elemento
         * @param valor El valor asociado a la clave
         */
        Nodo(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    /**
     * Constructor por defecto.
     * Crea una tabla hash con tamaño inicial de 97.
     */
    public HashTable() {
        tamaño = 97;
        tabla = new Object[tamaño];
        elementos = 0;
    }

    /**
     * Función hash que convierte una clave en un índice de la tabla.
     * 
     * @param clave La clave a convertir
     * @return El índice correspondiente en la tabla
     */
    private int hash(K clave) {
        if (clave == null) return 0;
        return Math.abs(clave.hashCode()) % tamaño;
    }

    /**
     * Verifica si la tabla necesita crecer y la redimensiona si es necesario.
     * Se ejecuta después de cada inserción.
     */
    private void verificarTamaño() {
        double carga = (double) elementos / tamaño;
        if (carga > 0.7) {
            redimensionar(tamaño * 2);
        }
    }

    /**
     * Redimensiona la tabla a un nuevo tamaño.
     * Todos los elementos existentes se reinsertan en la nueva tabla.
     *
     * @param nuevoTamaño El nuevo tamaño para la tabla.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar(int nuevoTamaño) {
        Object[] TablaAnterior = tabla;
        int TamañoAnterior = tamaño;

        tamaño = nuevoTamaño;
        tabla = new Object[tamaño];
        elementos = 0;

        for (int i = 0; i < TamañoAnterior; i++) {
            Nodo actual = (Nodo) TablaAnterior[i];
            while (actual != null) {
                put(actual.clave, actual.valor);
                actual = actual.siguiente;
            }
        }
    }

    /**
     * Inserta un nuevo elemento en la tabla o actualiza uno existente.
     *
     * @param clave La clave del elemento
     * @param valor El valor a asociar con la clave.
     */
    public void put(K clave, V valor) {
        int pos = hash(clave);

        if (tabla[pos] == null) {
            tabla[pos] = new Nodo(clave, valor);
            elementos++;
            verificarTamaño();
            return;
        }

        Nodo actual = (Nodo) tabla[pos];
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                actual.valor = valor; 
                return;
            }
            actual = actual.siguiente;
        }

        Nodo nuevo = new Nodo(clave, valor);
        nuevo.siguiente = (Nodo) tabla[pos];
        tabla[pos] = nuevo;
        elementos++;
        verificarTamaño();
    }

    /**
     * Obtiene el valor asociado a una clave.
     *
     * @param clave La clave a buscar.
     * @return El valor asociado a la clave, o null si no existe
     */
    @SuppressWarnings("unchecked")
    public V get(K clave) {
        int pos = hash(clave);
        Nodo actual = (Nodo) tabla[pos];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Verifica si una clave existe en la tabla.
     *
     * @param clave La clave a verificar
     * @return true si la clave existe, false en caso contrario
     */
    public boolean contiene(K clave) {
        return get(clave) != null;
    }

    /**
     * Elimina un elemento de la tabla.
     *
     * @param clave La clave del elemento a eliminar.
     * @return true si el elemento fue eliminado, false si no existía.
     */
    @SuppressWarnings("unchecked")
    public boolean eliminar(K clave) {
        int pos = hash(clave);

        if (tabla[pos] == null) {
            return false;
        }

        Nodo primerNodo = (Nodo) tabla[pos];
        if (primerNodo.clave.equals(clave)) {
            tabla[pos] = primerNodo.siguiente;
            elementos--;
            return true;
        }

        Nodo actual = primerNodo;
        while (actual.siguiente != null) {
            if (actual.siguiente.clave.equals(clave)) {
                actual.siguiente = actual.siguiente.siguiente;
                elementos--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Obtiene una lista con todas las claves de la tabla.
     *
     * @return ArrayList conteniendo todas las claves.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<K> claves() {
        ArrayList<K> lista = new ArrayList<>();

        for (int i = 0; i < tamaño; i++) {
            Nodo actual = (Nodo) tabla[i];
            while (actual != null) {
                lista.add(actual.clave);
                actual = actual.siguiente;
            }
        }
        return lista;
    }

    /**
     * Obtiene una lista con todos los valores de la tabla.
     *
     * @return ArrayList conteniendo todos los valores.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<V> valores() {
        ArrayList<V> lista = new ArrayList<>();

        for (int i = 0; i < tamaño; i++) {
            Nodo actual = (Nodo) tabla[i];
            while (actual != null) {
                lista.add(actual.valor);
                actual = actual.siguiente;
            }
        }
        return lista;
    }

    /**
     * Obtiene el número de elementos en la tabla.
     *
     * @return El número de pares clave-valor almacenados.
     */
    public int size() {
        return elementos;
    }

    /**
     * Verifica si la tabla está vacía.
     *
     * @return true si no hay elementos, false en caso contrario.
     */
    public boolean isEmpty() {
        return elementos == 0;
    }

    /**
     * Elimina todos los elementos de la tabla.
     *
     * Reinicia la tabla a su tamaño inicial.
     */
    public void clear() {
        tamaño = 97;
        tabla = new Object[tamaño];
        elementos = 0;
    }
}  