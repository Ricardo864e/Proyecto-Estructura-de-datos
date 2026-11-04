/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 * <b> Clase Cola. </b>
 * Los elementos salen exactamente en el mismo orden en el que entraron. Lo
 * usamos para el recorrido BFS o para procesar elementos por tuenos.
 * 
 * @param <T> El tipo de dato gen&eacute;rico que guardaremos en la cola.
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class Cola <T>{
    Nodo<T> primero; // Apunta al primer nodo que tiene la cola.
    Nodo<T> ultimo; // Apunta al &uacute;ltimo nodo que tiene la lista.
    int head; // Lleva la cuenta del tama&ntilde;o de la cola.
    
    /**
     * Constructor que inicia una cola totalmente vac&iacute;a.
     */
    public Cola(){
        this.primero = null;
        this.ultimo = null;
        this.head = 0;
    }
    
    /**
     * Agrega un nuevo elemento a la cola.
     * 
     * @param dato El elemento que queremos guardar.
     */
    public void EnColar(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        if(primero == null){
            primero = nuevo;
            ultimo = primero;
        }
        else{
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
        head += 1;
    }
    
    /**
     * Elimina y devuelve el primer elemento de la cola.
     * 
     * @return El elemento eliminado o null si la cola est&aacute; vac&iacute;a.
     */
    public T Desencolar(){
        if(primero == null){
            return null;
        }
        else{
            Nodo<T> aux = primero;
            primero = primero.siguiente;
            if(primero == null){
                ultimo = null;
            }
            head -= 1;
            return aux.dato;
        }
    }
    
    /**
     * Muestra el elemento del frente sin eliminarlo de la cola.
     * 
     * @return El elemento que est&aacute; de primero, o null si la cola est&aacute; vac&iacute;a.
     */
    public T ObtenerPrimero(){
        if(this.EsVacia()){
            return null;
        }
        else{
            return primero.dato;
        }
    }
    
    /**
     * Indica si la cola no tiene elementos.
     * 
     * @return true si la cola est&aacute; vac&iacute;a, false no lo est&aacute;.
     */
    public boolean EsVacia(){
        return head == 0;
    }
    
    /**
     * Devuelve el tama&ntilde;o de la cola.
     * 
     * @return El n&uacute;mero de elementos.
     */
    public int Tamano(){
        return head;
    }
}
