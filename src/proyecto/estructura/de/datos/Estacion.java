/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 *
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
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
    
    public String getid() { 
        return id; 
    }
    
    public String getnombre() { 
        return nombre; 
    }
    
    public String getlinea() { 
        return linea; 
    }
    
    public String getzona() { 
        return zona; 
    }
    
    public int getcapacidad_pas_hr() { 
        return capacidad_pas_hr; 
    }
    
    public String aCSV() {
        return id + "," + nombre + "," + linea + "," + zona + "," + capacidad_pas_hr;
    }
}    
