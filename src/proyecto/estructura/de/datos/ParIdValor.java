/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;
/**

 * 
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class ParIdValor {
    String id; // Identificador unico de la estacion.
    double valor; // Valor numerico asociado (distancia acumulada o grado de conexion).
    String texto; // Informacion adicional (ID del predecesor o nombre de la estacion).

    /** Constructor de ParIdValor.
     * @param id Clave primaria de la estacion.
     * @param valor Distancia o conteo numerico.
     * @param texto Texto descriptivo auxiliar.
     */
    public ParIdValor(String id, double valor, String texto) {
        this.id = id;
        this.valor = valor;
        this.texto = texto;
    }
}