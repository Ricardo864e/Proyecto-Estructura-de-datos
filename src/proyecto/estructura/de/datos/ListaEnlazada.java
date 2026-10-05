/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 * Clase ListaEnlazada.
 * Nos sirve para guardar elementos dinámicamente sin tener que usar ArrayList.
 * Como se uso T podemos reutilizar esta misma lista para guardar Estaciones, Aristas o Strings.
 * @author ricardopereira
 */
public class ListaEnlazada<T> {
    // Apunta al primer nodo que tiene la lista.
    Nodo<T> cabeza;
    // Lleva la cuenta del tama&ntildeo de la lista.
    int tamano;
    
    /**
     * Constructor de la lista.
     * La inicia vacia.
     */
    
    public ListaEnlazada(){
        this.cabeza = null;
        this.tamano = 0;
    }
    
    /**
     * Ingresa o agrega un nuevo elemento al final de la lista.
     * 
     * @param dato El elemento que queremos guardar.
     */
    
    public void Insertar(T dato){
        Nodo nuevo = new Nodo(dato);
        if(cabeza == null){
            cabeza = nuevo;
            tamano ++;
        }
        else{
            Nodo aux = cabeza;
            while(aux.siguiente != null){
                aux = aux.siguiente;
            }
            aux.siguiente = nuevo;
            tamano ++;
        }
    }
    
    /**
     * Busca el valor a eliminar y si lo encuentra lo elimina.
     * 
     * @param dato Es el elemento a eliminar.
     * @return true si lo encontr&oacute y lo borr&oacute, false si no estaba en la lista.
     */
    
    public boolean Eliminar(T dato){
        if(cabeza == null){
            return false;
        }
        // Revisamos si es la cabeza, ya que si lo es se elimina de forma distinta
        // al resto.
        if(cabeza.dato.equals(dato)){
            cabeza = cabeza.siguiente;
            tamano --;
            return true;
        }
        else{
            Nodo prueba = cabeza;
            while(prueba.siguiente != null && !prueba.siguiente.dato.equals(dato)){
                prueba = prueba.siguiente;
            }
            
            if(prueba.siguiente == null){
                //No esta el dato en la lista.
                return false;
            }
            else{
                //Si esta el dato en la lista.
                prueba.siguiente = prueba.siguiente.siguiente;
                tamano --;
                return true;
            }
        }
    }
    
    /**
     * Devuelve el elemento a buscar.
     * 
     * @param posicion La posición del elemento que buscamos, que empieza en 0 no en 1.
     * @return El elemento en esa posición, o null si la posición esta fuera de rango.
     */
    
    public T Agarrar(int posicion){
        if(posicion < 0 || posicion >= tamano){
            return null;
        }
        else{
            Nodo<T> aux = cabeza;
            for(int h = 0; h < posicion; h ++){
                aux = aux.siguiente;
            }
            return aux.dato;
        }
    }
    
    /**
     * Revisa si un elemento est&aacute dentro de la lista.
     * 
     * @param dato El elemento que estamos buscando.
     * @return true si el elemento está en la lista, false si no se est&aacute.
     */
    
    public boolean Esta(T dato){
        if(cabeza == null){
            return false;
        }
        else{
            Nodo<T> aux = cabeza;
            while(aux != null){
                if(aux.dato.equals(dato)){
                    return true;
                }
                aux = aux.siguiente;
            }
            return false;
            
        }
    }
    
    /**
     * Devuelve el tama&ntildeo de la lista.
     * 
     * @return El tama&ntildeo de la lista, en si la cantidad de elemntos.
     */
    
    public int Tamano(){
        return tamano;
    }
    
    /**
     * Indica si la lista est&aacute vacia, o no.
     * 
     * @return true si no tiene ning&uacuten elemento, false si tiene elementos.
     */
    
    public boolean Vacia(){
        if(tamano == 0){
            return true;
        }
        else{
            return false;
        }
    }
}
