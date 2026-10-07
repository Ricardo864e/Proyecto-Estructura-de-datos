/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/** <b> Clase Vertice. </b>
 * Representa un nodo dentro del grafo de transporte (un punto de la red).
 * Guarda la estación asociada y la lista de aristas (conexiones) que salen de ella.
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira
 */
public class Vertice {
    private Estacion estacion;
    private Lista<Arista> adyacentes;

    /** Constructor del Vertice.
     * @param estacion La estación que representa este vértice en el grafo.
     */
    public Vertice(Estacion estacion) {
        this.estacion = estacion;
        this.adyacentes = new Lista<>();
    }

    /** Agrega una conexión (arista) hacia otra estación con un tiempo determinado.
     * Si la conexión ya existía, la reemplaza con el nuevo tiempo.
     * @param destino Estación a la que se conecta.
     * @param tiempoMin Tiempo de traslado en minutos.
     */
    public void agregarArista(Estacion destino, int tiempoMin) {
        for (int i = 0; i < adyacentes.Tamano(); i += 1) { // Si ya existe una arista hacia ese destino, la eliminamos primero para actualizarla
            Arista uno = adyacentes.Agarrar(i);
            if (uno.getid_destino().getid().equals(destino.getid())) {
                adyacentes.Eliminar(uno);
                break;
            }
        }
        adyacentes.Insertar(new Arista(destino, tiempoMin)); // Agregamos la nueva conexión
    }

    /** Elimina la conexión (arista) dirigida hacia una estación específica.
     * @param id_destino
     */
    public void eliminarArista(String id_destino) {
        for (int i = 0; i < adyacentes.Tamano(); i += 1) {
            Arista uno = adyacentes.Agarrar(i);
            if (uno.getid_destino().getid().equals(id_destino)) {
                adyacentes.Eliminar(uno);
                break;
            }
        }
    }

    public Estacion getEstacion() { 
        return estacion; 
    }

    public Lista<Arista> getAdyacentes() {
        return adyacentes;
    }
}