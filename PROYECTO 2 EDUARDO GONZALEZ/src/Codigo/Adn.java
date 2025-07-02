/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codigo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.File;

/**
 * Clase responsable de obtener los datos de la secuencia de ADN de un txt
 * @author edusye
 */
public class Adn {
    
    private String secuenciaCompleta;
    
    /**
     * Constructor vacío
     */
    public Adn() {
        this.secuenciaCompleta = "";
    }
    
    /**
     * Constructor con secuencia.
     * 
     * @param secuencia La secuencia de ADN
     */
    public Adn(String secuencia) {
        this.secuenciaCompleta = validarSecuencia(secuencia);
    }
    
    /**
     * Abre un JFileChooser para seleccionar y cargar un archivo de secuencia ADN.
     * 
     * @return true si la carga fue exitosa, false si no fue exitosa
     */
    public boolean CargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        
        int resultado = fileChooser.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            return CargarSecuencia(archivoSeleccionado.getAbsolutePath());
        } else {
            return false;
        }
    }
    
    /**
     * Carga la secuencia de ADN desde un archivo de texto.
     * 
     * @param rutaArchivo Ruta del archivo que contiene la secuencia
     * @return true si la carga fue exitosa, false en caso contrario
     */
    public boolean CargarSecuencia(String rutaArchivo) {
        try {
            StringBuilder contenido = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea.trim().toUpperCase());
            }
            reader.close();
            
            this.secuenciaCompleta = validarSecuencia(contenido.toString());
            return !this.secuenciaCompleta.isEmpty();
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Valida que la secuencia solo contenga nucleótidos válidos.
     * 
     * @param secuencia Secuencia a validar
     * @return Secuencia validada (vacía si es inválida)
     */
    private String validarSecuencia(String secuencia) {
        if (secuencia == null) {
            return "";
        }
        for (char c : secuencia.toCharArray()) {
            if (c != 'A' && c != 'T' && c != 'G' && c != 'C') {
                System.err.println("Secuencia inválida: contiene caracteres no válidos");
                return "";
            }
        }
        
        return secuencia;
    }
    
    /**
     * Obtiene la secuencia completa de ADN.
     * 
     * @return La secuencia de ADN
     */
    public String getSecuenciaCompleta() {
        return secuenciaCompleta;
    }
    
    /**
     * Establece una nueva secuencia de ADN.
     * 
     * @param secuencia Nueva secuencia
     */
    public void setSecuenciaCompleta(String secuencia) {
        this.secuenciaCompleta = validarSecuencia(secuencia);
    }
    
    /**
     * Verifica si hay una secuencia cargada.
     * 
     * @return true si hay secuencia, false en caso contrario
     */
    public boolean tieneSecuenciaCargada() {
        return secuenciaCompleta != null && !secuenciaCompleta.isEmpty();
    }
    
    /**
     * Obtiene la longitud de la secuencia.
     * 
     * @return Longitud de la secuencia
     */
    public int getLongitudSecuencia() {
        return secuenciaCompleta != null ? secuenciaCompleta.length() : 0;
    }
    
    /**
     * Obtiene un fragmento específico de la secuencia.
     * 
     * @param inicio Posición de inicio
     * @param fin Posición de fin
     * @return Fragmento de la secuencia
     */
    public String obtenerFragmento(int inicio, int fin) {
        if (secuenciaCompleta == null || inicio < 0 || fin > secuenciaCompleta.length() || inicio >= fin) {
            return "";
        }
        return secuenciaCompleta.substring(inicio, fin);
    }
}