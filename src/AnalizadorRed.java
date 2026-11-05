/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jvera
 */
public class AnalizadorRed {
    private GrafoRedTransporte grafo;

    public AnalizadorRed(GrafoRedTransporte grafo) {
        this.grafo = grafo;
    }

    /**
     * Requerimiento 6: Algoritmo de Dijkstra para la Ruta de Menor Tiempo.
     */
    public String calcularRutaOptima(String criterioOrigen, String criterioDestino) {
        Estacion origen = grafo.buscarEstacion(criterioOrigen);
        Estacion destino = grafo.buscarEstacion(criterioDestino);

        if (origen == null || destino == null) {
            return "Error: Estación de origen o destino no encontrada en el diccionario.";
        }

        ListaEnlazada<Vertice> vertList = grafo.getVertices();
        int n = vertList.getTamanio();

        ParIdValor[] distancias = new ParIdValor[n];
        ParIdValor[] predecesores = new ParIdValor[n];
        boolean[] visitados = new boolean[n]; // Reemplaza al MinHeap

        for (int i = 0; i < n; i++) {
            String idEst = vertList.obtener(i).getEstacion().getId();
            distancias[i] = new ParIdValor(idEst, Double.MAX_VALUE, null);
            predecesores[i] = new ParIdValor(idEst, -1, null);
            visitados[i] = false;
        }

        setDistancia(distancias, origen.getId(), 0.0);

        // Búsqueda lineal en lugar de MinHeap
        for (int count = 0; count < n - 1; count++) {
            // Encontrar el vértice con la distancia mínima que no ha sido visitado
            double min = Double.MAX_VALUE;
            int minIndex = -1;
            String u = null;

            for (int i = 0; i < n; i++) {
                if (!visitados[i] && distancias[i].valor <= min) {
                    min = distancias[i].valor;
                    minIndex = i;
                    u = distancias[i].id;
                }
            }

            if (u == null || min == Double.MAX_VALUE || u.equalsIgnoreCase(destino.getId())) break;
            visitados[minIndex] = true;

            Vertice vU = grafo.buscarVerticePorId(u);
            if (vU == null) continue;

            ListaEnlazada<Arista> ady = vU.getAdyacentes();
            for (int i = 0; i < ady.getTamanio(); i++) {
                Arista ar = ady.obtener(i);
                String v = ar.getDestino().getId();
                double peso = ar.getTiempoMinutos();

                if (getDistancia(distancias, u) + peso < getDistancia(distancias, v)) {
                    double nuevaDist = getDistancia(distancias, u) + peso;
                    setDistancia(distancias, v, nuevaDist);
                    setPredecesor(predecesores, v, u);
                }
            }
        }

        double distFinal = getDistancia(distancias, destino.getId());
        if (distFinal == Double.MAX_VALUE) {
            return "No existe ruta posible entre " + origen.getNombre() + " y " + destino.getNombre() + ".";
        }

        // Reconstrucción del camino
        ListaEnlazada<String> camino = new ListaEnlazada<>();
        String actual = destino.getId();

        while (actual != null) {
            Estacion e = grafo.buscarEstacion(actual);
            camino.agregar(e.getNombre() + " (" + e.getLinea() + ")");
            actual = getPredecesor(predecesores, actual);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RUTA OPTIMA DE MENOR TIEMPO ===\n");
        sb.append("Origen: ").append(origen.getNombre()).append("\n");
        sb.append("Destino: ").append(destino.getNombre()).append("\n");
        sb.append("Tiempo Total Estimado: ").append(String.format("%.2f", distFinal)).append(" minutos\n\n");
        sb.append("Trayecto Paso a Paso:\n");

        for (int i = camino.getTamanio() - 1; i >= 0; i--) {
            int paso = camino.getTamanio() - i;
            sb.append("- ").append(paso).append(". ").append(camino.obtener(i)).append("\n");
        }

        return sb.toString();
    }

    /**
     * Requerimiento 5: Detección de Subredes / Componentes Conexos usando Búsqueda en Profundidad (DFS).
     */
    public String detectarSubredesConexas() {
        ListaEnlazada<Vertice> vertList = grafo.getVertices();
        int n = vertList.getTamanio();
        if (n == 0) return "El grafo está vacío.";

        ListaEnlazada<String> visitados = new ListaEnlazada<>();
        StringBuilder sb = new StringBuilder();
        sb.append("=== DETECCION DE SUBREDES / COMPONENTES CONEXOS (DFS) ===\n");

        int numSubred = 1;
        for (int i = 0; i < n; i++) {
            Vertice v = vertList.obtener(i);
            String id = v.getEstacion().getId();

            if (!visitados.contiene(id)) {
                sb.append("\nSubred / Sector aislado #").append(numSubred).append(":\n");
                ListaEnlazada<String> componente = new ListaEnlazada<>();
                dfsRec(v, visitados, componente);

                for (int j = 0; j < componente.getTamanio(); j++) {
                    Estacion e = grafo.buscarEstacion(componente.obtener(j));
                    sb.append("  - ").append(e.getNombre()).append(" (Línea: ").append(e.getLinea()).append(")\n");
                }
                numSubred++;
            }
        }

        sb.append("\nTotal de subredes aisladas: ").append(numSubred - 1).append("\n");
        return sb.toString();
    }

    private void dfsRec(Vertice u, ListaEnlazada<String> visitados, ListaEnlazada<String> componente) {
        String idU = u.getEstacion().getId();
        visitados.agregar(idU);
        componente.agregar(idU);

        ListaEnlazada<Arista> ady = u.getAdyacentes();
        for (int i = 0; i < ady.getTamanio(); i++) {
            String idV = ady.obtener(i).getDestino().getId();
            if (!visitados.contiene(idV)) {
                Vertice v = grafo.buscarVerticePorId(idV);
                if (v != null) {
                    dfsRec(v, visitados, componente);
                }
            }
        }
    }

    /**
     * Requerimiento 7: Identificación de Hubs de Transferencia (Centralidad de Grado).
     */
    public String identificarHubsTransferencia() {
        ListaEnlazada<Vertice> vertList = grafo.getVertices();
        int n = vertList.getTamanio();
        if (n == 0) return "El grafo está vacío.";

        StringBuilder sb = new StringBuilder();
        sb.append("=== HUBS DE TRANSFERENCIA (CENTRALIDAD DE GRADO) ===\n");
        sb.append("Estaciones ordenadas por mayor número de conexiones directas:\n\n");

        ParIdValor[] grados = new ParIdValor[n];
        for (int i = 0; i < n; i++) {
            Vertice v = vertList.obtener(i);
            grados[i] = new ParIdValor(v.getEstacion().getId(), v.getAdyacentes().getTamanio(), v.getEstacion().getNombre());
        }

        // Ordenamiento por selección (Selection Sort - Descendente)
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (grados[j].valor > grados[maxIdx].valor) {
                    maxIdx = j;
                }
            }
            ParIdValor temp = grados[i];
            grados[i] = grados[maxIdx];
            grados[maxIdx] = temp;
        }

        for (int i = 0; i < n; i++) {
            Estacion e = grafo.buscarEstacion(grados[i].id);
            sb.append(String.format("%d. %s [%s] -> Grado: %d conexiones directas\n",
                    (i + 1), e.getNombre(), e.getLinea(), (int) grados[i].valor));
        }

        return sb.toString();
    }

    // Métodos Auxiliares Internos de Dijkstra

    private double getDistancia(ParIdValor[] arr, String id) {
        for (ParIdValor p : arr) {
            if (p.id.equalsIgnoreCase(id)) return p.valor;
        }
        return Double.MAX_VALUE;
    }

    private void setDistancia(ParIdValor[] arr, String id, double val) {
        for (ParIdValor p : arr) {
            if (p.id.equalsIgnoreCase(id)) {
                p.valor = val;
                return;
            }
        }
    }

    private String getPredecesor(ParIdValor[] arr, String id) {
        for (ParIdValor p : arr) {
            if (p.id.equalsIgnoreCase(id)) return p.texto;
        }
        return null;
    }

    private void setPredecesor(ParIdValor[] arr, String id, String pred) {
        for (ParIdValor p : arr) {
            if (p.id.equalsIgnoreCase(id)) {
                p.texto = pred;
                return;
            }
        }
    }
}