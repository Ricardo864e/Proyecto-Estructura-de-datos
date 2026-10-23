/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 *
 * @author Francisco Olivo 
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.FontMetrics;
import java.awt.Dimension;

class VisualizadorGrafoPanel extends JPanel {
    private Grafo_De_Transporte grafo;

    public VisualizadorGrafoPanel(Grafo_De_Transporte grafo) {
        this.grafo = grafo;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Representación Gráfica Multimodal"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Lista<Vertice> verts = grafo.getVertices();
        int total = verts.Tamano();
        if (total == 0) {
            g2.drawString("No hay datos cargados en la red.", 20, 30);
            return;
        }

        int panelWidth = Math.max(getWidth(), 550);
        int[] posX = new int[total];
        int[] posY = new int[total];
        int[] tamanioNodo = new int[total];

       
        int colLinea2 = panelWidth / 2 - 170; 
        int colLinea4 = panelWidth / 2 - 70;  
        int colLinea1 = panelWidth / 2 + 40;  
        int colLinea3 = panelWidth / 2 + 130; 

        
        int yL1 = 40;
        int yL2 = 420; 
        int yL3 = 380; 
        int yL4 = 920; 

        int stepY = 58; 

       
        for (int i = 0; i < total; i++) {
            Vertice v = verts.Agarrar(i);
            String id = v.getEstacion().getid().toUpperCase();
            String linea = v.getEstacion().getlinea().toLowerCase();
            int grado = v.getAdyacentes().Tamano();

            
            int diametro = 38;
            if (grado >= 4 || id.contains("PZA_VZLA") || id.contains("CAP_L4")) {
                diametro = 58;
            } else if (grado == 3 || id.contains("CHA_CAO") || id.contains("PAR_C")) {
                diametro = 48;
            }
            tamanioNodo[i] = diametro;

            
            if (linea.contains("2")) {
                posX[i] = colLinea2;
                posY[i] = yL2;
                yL2 += stepY;
            } else if (linea.contains("3")) {
                posX[i] = colLinea3;
                posY[i] = yL3;
                yL3 += stepY;
            } else if (linea.contains("4")) {
                posX[i] = colLinea4;
                posY[i] = yL4;
                yL4 += stepY;
            } else { // 
                posX[i] = colLinea1;
                posY[i] = yL1;
                yL1 += stepY;
            }
        }

       
        int maxY = Math.max(Math.max(yL1, yL2), Math.max(yL3, yL4)) + 50;
        if (getPreferredSize().height != maxY) {
            setPreferredSize(new Dimension(panelWidth, maxY));
            revalidate();
        }

        
        g2.setStroke(new BasicStroke(1.8f));
        for (int i = 0; i < total; i++) {
            Vertice u = verts.Agarrar(i);
            Lista<Arista> ady = u.getAdyacentes();
            for (int j = 0; j < ady.Tamano(); j++) {
                Arista ar = ady.Agarrar(j);
                int idxDestino = obtenerIndiceVertice(ar.getid_destino().getid());

                if (idxDestino != -1 && idxDestino > i) {
                    int x1 = posX[i];
                    int y1 = posY[i];
                    int x2 = posX[idxDestino];
                    int y2 = posY[idxDestino];

                    
                    g2.setColor(new Color(110, 110, 110));
                    g2.drawLine(x1, y1, x2, y2);

                   
                    int midX = (x1 + x2) / 2;
                    int midY = (y1 + y2) / 2;
                    String pesoStr = String.valueOf((int) ar.gettiempo_min());

                    g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(pesoStr);

                  
                    g2.setColor(Color.WHITE);
                    g2.fillRect(midX - textWidth / 2 - 2, midY - 6, textWidth + 4, 12);
                    g2.setColor(Color.BLACK);
                    g2.drawString(pesoStr, midX - textWidth / 2, midY + 4);
                }
            }
        }

        
        Color colorCeleste = new Color(126, 200, 227); 
        Color colorBorde = new Color(40, 40, 40);

        for (int i = 0; i < total; i++) {
            Vertice v = verts.Agarrar(i);
            int d = tamanioNodo[i];
            int x = posX[i] - d / 2;
            int y = posY[i] - d / 2;

            
            g2.setColor(colorCeleste);
            g2.fillOval(x, y, d, d);

        
            g2.setColor(colorBorde);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawOval(x, y, d, d);

            
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            FontMetrics fm = g2.getFontMetrics();
            String idText = v.getEstacion().getid();
            int textX = posX[i] - fm.stringWidth(idText) / 2;
            int textY = posY[i] + fm.getAscent() / 2 - 2;
            g2.drawString(idText, textX, textY);
        }
    }

    private int obtenerIndiceVertice(String id) {
        Lista<Vertice> verts = grafo.getVertices();
        for (int i = 0; i < verts.Tamano(); i++) {
            if (verts.Agarrar(i).getEstacion().getid().equalsIgnoreCase(id)) return i;
        }
        return -1;
    }
}
