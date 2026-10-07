/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vertice;

/**
 *
 * @author Francisco olivo,johan Veracierto, Ricardo Pereira
 */
public class Vertice {
    private Estacion estacion;
    private ListaEnlazada<Arista> adyacentes;

public Vertice(Estacion) {
    this.estacion = estacion;
    this.adyacentes = new ListaEnlazada<>();
}

public void agregarArista(Estacion final, doublee tempo) {
    for (int i = 0; i < adyacentes.getTamano();i++){
        Arista uno =adyacentes.obtener(i);
        if (uno.getDestino().getId().equals(destino.getId())){
            adyacentes.eliminar(uno);
            break;
        }
    }
    adyacentes.agregar(new Arista(destino,tiempo));
}

public void eliminarAristaConDestino(String idDestino) {
    for (int i = 0; i < adyacentes.getTamano(); i++) {
        Arista uno = adyacentes.obtener(i);
        if (uno.getDestino().getId().equals(idDestino)){
            adyacentes.eliminar(uno);
            break;
        }
    }
}

public Estacion getEstacion(){ return estacion; }
public ListaEnlazada<Arista> getAdyacentes () {return adyacentes;}
}
