/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/** <b> Clase ListaEnlazada. </b>
 * Sirve para guardar elementos dinámicamente sin tener que usar <i> ArrayList </i>.
 * Como se uso T podemos reutilizar esta misma lista para guardar (Strings, Enteros, Estaciones, Aristas).
 * @author FO, JV, RP
 * @param <T>
 */
public class Lista<T> {
    Nodo<T> primero; // Apunta al primer nodo que tiene la lista.
    int head; // Lleva la cuenta del tama&ntildeo de la lista.
    
    /** Constructor de la lista.
     * La lista inicia vacia.
     */
    public Lista(){
        this.primero = null;
        this.head = 0;
    }
    
    /** Ingresa o agrega un nuevo elemento al final de la lista.
     * @param dato El elemento que queremos guardar.
     */
    public void Insertar(T dato){
        Nodo nuevo = new Nodo(dato);
        if(primero == null){
            primero = nuevo;
            head += 1;
        }
        else{
            Nodo aux = primero;
            while(aux.siguiente != null){
                aux = aux.siguiente;
            }
            aux.siguiente = nuevo;
            head += 1;
        }
    }
    
    /** Busca el valor a eliminar y si lo encuentra lo elimina.
     * @param dato Es el elemento a eliminar.
     * @return true si lo encontr&oacute y lo borr&oacute, false si no estaba en la lista.
     * 
     */
    public boolean Eliminar(T dato){
        if(primero == null){
            return false;
        }
        else if(primero.dato.equals(dato)){ // Revisamos si es la cabeza, ya que si lo es se elimina de forma distinta al resto.
            primero = primero.siguiente;
            head -= 1;
            return true;
        }
        else{
            Nodo prueba = primero;
            while(prueba.siguiente != null && !prueba.siguiente.dato.equals(dato)){
                prueba = prueba.siguiente;
            }
            if(prueba.siguiente == null){
                return false; //No esta el dato en la lista.
            }
            else{
                prueba.siguiente = prueba.siguiente.siguiente; //Si esta el dato en la lista.
                head -= 1;
                return true;
            }
        }
    }
    
    /** Devuelve el elemento a buscar.
     * @param posicion La posición del elemento que buscamos, que empieza en 0 no en 1.
     * @return El elemento en esa posición, o null si la posición esta fuera de rango.
     */
    public T Agarrar(int posicion){
        if(posicion < 0 || posicion >= head){
            return null;
        }
        else{
            Nodo<T> aux = primero;
            for(int i = 0; i < posicion; i += 1){
                aux = aux.siguiente;
            }
            return aux.dato;
        }
    }
    
    /** Revisa si un elemento est&aacute dentro de la lista.
     * @param dato El elemento que estamos buscando.
     * @return true si el elemento está en la lista, false si no se est&aacute.
     */
    public boolean Contiene(T dato){
        if(primero == null){
            return false;
        }
        else{
            Nodo<T> aux = primero;
            while(aux != null){
                if(aux.dato.equals(dato)){
                    return true;
                }
                aux = aux.siguiente;
            }
            return false;
            
        }
    }
    
    /** Devuelve el tama&ntildeo de la lista.
     * @return El tama&ntildeo de la lista, en si la cantidad de elemntos.
     */
    public int Tamano(){
        return head;
    }
    
    /** Indica si la lista est&aacute vacia, o no.
     * 
     * @return true si no tiene ning&uacuten elemento, false si tiene elementos.
     */
    public boolean EsVacia(){
        return head == 0;
    }
}
