/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Es la encargada de guardar y leer los datos desde archivos en formato CSV.
 * Su trabajo es servir como una especie de puente entre el el archivo y el programa,
 * contruyendo el grafo a partir de archivos y
 * guardar en el mismo cualquier cambio realizado en la red.
 * 
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class Manejo_Archivos {
    
    /**
     * Lee y construye la red de transporte al leer dos archivos csv.
     * El procedimiento borra el grafo anterior si es que hab&iacute;a, luego procesa
     * las estaciones y despues a&ntilde;ade las conexiones asegurando que los datos
     * tengan un valor de respaldo por si pasa algo.
     * 
     * @param archivoEstaciones Archivo CSV que contiene la lista de estaciones y sus atributos.
     * @param archivoConexiones Archivo CSV con los caminos, destinos y tiempos de viaje en minutos.
     * @param grafo Grafo de transporte que se vaciar&aacute; y poblar&aacute; con la nueva información.
     * @throws FileNotFoundException Si el archivo obligatorio de estaciones no se encuentra en el disco.
     * @throws IOException Si ocurre algún fallo de lectura durante el procesamiento del archivo.
     */
    
    public static void cargarDatosCompletos(File archivoEstaciones, File archivoConexiones, Grafo_De_Transporte grafo) throws IOException{
        //Vaciamos el grafo.
        grafo.vaciar();

        // 1. Cargar Estaciones.
        if(archivoEstaciones != null && archivoEstaciones.exists()){
            try(BufferedReader archi = new BufferedReader(new FileReader(archivoEstaciones))){
                String linea;
                boolean primera = true; // Para no agarrar el titulo que viene en el .csv
                while((linea = archi.readLine()) != null) {
                    if(primera){ 
                        primera = false;
                        continue;
                    }
                    if(!linea.trim().isEmpty()) {
                        Estacion metro = Estacion.fromCSV(linea);
                        if(metro != null){
                            grafo.agregarEstacion(metro);
                        }
                    }
                }
            }
        } 
        else{
            throw new FileNotFoundException("No se encontró el archivo de estaciones: " + archivoEstaciones.getName());
        }

        // 2. Cargar Conexiones.
        if(archivoConexiones != null && archivoConexiones.exists()){
            try (BufferedReader archi = new BufferedReader(new FileReader(archivoConexiones))){
                String linea;
                boolean primera = true;
                while((linea = archi.readLine()) != null){
                    if(primera){
                        primera = false;
                        continue;
                    }
                    if(!linea.trim().isEmpty()){
                        String[] d = linea.split(",");
                        if (d.length >= 3) {
                            String orig = d[0].trim();
                            String dest = d[1].trim();
                            double tiempo = 0.0; //Por si no hay tiempo es 0.0
                            try{
                                tiempo = Double.parseDouble(d[2].trim());
                            }
                            catch (NumberFormatException ex) {
                                tiempo = 1.0; // Valor por defecto si el peso no es numérico
                            }
                            // Asumimos conexión bidireccional en redes de transporte
                            grafo.conectarEstaciones(orig, dest, tiempo, true);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Guarda las actualizaciones del grafo, de vuelta a dos archivos CSV.
     * Escribe el titulo y convierte cada vértice y arista a texto.
     * Para el archivo de conexiones, utiliza una lista temporal de control para no escribir
     * duplicados innecesarios, garantizando que nada se escriba dos veces.
     * 
     * @param archivoEstaciones Archivo de destino para escribir las estaciones.
     * @param archivoConexiones Archivo de destino para guardar las rutas y tiempos.
     * @param grafo Grafo que contiene la red actual a guardar.
     * @throws IOException Si ocurre algún error durante la escritura en disco.
     */
    
    public static void guardarDatosCSV(File archivoEstaciones, File archivoConexiones, GrafoRedTransporte grafo) throws IOException{
        // Guardar Estaciones
        try(BufferedWriter archi = new BufferedWriter(new FileWriter(archivoEstaciones))){
            archi.write("ID_Estacion,Nombre,Linea,Zona,Capacidad_Pasajeros_Hora");
            archi.newLine();
            Lista<Vertice> vertice = grafo.getVertices();
            for(int i = 0; i < vertice.Tamano(); i++){
                archi.write(vertice.Agarrar(i).getEstacion().aCSV());
                archi.newLine();
            }
        }

        // Guardar Conexiones
        if(archivoConexiones != null){
            try(BufferedWriter archi = new BufferedWriter(new FileWriter(archivoConexiones))){
                archi.write("Estacion_De_Origen,Estacion_De_Destino,Tiempo_En_Minutos");
                archi.newLine();
                Lista<Vertice> vertice = grafo.getVertices();
                Lista<String> pasadas = new Lista<>();

                for(int i = 0; i < vertice.Tamano(); i++){
                    Vertice v = vertice.Agarrar(i);
                    String placa = v.getEstacion().getid();
                    Lista<Arista> adyacentes = v.getAdyacentes(); // Control de claves pasadas para evitar repetidos

                    for(int j = 0; j < adyacentes.Tamano(); j++){
                        Arista arista = adyacentes.Agarrar(j);
                        String idV = arista.getid_destino().getid();

                        // Evitar duplicados en grafo no dirigido (bidireccional)
                        String clave1 = placa + "->" + idV;
                        String clave2 = idV + "->" + placa;
                        if(!pasadas.Contiene(clave1) && !pasadas.Contiene(clave2)){
                            archi.write(String.format("%s,%s,%.2f", placa, idV, arista.gettiempo_min()));
                            archi.newLine();
                            pasadas.Insertar(clave1);
                        }
                    }
                }
            }
        }
    }
}
