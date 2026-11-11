package proyecto.estructura.de.datos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class AnalizadorRed {
    private Grafo_De_Transporte grafo; // Referencia al grafo de transporte que contiene la red.

    /** Constructor del analizador de red.
     * @param grafo El grafo de transporte con el que va a trabajar el analizador.
     */
    public AnalizadorRed(Grafo_De_Transporte grafo){
        this.grafo = grafo;
    }

    /**Algoritmo de Dijkstra para la Ruta de Menor Tiempo.
     * Encuentra la ruta mas rapida entre dos estaciones utilizando busqueda lineal para la distancia minima.
     * 
     * @param criterioOrigen Nombre o ID de la estacion de origen.
     * @param criterioDestino Nombre o ID de la estacion de destino.
     * @return Un texto formateado con el desglose del tiempo total y el trayecto paso a paso.
     */
    public String calcularRutaOptima(String criterioOrigen, String criterioDestino){
        Estacion origen = grafo.buscarEstacion(criterioOrigen);
        Estacion destino = grafo.buscarEstacion(criterioDestino);

        if (origen == null || destino == null) {
            return "Error: Estación de origen o destino no encontrada en el diccionario.";
        }

        Lista<Vertice> vertList = grafo.getVertices();
        int n = vertList.Tamano();

        ParIdValor[] distancias = new ParIdValor[n]; // Guarda las distancias minimas acumuladas a cada vertice.
        ParIdValor[] predecesores = new ParIdValor[n]; // Guarda el vertice previo en la ruta optima.
        boolean[] visitados = new boolean[n]; // Lleva el control de nodos evaluados (reemplaza al MinHeap).

        // Inicializacion de estructuras auxiliares
        for (int i = 0; i < n; i++){
            String idEst = vertList.Agarrar(i).getEstacion().getid();
            distancias[i] = new ParIdValor(idEst, Double.MAX_VALUE, null);
            predecesores[i] = new ParIdValor(idEst, -1, null);
            visitados[i] = false;
        }

        setDistancia(distancias, origen.getid(), 0.0);

        // Busqueda lineal para obtener la distancia minima
        for (int count = 0; count < n - 1; count++){
            double min = Double.MAX_VALUE;
            int minIndex = -1;
            String u = null;

            for (int i = 0; i < n; i++) {
                if (!visitados[i] && distancias[i].valor <= min){
                    min = distancias[i].valor;
                    minIndex = i;
                    u = distancias[i].id;
                }
            }

            if (u == null || min == Double.MAX_VALUE || u.equalsIgnoreCase(destino.getid())) break;
            visitados[minIndex] = true;

            Vertice vU = grafo.buscarVerticePorId(u);
            if (vU == null) continue;

            Lista<Arista> ady = vU.getAdyacentes();
            for (int i = 0; i < ady.Tamano(); i++) {
                Arista ar = ady.Agarrar(i);
                String v = ar.getid_destino().getid();
                double peso = ar.gettiempo_min();

                // Evaluamos si el camino a traves de 'u' es mas corto que el registrado previamente para 'v'
                if (getDistancia(distancias, u) + peso < getDistancia(distancias, v)){
                    double nuevaDist = getDistancia(distancias, u) + peso;
                    setDistancia(distancias, v, nuevaDist);
                    setPredecesor(predecesores, v, u);
                }
            }
        }

        double distFinal = getDistancia(distancias, destino.getid());
        if (distFinal == Double.MAX_VALUE){
            return "No existe ruta posible entre " + origen.getnombre() + " y " + destino.getnombre() + ".";
        }

        // Reconstruccion del camino desde el destino hasta el origen
        Lista<String> camino = new Lista<>();
        String actual = destino.getid();

        while (actual != null) {
            Estacion e = grafo.buscarEstacion(actual);
            camino.Insertar(e.getnombre() + " (" + e.getlinea() + ")");
            actual = getPredecesor(predecesores, actual);
        }

        StringBuilder mensae = new StringBuilder();
        mensae.append("RUTA OPTIMA DE MENOR TIEMPO\n");
        mensae.append("Origen: ").append(origen.getnombre()).append("\n");
        mensae.append("Destino: ").append(destino.getnombre()).append("\n");
        mensae.append("Tiempo Total Estimado: ").append(String.format("%.2f", distFinal)).append(" minutos\n\n");
        mensae.append("Trayecto Paso a Paso:\n");

        for (int i = camino.Tamano() - 1; i >= 0; i--){
            int paso = camino.Tamano() - i;
            mensae.append("- ").append(paso).append(". ").append(camino.Agarrar(i)).append("\n");
        }

        return mensae.toString();
    }

    /**Detección de Subredes / Usando B&uacute;squeda en Profundidad (DFS).
     * Recorre todo el grafo identificando componentes conexos o sectores aislados dentro del sistema.
     * 
     * @return Un String detallado con cada subred encontrada y el total de subredes aisladas.
     */
    public String detectarSubredesConexas(){
        Lista<Vertice> vertList = grafo.getVertices();
        int n = vertList.Tamano();
        if (n == 0) return "El grafo está vacío.";

        Lista<String> visitados = new Lista<>(); // Controla las estaciones que ya pertenecen a alguna subred
        StringBuilder sb = new StringBuilder();
        sb.append("Detección de subredes / Componentes conexos (DFS)\n");

        int numSubred = 1;
        for (int i = 0; i < n; i++){
            Vertice v = vertList.Agarrar(i);
            String id = v.getEstacion().getid();

            if (!visitados.Contiene(id)) {
                sb.append("\nSubred / Sector aislado #").append(numSubred).append(":\n");
                Lista<String> componente = new Lista<>();
                dfsRec(v, visitados, componente);

                for (int j = 0; j < componente.Tamano(); j++){
                    Estacion e = grafo.buscarEstacion(componente.Agarrar(j));
                    sb.append("  - ").append(e.getnombre()).append(" (Línea: ").append(e.getlinea()).append(")\n");
                }
                numSubred++;
            }
        }

        sb.append("\nTotal de subredes aisladas: ").append(numSubred - 1).append("\n");
        return sb.toString();
    }

    /** Metodo recursivo auxiliar para realizar la busqueda en profundidad (DFS).
     * 
     * @param u Vertice desde el cual se inicia la exploraci&oacute;n.
     * @param visitados Lista global de ids de estaciones ya exploradas.
     * @param componente Lista donde se guardan los ids de la subred actual.
     */
    private void dfsRec(Vertice u, Lista<String> visitados, Lista<String> componente){
        String idU = u.getEstacion().getid();
        visitados.Insertar(idU);
        componente.Insertar(idU);

        Lista<Arista> ady = u.getAdyacentes();
        for (int i = 0; i < ady.Tamano(); i++){
            String idV = ady.Agarrar(i).getid_destino().getid();
            if (!visitados.Contiene(idV)) {
                Vertice v = grafo.buscarVerticePorId(idV);
                if (v != null) {
                    dfsRec(v, visitados, componente);
                }
            }
        }
    }

    /**Identificación de Hubs de Transferencia (Centralidad de Grado).
     * Calcula el grado (conexiones directas) de cada estaci&oacute;n y las ordena de mayor a menor.
     * 
     * @return Texto con la lista ordenada de estaciones con mayor numero de conexiones.
     */
    public String identificarHubsTransferencia(){
        Lista<Vertice> vertList = grafo.getVertices();
        int n = vertList.Tamano();
        if (n == 0) return "El grafo está vacío.";

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Hubs de transferencia.\n");
        mensaje.append("Estaciones ordenadas por mayor número de conexiones directas:\n\n");

        ParIdValor[] grados = new ParIdValor[n];
        for (int i = 0; i < n; i++){
            Vertice v = vertList.Agarrar(i);
            grados[i] = new ParIdValor(v.getEstacion().getid(), v.getAdyacentes().Tamano(), v.getEstacion().getnombre());
        }

        // Ordenamiento por seleccion (Selection Sort - Descendente)
        for (int i = 0; i < n - 1; i++){
            int maxx = i;
            for (int j = i + 1; j < n; j++) {
                if (grados[j].valor > grados[maxx].valor) {
                    maxx = j;
                }
            }
            ParIdValor temp = grados[i];
            grados[i] = grados[maxx];
            grados[maxx] = temp;
        }

        for (int i = 0; i < n; i++){
            Estacion e = grafo.buscarEstacion(grados[i].id);
            mensaje.append(String.format("%d. %s [%s] -> Grado: %d conexiones directas\n",
                    (i + 1), e.getnombre(), e.getlinea(), (int) grados[i].valor));
        }

        return mensaje.toString();
    }

    // Metodos Auxiliares Internos para el Algoritmo de Dijkstra

    /** Busca la distancia acumulada guardada para un ID de estacion.
     * 
     * @param arr Arreglo con pares ID-Valor de distancias.
     * @param id ID de la estacion a buscar.
     * @return El valor numerico de la distancia o Double.MAX_VALUE si no se encuentra.
     */
    private double getDistancia(ParIdValor[] arr, String id){
        for (ParIdValor p : arr) {
            if (p.id.equalsIgnoreCase(id)) return p.valor;
        }
        return Double.MAX_VALUE;
    }

    /** Actualiza el valor de la distancia acumulada de una estacion.
     * 
     * @param arr Arreglo con pares ID-Valor de distancias.
     * @param id ID de la estaci&oacute;n.
     * @param val Nuevo valor de la distancia.
     */
    private void setDistancia(ParIdValor[] arr, String id, double val){
        for (ParIdValor p : arr){// usamos los dos puntos para no sacar el valor manualmente.
            if (p.id.equalsIgnoreCase(id)) {
                p.valor = val;
                return;
            }
        }
    }

    /** Busca el ID del predecesor de una estacion en el camino.
     * 
     * @param arr Arreglo con pares ID-Valor de predecesores.
     * @param id ID de la estacion actual.
     * @return El ID de la estacion predecesora o null si no existe.
     */
    private String getPredecesor(ParIdValor[] arr, String id){
        for (ParIdValor p : arr){ // usamos los dos puntos para no sacar el valor manualmente.
            if (p.id.equalsIgnoreCase(id)) return p.texto;
        }
        return null;
    }

    /** Asigna el ID del predecesor para una estacion.
     * 
     * @param arr Arreglo con pares ID-Valor de predecesores.
     * @param id ID de la estacion actual.
     * @param pred ID de la estacion previa en la ruta.
     */
    private void setPredecesor(ParIdValor[] arr, String id, String pred){
        for (ParIdValor p : arr){// usamos los dos puntos para no sacar el valor manualmente.
            if (p.id.equalsIgnoreCase(id)){
                p.texto = pred;
                return;
            }
        }
    }
}