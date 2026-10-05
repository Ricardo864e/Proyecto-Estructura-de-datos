/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 * Clase Nodo.
 * Esta clase es la base para construir las listas enlazadas y las colas del proyecto.
 * Usamos T para que sirva para guardar cualquier tipo de dato (Estaciones, Aristas, Strings, etc.)
 * sin tener que crear mil clases nodo para cada uno.
 * @author ricardopereira
 */
public class Nodo<T>{
    T dato;
    Nodo<T> siguiente;
    
    /**
     * Constructor del nodo.
     * @param dato Es el elemento que queremos guardar en este nodo.
     */
    public Nodo(T dato){
        this.dato = dato;
        this.siguiente = null;
    }
}
