package proyecto.estructura.de.datos;

/**
 * Grafo Ponderado Multimodal basado en Lista de Adyacencia Propia.
 */
public class Grafo_De_Transporte {

    private Lista<Vertice> vertices;
    private Hash_Estacioness tablaHash;

    /**
     * Constructor de la clase. Inicializa la lista de vértices y la tabla hash.
     */
    public Grafo_De_Transporte(){
        this.vertices = new Lista<>();
        this.tablaHash = new Hash_Estacioness();
    }

    /**
     * Agrega una nueva estación al grafo si no existe previamente en la tabla hash.
     * 
     * @param estacion la estación a agregar
     */
    public void agregarEstacion(Estacion estacion){
        if(tablaHash.buscar(estacion.getid()) == null){
            Vertice nuevo = new Vertice(estacion);
            vertices.Insertar(nuevo);
            tablaHash.insertar(estacion.getid(), estacion);
            tablaHash.insertar(estacion.getnombre(), estacion);
        }
    }

    /**
     * Elimina una estación del grafo por su ID o nombre.
     * 
     * @param nombre_o_id el ID o nombre de la estación a eliminar
     * @return true si se eliminó con éxito, false si no se encontró
     */
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

    /**
     * Conecta dos estaciones mediante una arista con un peso/tiempo.
     * 
     * @param idOrigen ID de la estación origen
     * @param idDestino ID de la estación destino
     * @param tiempo peso o tiempo de la conexión
     * @param bidirecional indica si la conexión es de ida y vuelta
     */
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

    /**
     * Busca un vértice dentro de la lista según el ID de la estación.
     * 
     * @param id el identificador de la estación
     * @return el vértice correspondiente o null si no existe
     */
    public Vertice buscarVerticePorId(String id){
        for(int i = 0; i < vertices.Tamano(); i++){
            Vertice v = vertices.Agarrar(i);
            if(v.getEstacion().getid().equalsIgnoreCase(id)){
                return v;
            }
        }
        return null;
    }

    /**
     * Busca una estación en la tabla hash por su criterio (ID o nombre).
     * 
     * @param criterio el ID o nombre de la estación
     * @return la estación encontrada o null si no está
     */
    public Estacion buscarEstacion(String criterio){
        return tablaHash.buscar(criterio);
    }

    /**
     * Obtiene la lista completa de vértices.
     * 
     * @return lista de vértices del grafo
     */
    public Lista<Vertice> getVertices(){
        return vertices;
    }

    /**
     * Vacía el grafo reinicializando la lista y la tabla hash.
     */
    public void vaciar(){
        this.vertices = new Lista<>();
        this.tablaHash = new Hash_Estacioness();
    }
}