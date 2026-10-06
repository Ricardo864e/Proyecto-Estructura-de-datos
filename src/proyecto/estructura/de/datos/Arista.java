/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 *
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class Arista {
    String id_destino;
    int tiempo_min;
    
    public Arista(String id_destino, int tiempo_min){
        this.id_destino = id_destino;
        this.tiempo_min = tiempo_min;
    }
}
