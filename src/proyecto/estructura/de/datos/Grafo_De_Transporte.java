package proyecto.estructura.de.datos;

/**
 * Grafo Ponderado Multimodal basado en Lista de Adyacencia Propia.
 */
public class Grafo_De_Transporte {

    private Lista<Vertice> vertices;
    private Hash_Estacioness tablaHash;

    public Grafo_De_Transporte(){
        this.vertices = new Lista<>();
        this.tablaHash = new Hash_Estacioness();
    }

    public void agregarEstacion(Estacion estacion){
        if(tablaHash.buscar(estacion.getid()) == null){
            Vertice nuevo = new Vertice(estacion);
            vertices.Insertar(nuevo);
            tablaHash.insertar(estacion.getid(), estacion);
            tablaHash.insertar(estacion.getnombre(), estacion);
        }
    }

    public boolean eliminarEstacion(String nombre_o_id){
        Estacion cual = tablaHash.buscar(nombre_o_id);
        if(cual == null){
            return false;
        }

        String id = cual.getid();
        String nombre = cual.getnombre();

        // 1. Eliminar aristas incidentes hacia esta estacion en todos los vertices
        for(int i = 0; i < vertices.Tamano(); i++){
            vertices.Agarrar(i).eliminarArista(id);
        }

        // 2. Eliminar el vertice del grafo
        Vertice aEliminar = null;
        for(int i = 0; i < vertices.Tamano(); i++){
            Vertice v = vertices.Agarrar(i);
            if(v.getEstacion().getid().equals(id)){
                aEliminar = v;
                break;
            }
        }

        if(aEliminar != null){
            vertices.Eliminar(aEliminar);
        }

        // 3. Eliminar de la Tabla Hash
        tablaHash.eliminar(id);
        tablaHash.eliminar(nombre);
        return true;
    }

    public void conectarEstaciones(String idOrigen, String idDestino, double tiempo, boolean bidirecional){
        Vertice vertiOrigen = buscarVerticePorId(idOrigen);
        Vertice vertiDestino = buscarVerticePorId(idDestino);

        if(vertiOrigen != null && vertiDestino != null){
            vertiOrigen.agregarArista(vertiDestino.getEstacion(), tiempo);
            if (bidirecional) {
                vertiDestino.agregarArista(vertiOrigen.getEstacion(), tiempo);
            }
        }
    }

    public Vertice buscarVerticePorId(String id){
        for(int i = 0; i < vertices.Tamano(); i++){
            Vertice v = vertices.Agarrar(i);
            if(v.getEstacion().getid().equalsIgnoreCase(id)){
                return v;
            }
        }
        return null;
    }

    public Estacion buscarEstacion(String criterio){
        return tablaHash.buscar(criterio);
    }

    public Lista<Vertice> getVertices(){
        return vertices;
    }

    public void vaciar(){
        this.vertices = new Lista<>();
        this.tablaHash = new Hash_Estacioness();
    }
}
