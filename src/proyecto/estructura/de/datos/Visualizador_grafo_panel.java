/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Panel que forma la gr&aacute;fica del grafo de la red de transporte.
 * Organiza las estaciones, muestra las conexiones y dibuja las rutas con sus tiempos.
 * 
 * Hacemos un "extends JPanel" porque necesitamos un componente de la interfaz gr&aacute;fica 
 * de Java Swing que funcione como una pizarra en blanco. As&iacute; podemos usar 
 * las herramientas.
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */

public class Visualizador_grafo_panel extends JPanel {

    private Grafo_De_Transporte grafoPersonalizado;
    
    /**
     * Constructor del panel visual.
     * Recibe el grafo que construimos a mano en el proyecto y hace que ocupe todo el panel.
     * 
     * @param grafo El grafo con las listas de adyacencia de nuestro proyecto.
     */

    public Visualizador_grafo_panel(Grafo_De_Transporte grafo){
        this.grafoPersonalizado = grafo;
        
        // Usamos BorderLayout para que el mapa de GraphStream ocupe todo el espacio del panel
        this.setLayout(new BorderLayout());
        // Llamamos al método que arma todo
        inicializarGraphStream();
    }

    /**
     * N&uacute;cleo visual de la clase. 
     * Se encarga de configurar el GraphStream, darle estilo CSS 
     * para que tenga los mismos colores y forma del boceto en el enunciado del proyevto,
     * y traducir nuestros Vértices y Aristas 
     * a los Nodos que entiende la librería.
     */
    private void inicializarGraphStream() {

        // Esto le dice a la librería que vamos a usar Java Swing.
        System.setProperty("org.graphstream.ui", "swing");

        // Creamos el grafo de la librería.
        Graph gsGraph = new SingleGraph("Red de Transporte Urbano");
        
        // Podemos usar CSS para que se vea bonito (tamaño de texto, color de fondo de las letras, etc.)
        gsGraph.setAttribute("ui.stylesheet", "node { text-size: 14px; text-background-mode: plain; text-background-color: white; } edge { text-size: 12px; text-color: blue; }");

        Lista vertices = grafoPersonalizado.getVertices();
        if (vertices == null || vertices.Tamano() == 0) {
            return; // Si no hay datos, no dibujamos nada
        }

        // Se crean los nodos GraphStream.
        for (int i = 0; i < vertices.Tamano(); i++) {
            Vertice v = (Vertice) vertices.Agarrar(i);
            Estacion est = v.getEstacion();
            String idEstacion = est.getid();
            
            // Agregamos el nodo.
            Node nodoo = gsGraph.addNode(idEstacion);
            
            // "ui.label" es el texto que aparece al lado del circulito
            nodoo.setAttribute("ui.label", idEstacion);
            
            // Coloreamos el nodo usando CSS integrado, dependiendo de la línea
            String linea = est.getlinea().toLowerCase();
            if (linea.contains("1")) {
                nodoo.setAttribute("ui.style", "fill-color: red;");
            } else if (linea.contains("2")) {
                nodoo.setAttribute("ui.style", "fill-color: green;");
            } else if (linea.contains("3")) {
                nodoo.setAttribute("ui.style", "fill-color: blue;");
            } else if (linea.contains("4")) {
                nodoo.setAttribute("ui.style", "fill-color: yellow;");
            } else {
                nodoo.setAttribute("ui.style", "fill-color: orange;");
            }
        }

        // Creamos las conexiones en GraphStream.
        for (int i = 0; i < vertices.Tamano(); i++) {
            Vertice v = (Vertice) vertices.Agarrar(i);
            String origen = v.getEstacion().getid();
            
            Lista adyacentes = v.getAdyacentes();
            for (int j = 0; j < adyacentes.Tamano(); j++) {
                Arista arista = (Arista) adyacentes.Agarrar(j);
                String destino = arista.getid_destino().id;
                
                // Creamos un ID único para la arista
                String idArista = origen + "-" + destino;
                String idAristaInversa = destino + "-" + origen;
                
                // Evitamos duplicados, ya que es no dirigido.
                if (gsGraph.getEdge(idArista) == null && gsGraph.getEdge(idAristaInversa) == null) {
                    // Hacemos la arista en si (edge).
                    // addEdge(id, origen, destino, dirigido (false para que no tenga flecha)) 
                    Edge edge = gsGraph.addEdge(idArista, origen, destino, false);
                    
                    // Le ponemos el tiempo de traslado como etiqueta en el medio de la línea
                    edge.setAttribute("ui.label", String.valueOf(arista.gettiempo_min()) + " min");
                }
            }
        }

        // Se muestra en grafico.
        // Creamos el visor de Swing
        SwingViewer viewer = new SwingViewer(gsGraph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        
        // Magia pura: esto activa la "gravedad" y repulsión para que los nodos 
        // no queden todos amontonados en la esquina, sino que se separen orgánicamente.
        viewer.enableAutoLayout();
        
        // Obtenemos el panel visual de GraphStream. 
        // Le pasamos 'false' para que NO cree una ventana separada, sino que nos dé el componente.
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);
        
        // Para que se puedan mover los nodos.
        viewPanel.setMouseManager(new org.graphstream.ui.swing_viewer.util.DefaultMouseManager());
        
        
        // Agregamos ese componente de GraphStream dentro de nuestro propio JPanel
        this.add(viewPanel, BorderLayout.CENTER);
    }
}