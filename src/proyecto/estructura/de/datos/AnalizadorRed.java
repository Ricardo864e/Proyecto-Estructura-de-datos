package proyecto.estructura.de.datos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jvera
 */
public class AnalizadorRed {
    private Grafo_De_Transporte grafo;

    public AnalizadorRed(Grafo_De_Transporte grafo) {
        this.grafo = grafo;
    }

    public String calcularRutaOptima(String criterioOrigen, String criterioDestino) {
        Estacion origen = grafo.buscarEstacion(criterioOrigen);
        Estacion destino = grafo.buscarEstacion(criterioDestino);

        if (origen == null || destino == null) {
            return "Error: Estación de origen o destino no encontrada en el diccionario.";
        }

        Lista<Vertice> vertList = grafo.getVertices();
        int n = vertList.Tamano();

        ParIdValor[] distancias = new ParIdValor[n];
        ParIdValor[] predecesores = new ParIdValor[n];
        boolean[] visitados = new boolean[n];

        for (int i = 0; i < n; i++) {
            String idEst = vertList.Agarrar(i).getEstacion().getid();
            distancias[i] = new ParIdValor(idEst, Double.MAX_VALUE, null);
            predecesores[i] = new ParIdValor(idEst, -1, null);
            visitados[i] = false;
        }

        setDistancia(distancias, origen.getid(), 0.0);
        for (int count = 0; count < n - 1; count++) {
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

            if (u == null || min == Double.MAX_VALUE || u.equalsIgnoreCase(destino.getid())) break;
            visitados[minIndex] = true;

            Vertice vU = grafo.buscarVerticePorId(u);
            if (vU == null) continue;

            Lista<Arista> ady = vU.getAdyacentes();
            for (int i = 0; i < ady.Tamano(); i++) {
                Arista ar = ady.Agarrar(i);
                String v = ar.getid_destino().getid();
                double peso = ar.gettiempo_min();

                if (getDistancia(distancias, u) + peso < getDistancia(distancias, v)) {
                    double nuevaDist = getDistancia(distancias, u) + peso;
                    setDistancia(distancias, v, nuevaDist);
                    setPredecesor(predecesores, v, u);
                }
            }
        }

        double distFinal = getDistancia(distancias, destino.getid());
        if (distFinal == Double.MAX_VALUE) {
            return "No existe ruta posible entre " + origen.getnombre() + " y " + destino.getnombre() + ".";
        }

        Lista<String> camino = new Lista<>();
        String actual = destino.getid();

        while (actual != null) {
            Estacion e = grafo.buscarEstacion(actual);
            camino.Insertar(e.getnombre() + " (" + e.getlinea() + ")");
            actual = getPredecesor(predecesores, actual);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RUTA OPTIMA DE MENOR TIEMPO ===\n");
        sb.append("Origen: ").append(origen.getnombre()).append("\n");
        sb.append("Destino: ").append(destino.getnombre()).append("\n");
        sb.append("Tiempo Total Estimado: ").append(String.format("%.2f", distFinal)).append(" minutos\n\n");
        sb.append("Trayecto Paso a Paso:\n");

        for (int i = camino.Tamano() - 1; i >= 0; i--) {
            int paso = camino.Tamano() - i;
            sb.append("- ").append(paso).append(". ").append(camino.Agarrar(i)).append("\n");
        }

        return sb.toString();
    }

    public String detectarSubredesConexas() {
        Lista<Vertice> vertList = grafo.getVertices();
        int n = vertList.Tamano();
        if (n == 0) return "El grafo está vacío.";

        Lista<String> visitados = new Lista<>();
        StringBuilder sb = new StringBuilder();
        sb.append("=== DETECCION DE SUBREDES / COMPONENTES CONEXOS (DFS) ===\n");

        int numSubred = 1;
        for (int i = 0; i < n; i++) {
            Vertice v = vertList.Agarrar(i);
            String id = v.getEstacion().getid();

            if (!visitados.Contiene(id)) {
                sb.append("\nSubred / Sector aislado #").append(numSubred).append(":\n");
                Lista<String> componente = new Lista<>();
                dfsRec(v, visitados, componente);

                for (int j = 0; j < componente.Tamano(); j++) {
                    Estacion e = grafo.buscarEstacion(componente.Agarrar(j));
                    sb.append("  - ").append(e.getnombre()).append(" (Línea: ").append(e.getlinea()).append(")\n");
                }
                numSubred++;
            }
        }

        sb.append("\nTotal de subredes aisladas: ").append(numSubred - 1).append("\n");
        return sb.toString();
    }

    private void dfsRec(Vertice u, Lista<String> visitados, Lista<String> componente) {
        String idU = u.getEstacion().getid();
        visitados.Insertar(idU);
        componente.Insertar(idU);

        Lista<Arista> ady = u.getAdyacentes();
        for (int i = 0; i < ady.Tamano(); i++) {
            String idV = ady.Agarrar(i).getid_destino().getid();
            if (!visitados.Contiene(idV)) {
                Vertice v = grafo.buscarVerticePorId(idV);
                if (v != null) {
                    dfsRec(v, visitados, componente);
                }
            }
        }
    }

    public String identificarHubsTransferencia() {
        Lista<Vertice> vertList = grafo.getVertices();
        int n = vertList.Tamano();
        if (n == 0) return "El grafo está vacío.";

        StringBuilder sb = new StringBuilder();
        sb.append("=== HUBS DE TRANSFERENCIA (CENTRALIDAD DE GRADO) ===\n");
        sb.append("Estaciones ordenadas por mayor número de conexiones directas:\n\n");

        ParIdValor[] grados = new ParIdValor[n];
        for (int i = 0; i < n; i++) {
            Vertice v = vertList.Agarrar(i);
            grados[i] = new ParIdValor(v.getEstacion().getid(), v.getAdyacentes().Tamano(), v.getEstacion().getnombre());
        }

        // Selection Sort Descendente
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
                    (i + 1), e.getnombre(), e.getlinea(), (int) grados[i].valor));
        }

        return sb.toString();
    }

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