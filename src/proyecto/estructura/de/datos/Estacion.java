/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 *
 * @author FO, JV, RP
 * @param <T>
 */
public class Estacion<T> {
    String id;
    String nombre;
    String linea;
    String zona;
    int capacidad_pas_hr;      
    
    public Estacion(String id, String nombre, String linea, String zona, int capacidad_pas_hr){
        this.id = id;
        this.nombre = nombre;
        this.linea = linea;
        this.zona = zona;
        this.capacidad_pas_hr = capacidad_pas_hr;
    }
}    
