/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.estructura.de.datos;

/**
 * Tabla Hash personalizada para almacenar y usar las estaciones.
 * En lugar de buscar una por una, usamos una clave (como lo es el codigo de
 * la estaci&oacute;n), para saber donde est&aacute; guardada, y encontrarlo mas rapido,
 * y a su vez evitando colisiones.
 * @author Francisco Olivo, Johan Veracierto, Ricardo Pereira.
 */
public class Hash_Estacioness {
    /**
     * Arreglo principal de listas enlazadas. 
     * Funciona como la estructura de casillas donde cada
     * casilla @code tabla[i] encadena los elementos que colisionan en el mismo 
     * &iacute;ndice hash.
     */
    private Lista<ElementoHash> [] tabla;
    /**
     * Capacidad inicial y fija del arreglo de casillas. 
     * Se pone en 101 para
     * poder distribuir de mejor forma los &iacute;ndices y minimizar colisiones.
     * Se eligio el 101 ya que, es un n&uacute;mero primo e investigando sobre 
     * tablas hash aprendimos que usar un
     * número primo al sacar el m&oacute;dulo (% 101) ayuda a que los &iacute;ndices queden
     * mejor repartidos, y tambien genera un buen margen de espacio ya que como la red base tiene
     * 44 estaciones y ssí evitamos muchas colisiones
     * y mantenemos las búsquedas rápidas en O(1).
     */

    private int capacidad = 101; 
    
    //Que hace {@code @SuppressWarnings("unchecked")}.
    //Java no deja crear arreglos gen&eacute;ricos directos ya que (da error al compilar).
    //Hacemos la conversi&oacute;n manual {@code (new Lista[capacidad])} y colocamos esta
    //anotación para decirle a Java que sabemos que la conversión es segura y no
    //nos muestre advertencias en la consola.

    @SuppressWarnings("unchecked")
    /**
     * Crea la tabla hash con una lista enlazada vac&iacute;a en cada
     * una de las casillas del arreglo.
     */
    public Hash_Estacioness() {
        tabla = new Lista[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new Lista<>();
        }
    }
    
    /**
     * Funci&oacute;n Hash que convierte un texto en un &iacute;ndice dentro del 
     * arreglo.
     * 
     * Utiliza el 31 para dispersar las letras del texto de forma uniforme, a su vez:
     * Evita choques con letras repetidas ya que si solo sumáramos el valor de cada letra,
     * c&iacute;digos con las mismas letras en distinto orden (como "CAR" y "ARC") dar&iacute;an
     * el mismo resultado. Al ir multiplicando por 31 en cada paso, importa la
     * posici&oacute;n de cada letra.
     * Tambien el 31 es un n&uacute;mero primo
     * impar que mezcla bien los valores sin perder informaci&oacute;n.
     * 
     * @param clave identificador de la estaci&oacute;n a procesar.
     * @return &iacute;ndice entre 0 y la capacidad menos 1.
     */


    private int funcionHash(String clave){
        int hash = 0;
        String cla = clave.toLowerCase();
        for (int i = 0; i < cla.length(); i++){
            hash = (31 * hash + cla.charAt(i)) % capacidad;
        }
        
        //Le aplicamos el valor absoluto al hash.
        if(hash < 0){
            hash = hash * -1; 
        }
        return hash;
    }
    
    /**
     * Inserta una nueva estaci&oacute;n en la tabla hash o actualiza su contenido si la clave ya existe.
     * 
     * @param clave Texto que se usar&aacute; como identificador para la b&uacute;squeda.
     * @param estacion Objeto Estacion con la informaci&oacute;n que se desea guardar.
     */
    public void insertar(String clave, Estacion estacion){
        int indice = funcionHash(clave);
        Lista<ElementoHash> casilla = tabla[indice];
        
        // Se recorre la lista de esa posici&oacute;n por si la clave ya estaba registrada
        for(int i = 0; i < casilla.Tamano(); i++){
            ElementoHash guardar = casilla.Agarrar(i);
            // Si la clave ya existe, se sobrescribe con la nueva informaci&oacute;n, en pocas palabras se actualiza.
            if (guardar.clave.equalsIgnoreCase(clave)){
                guardar.estacion = estacion;
                return;
            }
        }
        // Si no exist&eacute; en la lista, se agrega como un nuevo nodo al final
        casilla.Insertar(new ElementoHash(clave, estacion));
    }
    
    
    /**
     * Elimina una estaci&oacuten de la tabla hash a partir de su clave.
     * 
     * @param clave clave de la estaci&oacuten que se desea remover del sistema.
     */
    public void eliminar(String clave) {
        int indice = funcionHash(clave);
        Lista<ElementoHash> casilla = tabla[indice];
        // Se busca el elemento dentro de la lista.
        for (int i = 0; i < casilla.Tamano(); i++){
            ElementoHash guar = casilla.Agarrar(i);
            if (guar.clave.equalsIgnoreCase(clave)){
                // Se elimina de la lista el elemento encontrado
                casilla.Eliminar(guar);
                return;
            }
        }
    }
    
    /**
     * Busca la informaci&oacuten de una estaci&oacuten usando su 
     * clave.
     * 
     * @param clave clave de la estaci&oacuten que se quiere buscar.
     * @return El objeto Estacion encontrado en la tabla, o null si la clave no 
     * existe o es nula.
     */
    public Estacion buscar(String clave){
        if (clave == null) return null;
        int indice = funcionHash(clave);
        Lista<ElementoHash> casilla = tabla[indice];

        for (int i = 0; i < casilla.Tamano(); i++){
            ElementoHash encontrado = casilla.Agarrar(i);
            if (encontrado.clave.equalsIgnoreCase(clave)){
                return encontrado.estacion;
            }
        }
        return null;
    }
}

