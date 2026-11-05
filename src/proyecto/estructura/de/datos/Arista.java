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
    private Estacion id_destino; // Estacion destino a la que apunta esta conexion.
    private double tiempo_min; // Tiempo de viaje entre las estaciones en minutos.

    /** Constructor de la arista.
     * @param id_destino Objeto Estacion al que se llega.
     * @param tiempo_min Tiempo de recorrido estimado en minutos.
     */
    public Arista(Estacion id_destino, double tiempo_min) {
        this.id_destino = id_destino;
        this.tiempo_min = tiempo_min;
    }

    /** Devuelve la estacion de destino.
     * @return El objeto Estacion al que apunta esta conexion.
     */
    public Estacion getid_destino() {
        return id_destino;
    }

    /** Devuelve el tiempo de recorrido en minutos.
     * @return El tiempo estimado en minutos.
     */
    public double gettiempo_min() {
        return tiempo_min;
    }
}