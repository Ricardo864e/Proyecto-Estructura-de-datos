package proyecto.estructura.de.datos;

/**
 * Representa la estructura completa de nuestra red de transporte.
 * 
 * Es la clase principal donde organizamos las estaciones 
 * en una lista de v&eacute;rtices y mantenemos una tabla hash para realizar b&uacute;squedas 
 * r&aacute;pidas por nombre o ID sin tener que recorrer todo el grafo a mano cada vez.
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class Grafo_De_Transporte {

    private Lista<Vertice> vertices;
    private Hash_Estacioness tablaHash;

    /**
     * Constructor de la clase. 
     * Inicializa la lista de v&eacute;rtices y la tabla hash.
     */
    public Grafo_De_Transporte(){
        this.vertices = new Lista<>();
        this.tablaHash = new Hash_Estacioness();
    }

    /**
     * Agrega una nueva estaci&oacute;n a la red.
     * Antes de insertarla, revisa en la tabla hash que no la hayamos registrado antes 
     * para evitar tener nodos duplicados. Si es nueva, la guarda tanto en la lista de v&eacute;rtices 
     * como en la tabla hash (registrándola por su ID y por su Nombre).
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
     * Elimina una estaci&oacute;n por completo.
     * Para borrarla bien sin romper la estructura, se le quita las conexiones 
     * (aristas) que apuntaban a esta estaci&oacute;n a los dem&aacute;s v&eacute;rtices,
     * , se elimina el v&eacute;rtice de la lista principal, y se borra la estaci&oacute;n de la tabla hash.
     * @param nombre_o_id el ID o nombre de la estaci&oacute;n a eliminar
     * @return true si se elimin&oacute; con &eacute;xito, false si no se encontr&oacute;
     */
    public boolean eliminarEstacion(String nombre_o_id){
        Estacion cual = tablaHash.buscar(nombre_o_id);
        if(cual == null){
            return false;
        }

        String id = cual.getid();
        String nombre = cual.getnombre();

        //Eliminar aristas incidentes hacia esta estacion en todos los vertices
        for(int i = 0; i < vertices.Tamano(); i++){
            vertices.Agarrar(i).eliminarArista(id);
        }

        //Eliminar el vertice del grafo
        Vertice aEliminar = null;
        for(int i = 0; i < vertices.Tamano(); i++){
            Vertice v = vertices.Agarrar(i);
            if(v.getEstacion().getid().equalsIgnoreCase(id)){
                aEliminar = v;
                break;
            }
        }

        if(aEliminar != null){
            vertices.Eliminar(aEliminar);
        }

        //Eliminar de la Tabla Hash
        tablaHash.eliminar(id);
        tablaHash.eliminar(nombre);
        return true;
    }
    
    /**
     * Elimina la conexi&oacute;n directa entre dos estaciones en ambas direcciones.
     * Al ser un grafo no dirigido, le quita a la estaci&oacute;n de origen la ruta hacia el destino 
     * y viceversa.
     * 
     * @param idOrigen ID de la primera estaci&oacute;n.
     * @param idDestino ID de la segunda estaci&oacute;n.
     * @return true si se eliminó el tramo con &eacute;xito, false si alguna estaci&oacute;n no existe.
     */
    public boolean eliminarConexion(String idOrigen, String idDestino){
        Vertice vOrigen = buscarVerticePorId(idOrigen);
        Vertice vDestino = buscarVerticePorId(idDestino);

        if (vOrigen != null && vDestino != null) {
            // Eliminar la conexi&oacute;n en ambas direcciones bidireccional.
            boolean eliminadaIda = vOrigen.eliminarArista(idDestino);
            boolean eliminadaVuelta = vDestino.eliminarArista(idOrigen);
            return eliminadaIda || eliminadaVuelta;
        }
        return false;
    }

    /**
     * Conecta dos estaciones mediante una arista con un tiempo.
     * Se hacen las conexiones de forma bidireccionar.
     * Aqu&iacute; se manifiesta la naturaleza de nuestro grafo bidireccional:
     * creamos la arista de ida y al mismo tiempo la arista de vuelta con el mismo peso.
     * @param idOrigen ID de la estaci&oacute;n origen
     * @param idDestino ID de la estación destino
     * @param tiempo peso o tiempo de la conexi&oacute;n
     */
    public void conectarEstaciones(String idOrigen, String idDestino, double tiempo){
        Vertice vertiOrigen = buscarVerticePorId(idOrigen);
        Vertice vertiDestino = buscarVerticePorId(idDestino);

        if(vertiOrigen != null && vertiDestino != null){
            //Agregas la ida (Origen -> Destino)
            vertiOrigen.agregarArista(vertiDestino.getEstacion(), tiempo);
            //Agregas la vuelta (Destino -> Origen)
            vertiDestino.agregarArista(vertiOrigen.getEstacion(), tiempo);
        }
    }

    /**
     * Busca un v&eacute;rtice dentro de la lista seg&uacute;n el ID de la estaci&oacute;n.
     * 
     * @param id el identificador de la estación
     * @return el v&eacute;rtice correspondiente o null si no existe
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
     * Busca una estaci&oacute;n en la tabla hash por su criterio (ID o nombre).
     * 
     * @param criterio el ID o nombre de la estaci&oacute;n
     * @return la estación encontrada o null si no está
     */
    public Estacion buscarEstacion(String criterio){
        return tablaHash.buscar(criterio);
    }

    /**
     * Obtiene la lista completa de v&eacute;rtices.
     * 
     * @return lista de v&eacute;rtices del grafo
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