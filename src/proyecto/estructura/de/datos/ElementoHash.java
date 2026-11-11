/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 * Clase que representa la clave - valor dentro de la tabla hash.
 * Establece una clave al objeto Estacion correspondiente.
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class ElementoHash {

    String clave;
    Estacion estacion;
        
    /**
     * Constructor básico para el elemento guardando su clave y su estación.
     * 
     * @param clave Texto que identifica la estaci&oacute;n (por ejemplo: PRO - Propatia.).
     * @param estacion Objeto con los datos de la estación.
     */
        
    ElementoHash(String clave, Estacion estacion){
        this.clave = clave;
        this.estacion = estacion;
    }
}
