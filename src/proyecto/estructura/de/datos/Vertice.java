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
    private Estacion estacion; // Objeto Estacion que representa este vertice.
    private Lista<Arista> adyacentes; // Lista de conexiones salientes desde esta estacion.

    /** Constructor del Vertice.
     * @param estacion La estacion que representa este vertice en el grafo.
     */
    public Vertice(Estacion estacion) {
        this.estacion = estacion;
        this.adyacentes = new Lista<>();
    }

    /** Agrega una conexion (arista) hacia otra estacion con un tiempo determinado.
     * Si la conexion ya existia, la reemplaza con el nuevo tiempo.
     * 
     * @param destino Estacion a la que se conecta.
     * @param tiempo_min Tiempo estimado del recorrido en minutos.
     */
    public void agregarArista(Estacion destino, double tiempo_min) {
        for (int i = 0; i < adyacentes.Tamano(); i += 1) { // Si ya existe una arista hacia ese destino, la eliminamos primero para actualizarla
            Arista uno = adyacentes.Agarrar(i);
            if (uno.getid_destino().getid().equals(destino.getid())) {
                adyacentes.Eliminar(uno);
                break;
            }
        }
        adyacentes.Insertar(new Arista(destino, tiempo_min)); // Agregamos la nueva conexion
    }

    /** Elimina la conexion (arista) dirigida hacia una estacion especifica.
     * 
     * @param id_destino ID de la estacion destino que se desea desconectar.
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

    /** Devuelve la estacion asociada al vertice.
     * @return El objeto Estacion guardado.
     */
    public Estacion getEstacion() { 
        return estacion; 
    }

    /** Devuelve la lista de aristas adyacentes.
     * @return Lista con las conexiones de la estacion.
     */
    public Lista<Arista> getAdyacentes() {
        return adyacentes;
    }
}