/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Codigo;

import Interfaz.Ventana1;

/**
 *
 * @author edusye
 */
public class Main {

    /**
     * Método principal que sirve como punto de entrada de la aplicación.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ul>
     *   <li>Crea una nueva instancia de la ventana principal (Ventana1)</li>
     *   <li>Centra la ventana en la pantalla usando setLocationRelativeTo(null)</li>
     *   <li>Hace visible la ventana mediante el método show()</li>
     * </ul>
     * @param args the command line arguments
    */
    public static void main(String[] args) {
        // Constructor por defecto - no requiere inicialización específica
        Ventana1 v1 = new Ventana1();
        v1.setLocationRelativeTo(null);
        v1.show();
    }
    
}
