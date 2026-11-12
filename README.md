# Proyecto-Estructura-de-datos
# Urban Route - Optimización y Análisis de Redes de Transporte Multimodal
> **Nota:** Todo el código fuente del proyecto se encuentra alojado en la rama **`master`**.
Un sistema desarrollado en Java Swing para modelar, analizar y visualizar 
redes de transporte público multimodal mediante un grafo ponderado bidireccional 
y una Tabla Hash personalizada[cite: 3, 5]. Permite calcular rutas de menor tiempo (Dijkstra), 
detectar subredes aisladas tras contingencias (DFS) e identificar estaciones neurálgicas 
o hubs de transferencia (Centralidad de Grado), todo implementado completamente desde cero.

---

## Integrantes del Equipo

* **Francisco Olivo**
* **Johan Veracierto**
* **Ricardo Pereira**

## Repositorio de GitHub

* **URL:** `https://github.com/Ricardo864e/Proyecto-Estructura-de-datos/blob/master/src/proyecto/estructura/de/datos/Grafo_De_Transporte.java`
* **Rama del código (Todo el proyecto):** `master`.

---

## Estructuras de Datos Propias.

Todas las estructuras dinámicas fueron desarrolladas con nodos enlazados.

* **`Lista<T>` (Lista Enlazada Simple):** Estructura lineal genérica que gestiona el almacenamiento dinámico de elementos. Sirve como base para las listas de adyacencia del grafo, el encadenamiento en la Tabla Hash y la recolección de resultados.
* **`Cola<T>` (Cola Genérica FIFO):** Implementación de tipo *First In, First Out* mediante referencias `primero` y `ultimo`. Diseñada para procesar tareas y recorridos por niveles.
* **`Nodo<T>` (Nodo Genérico):** Unidad fundamental de almacenamiento dinámico con soporte genérico (`<T>`) para cualquier objeto (`Estacion`, `Arista`, `String`, etc.).
* **`Hash_Estacioness` & `ElementoHash` (Tabla Hash):** Estructura de 101 casillas con función hash polinomial (factor 31). Resuelve colisiones mediante encadenamiento separado (`Lista<ElementoHash>`) y ofrece búsquedas en O(1).
* **`ParIdValor` (DTO - Data Transfer Object):** Estructura auxiliar ligera para encapsular `(ID, valor_numérico, texto_auxiliar)`. Utilizada para gestionar distancias y predecesores en Dijkstra, así como grados de centralidad en la detección de Hubs.

---
## Algoritmos e Inteligencia de Negocio

1. **Algoritmo de Dijkstra (Ruta Óptima):** Encuentra el trayecto que minimiza el tiempo total de traslado (en minutos) entre dos estaciones seleccionadas.
2. **Búsqueda en Profundidad - DFS (Subredes Conexas):** Detecta sectores o líneas que permanecen aisladas tras la eliminación o el cierre temporal de estaciones o conexiones.
3. **Selection Sort (Identificación de Hubs):** Ordena las estaciones según su grado de conectividad y con mayor volumen de transferencias.

---

## Arquitectura de Clases (`src/proyecto/estructura/de/datos`)

src/
└── proyecto.estructura.de.datos/
├── AnalizadorRed.java             # Lógica analítica: Dijkstra, DFS y Selection Sort (Hubs)
├── Arista.java                    # Conexión ponderada entre estaciones (destino y tiempo en min)
├── Cola.java                      # Estructura genérica FIFO propia
├── ElementoHash.java              # Clave-Valor (String, Estacion) para la Tabla Hash
├── Estacion.java                  # Modelo de la estación y parsing CSV (fromCSV, aCSV)
├── Grafo_De_Transporte.java       # Estructura de Grafo ponderado bidireccional
├── Hash_Estacioness.java          # Tabla Hash personalizada (Chaining O(1))
├── Lista.java                     # Lista enlazada simple genérica (Base del proyecto)
├── Main_interfa.java              # Ventana principal y control de eventos Java Swing (GUI)
├── Manejo_Archivos.java           # Persistencia de datos en CSV y gestión JFileChooser
├── Nodo.java                      # Nodo genérico reutilizable para Lista y Cola
├── ParIdValor.java                # DTO auxiliar para retorno de tuplas en algoritmos
├── Vertice.java                   # Vértice del grafo que asocia una Estación con sus Aristas
└── Visualizador_grafo_panel.java  # Renderizador gráfico dinámico integrado con GraphStream

---

## Formato de Archivos CSV y Persistencia

El sistema opera mediante dos archivos `.csv` cargados por `JFileChooser` y actualizables:

### 1. Estaciones (`estaciones.csv`).
```csv
ID_Estacion,Nombre,Linea,Zona,Capacidad_Pasajeros_Hora
PRO,Propatria,Linea 1,Catia,12000
PER,Perez Bonalde,Linea 1,Catia,8000
```
### 2. Conexiones (`conexiones.csv`).
```csv
Estacion_De_Origen,Estacion_De_Destino,Tiempo_En_Minutos
PRO,PER,2.0
PER,PZA,2.0
```
## Visualización Gráfica
* Integración de librería GraphStream 2.0 en Java Swing (JPanel).
* Coloreado de nodos según la linea de asignada.
* Se actualiza en tiempo real luego de agregar o eliminar estaciones o conexiones.

## Requisitos de Ejecución
* Java Developmente Kit: Versión 8 o superior (Probado en JDK 26).
* IDE: NetBeans IDE 12+ (o cualquier IDE Java compatible).
* Librecias JAR requeridas, para el GraphStream 2.0:
* `gs-core-2.0.jar`
* `gs-ui-swing-2.0.jar`
* `AbsoluteLayout.jar`


