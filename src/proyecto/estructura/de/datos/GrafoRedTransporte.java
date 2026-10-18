package proyecto.estructura.de.datos;

/**
 * Grafo Ponderado Multimodal basado en Lista de Adyacencia Propia.
 */
public class GrafoRedTransporte {

    private ListaEnlazada<Vertice> vertices;
    private TablaHashEstaciones tablaHash;

    public GrafoRedTransporte() {
        this.vertices = new ListaEnlazada<>();
        this.tablaHash = new TablaHashEstaciones();
    }

    public void agregarEstacion(Estacion estacion) {
        if (tablaHash.buscar(estacion.getId()) == null) {
            Vertice nuevo = new Vertice(estacion);
            vertices.agregar(nuevo);
            tablaHash.insertar(estacion.getId(), estacion);
            tablaHash.insertar(estacion.getNombre(), estacion);
        }
    }

    public boolean eliminarEstacion(String idONombre) {
        Estacion e = tablaHash.buscar(idONombre);
        if (e == null) {
            return false;
        }

        String id = e.getId();
        String nombre = e.getNombre();

        // 1. Eliminar aristas incidentes hacia esta estacion en todos los vertices
        for (int i = 0; i < vertices.getTamanio(); i++) {
            vertices.obtener(i).eliminarAristaConDestino(id);
        }

        // 2. Eliminar el vertice del grafo
        Vertice aEliminar = null;
        for (int i = 0; i < vertices.getTamanio(); i++) {
            Vertice v = vertices.obtener(i);
            if (v.getEstacion().getId().equals(id)) {
                aEliminar = v;
                break;
            }
        }

        if (aEliminar != null) {
            vertices.eliminar(aEliminar);
        }

        // 3. Eliminar de la Tabla Hash
        tablaHash.eliminar(id);
        tablaHash.eliminar(nombre);

        return true;
    }

    public void conectarEstaciones(String idOrigen, String idDestino, double tiempo, boolean bidireccional) {
        Vertice vOrigen = buscarVerticePorId(idOrigen);
        Vertice vDestino = buscarVerticePorId(idDestino);

        if (vOrigen != null && vDestino != null) {
            vOrigen.agregarArista(vDestino.getEstacion(), tiempo);
            if (bidireccional) {
                vDestino.agregarArista(vOrigen.getEstacion(), tiempo);
            }
        }
    }

    public Vertice buscarVerticePorId(String id) {
        for (int i = 0; i < vertices.getTamanio(); i++) {
            Vertice v = vertices.obtener(i);
            if (v.getEstacion().getId().equalsIgnoreCase(id)) {
                return v;
            }
        }
        return null;
    }

    public Estacion buscarEstacion(String criterio) {
        return tablaHash.buscar(criterio);
    }

    public ListaEnlazada<Vertice> getVertices() {
        return vertices;
    }

    public void vaciar() {
        this.vertices = new ListaEnlazada<>();
        this.tablaHash = new TablaHashEstaciones();
    }
}
