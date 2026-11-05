/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/** *  <b> Clase Nodo. </b>
 * Esta clase es la base para construir las listas enlazadas y las colas del proyecto.
 * Usamos T para que el nodo pueda guardar cualquier tipo de dato (Strings, Enteros, Estaciones, Aristas...).
 * Sin tener que crear muchas clases nodo para cada uno.
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 * @param <T>
 */
public class Nodo<T> {
    T dato; // Elemento o valor guardado dentro del nodo.
    Nodo<T> siguiente; // Apuntador o referencia al siguiente nodo en la estructura.

    /** Constructor del nodo.
     * @param dato El elemento que queremos guardar en este nodo.
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
