/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 *
 * @author FO, JV, RP
 */
public class Arista {
    Estacion id_destino;
    int tiempo_min;
    
    public Arista(Estacion id_destino, int tiempo_min){
        this.id_destino = id_destino;
        this.tiempo_min = tiempo_min;
    }
    
    public Estacion getid_destino() {
        return id_destino;
    }
    
    public int gettiempo_min(){
        return tiempo_min;
    }
}
