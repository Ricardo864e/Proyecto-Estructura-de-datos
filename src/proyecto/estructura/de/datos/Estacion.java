/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 * <b> Clase Estacion. </b>
 * Representa una estaci&oacute;n o v&eacute;rtice de nuestra red de transporte.
 * 
 * Decidimos poner la lectura y escritura del CSV aqu&iacute; mismo para que la clase 
 * maneje sus propios datos directamente, para no complicar el GestorArchivo.
 * 
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class Estacion{
    String id;
    String nombre;
    String linea;
    String zona;
    int capacidad_pas_hr; 
    
    /**
     * Constructor de la Estacion.
     * 
     * @param id C&oacute;digo corto, por ejemplo PRO.
     * @param nombre Nombre completo de la estaci&oacute;n.
     * @param linea L&iacute;nea a la que pertenece.
     * @param zona Zona o ubicaci&oacute;n.
     * @param capacidad_pas_hr Pasajeros maximos.
     */
    
    public Estacion(String id, String nombre, String linea, String zona, int capacidad_pas_hr){
        this.id = id;
        this.nombre = nombre;
        this.linea = linea;
        this.zona = zona;
        this.capacidad_pas_hr = capacidad_pas_hr;
    }
    
    /**
     * Crea una Estacion al leer una l&iacute;nea del archivo CSV.
     * 
     * @param lineaCsv L&iacute;nea de texto que viene del archivo.
     * @return El objeto Estacion, o null si la l&iacute;nea est&aacute; incompleta, osea no tiene ni nombre ni ID.
     */
    public static Estacion fromCSV(String lineaCsv) {
        String[] c = lineaCsv.split(",");
        if (c.length < 2){
            return null;
        } // Requiere al menos ID y Nombre
        
        String id = c[0].trim();
        String nombre = c[1].trim();
        
        String linea = "Desconocida";// Pusimos desconocida, ya que si no colocan se pone esa linea.
        if (c.length > 2) {
            linea = c[2].trim();
        }
        
        String zona = "General";// Pusimos general, ya que si ni colocan se pone como zona desconocida.
        if (c.length > 3) {
            zona = c[3].trim();
        }
        
        int capacidad = 0;
        if (c.length > 4 && !c[4].trim().isEmpty()) {
            capacidad = Integer.parseInt(c[4].trim());
        }
        
        return new Estacion(id, nombre, linea, zona, capacidad);
    }
    
    public String getid() { 
        return id; 
    }
    
    public String getnombre() { 
        return nombre; 
    }
    
    public String getlinea() { 
        return linea; 
    }
    
    public String getzona() { 
        return zona; 
    }
    
    public int getcapacidad_pas_hr() { 
        return capacidad_pas_hr; 
    }
    
    /**
     * Convierte la estaci&oacute;n a texto para guardarla en el CSV.
     * Esto lo usamos para poder guardar los cambios.
     * 
     * @return String para el archivo.
     */
    public String aCSV() {
        return id + "," + nombre + "," + linea + "," + zona + "," + capacidad_pas_hr;
    }
}    
