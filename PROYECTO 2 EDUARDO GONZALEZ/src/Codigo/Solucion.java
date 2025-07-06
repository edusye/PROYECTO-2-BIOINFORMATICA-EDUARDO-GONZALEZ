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
     * Obtiene el patrón más frecuente.
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
    * Obtiene el patrón menos frecuente.
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
    
    /**
     * Genera un lista de colisiones de la tabla hash de patrones.
     * 
     * @return Lista de strings con información detallada de las colisiones
     */
    public ArrayList<String> generarReporteColisiones() {
        if (tablaHashPatrones == null || tablaHashPatrones.isEmpty()) {
            OrdenadosPorFrecuencia();
        }

        if (tablaHashPatrones == null || tablaHashPatrones.isEmpty()) {
            ArrayList<String> reporteVacio = new ArrayList<>();
            reporteVacio.add("No hay patrones cargados para generar el reporte.");
            return reporteVacio;
        }

        return tablaHashPatrones.reporteColisiones();
    }
    
    /**
     * Convierte una tripleta de ADN a su equivalente en ARN.
     * 
     * @param tripletaADN La tripleta de ADN (con T)
     * @return La tripleta equivalente en ARN (con U)
     */
    private String convertirADNaARN(String tripletaADN) {
        return tripletaADN.replace('T', 'U');
    }

   /**
    * Obtiene el aminoácido correspondiente a una tripleta de ARN.
    * 
    * @param tripletaARN La tripleta de ARN
    * @return El aminoácido correspondiente (abreviatura de 3 letras)
    */
    private String obtenerAminoácido(String tripletaARN) {
        switch (tripletaARN) {
            // Fenilalanina
            case "UUU": case "UUC": return "Phe";
            // Leucina
            case "UUA": case "UUG": case "CUU": case "CUC": case "CUA": case "CUG": return "Leu";
            // Serina
            case "UCU": case "UCC": case "UCA": case "UCG": case "AGU": case "AGC": return "Ser";
            // Tirosina
            case "UAU": case "UAC": return "Tyr";
            // Cisteína
            case "UGU": case "UGC": return "Cys";
            // Triptófano
            case "UGG": return "Trp";
            // Prolina
            case "CCU": case "CCC": case "CCA": case "CCG": return "Pro";
            // Histidina
            case "CAU": case "CAC": return "His";
            // Glutamina
            case "CAA": case "CAG": return "Gln";
            // Arginina
            case "CGU": case "CGC": case "CGA": case "CGG": case "AGA": case "AGG": return "Arg";
            // Isoleucina
            case "AUU": case "AUC": case "AUA": return "Ile";
            // Metionina (Inicio)
            case "AUG": return "Met (M)";
            // Treonina
            case "ACU": case "ACC": case "ACA": case "ACG": return "Thr";
            // Asparagina
            case "AAU": case "AAC": return "Asn";
            // Lisina
            case "AAA": case "AAG": return "Lys";
            // Valina
            case "GUU": case "GUC": case "GUA": case "GUG": return "Val";
            // Alanina
            case "GCU": case "GCC": case "GCA": case "GCG": return "Ala";
            // Ácido Aspártico
            case "GAU": case "GAC": return "Asp";
            // Ácido Glutámico
            case "GAA": case "GAG": return "Glu";
            // Glicina
            case "GGU": case "GGC": case "GGA": case "GGG": return "Gly";
            case "UAA": return "(Ocre)";
            case "UAG": return "(Ámbar)";
            case "UGA": return "(Ópalo)";
            default: return "No válida";
        }
    }

    /**
     * Obtiene el nombre completo del aminoácido a partir de su abreviatura.
     * 
     * @param abreviatura La abreviatura del aminoácido.
     * @return El nombre completo del aminoácido.
     */
    private String obtenerNombreAminoácido(String abreviatura) {
        return switch (abreviatura) {
            case "Phe" -> "Fenilalanina";
            case "Leu" -> "Leucina";
            case "Ser" -> "Serina";
            case "Tyr" -> "Tirosina";
            case "Cys" -> "Cisteína";
            case "Trp" -> "Triptófano";
            case "Pro" -> "Prolina";
            case "His" -> "Histidina";
            case "Gln" -> "Glutamina";
            case "Arg" -> "Arginina";
            case "Ile" -> "Isoleucina";
            case "Met (M)" -> "Metionina (Inicio)";
            case "Thr" -> "Treonina";
            case "Asn" -> "Asparagina";
            case "Lys" -> "Lisina";
            case "Val" -> "Valina";
            case "Ala" -> "Alanina";
            case "Asp" -> "Ácido Aspártico";
            case "Glu" -> "Ácido Glutámico";
            case "Gly" -> "Glicina";
            case "(Ocre)" -> "--";
            case "(Ámbar)" -> "--";
            case "(Ópalo)" -> "--";
            case "No válida" -> "Tripleta no válida";
            default -> "Desconocido";
        };
    }
    
    /**
     * Genera la lista de aminoácidos con sus tripletas y frecuencias.
     * 
     * @return String con la lista hecha para mostrar en el textarea.
     */
    public String generarListaAminoácidos() {
        if (!tienePatronesCargados()) {
            List<ArbolBinario.Nodo> patrones = OrdenadosPorFrecuencia();
            if (patrones.isEmpty()) {
                return "No hay patrones cargados. Carga una secuencia de ADN primero.";
            }
        }

        StringBuilder lista = new StringBuilder();
        lista.append("=== REPORTE DE AMINOÁCIDOS ===\n\n");

        List<ArbolBinario.Nodo> patrones = OrdenadosPorFrecuencia();

        java.util.Map<String, java.util.List<ArbolBinario.Nodo>> aminoácidoMap = new java.util.HashMap<>();

        for (ArbolBinario.Nodo nodo : patrones) {
            String tripletaADN = nodo.patron;
            String tripletaARN = convertirADNaARN(tripletaADN);
            String aminoácido = obtenerAminoácido(tripletaARN);

            aminoácidoMap.computeIfAbsent(aminoácido, k -> new java.util.ArrayList<>()).add(nodo);
        }

        for (java.util.Map.Entry<String, java.util.List<ArbolBinario.Nodo>> entry : aminoácidoMap.entrySet()) {
            String aminoácido = entry.getKey();
            java.util.List<ArbolBinario.Nodo> tripletas = entry.getValue();

            lista.append("AMINOÁCIDO: ").append(obtenerNombreAminoácido(aminoácido)).append(" (").append(aminoácido).append(")\n");

            int frecuenciaTotal = tripletas.stream().mapToInt(n -> n.frecuencia).sum();
            lista.append("Frecuencia total: ").append(frecuenciaTotal).append("\n");

            lista.append("Tripletas que lo generan:\n");

            for (ArbolBinario.Nodo nodo : tripletas) {
                String tripletaADN = nodo.patron;
                String tripletaARN = convertirADNaARN(tripletaADN);

                lista.append("  • ADN: ").append(tripletaADN)
                       .append(" -> ARN: ").append(tripletaARN)
                       .append(" (Frecuencia: ").append(nodo.frecuencia).append(")\n");
            }

            lista.append("\n");
        }

        return lista.toString();
    }

}