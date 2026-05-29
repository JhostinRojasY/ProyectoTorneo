package com.utp.aed.proyectotorneo.estructuras;

public class ListaEnlazadaEquipos {
    private NodoEquipo cabeza;
    private int tamano;

    public ListaEnlazadaEquipos() {
        this.cabeza = null;
        this.tamano = 0;
    }

    // Insertar al final
    public void insertar(String nombre) {
        NodoEquipo nuevo = new NodoEquipo(nombre);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoEquipo actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamano++;
    }

    // Obtener elemento por índice (simulando ArrayList)
    public String obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            return null;
        }
        NodoEquipo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getNombre();
    }

    public int getTamano() {
        return tamano;
    }

    public void limpiar() {
        cabeza = null;
        tamano = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public boolean buscar(String nombre) {
        NodoEquipo actual = cabeza;
        while (actual != null) {
            if (actual.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // Algoritmo de Búsqueda Binaria (Convierte la lista a arreglo primero para poder hacerlo)
    public boolean buscarBinaria(String nombre) {
        if (tamano == 0) return false;
        
        // 1. Convertir a Arreglo
        String[] arreglo = new String[tamano];
        NodoEquipo actual = cabeza;
        int index = 0;
        while (actual != null) {
            arreglo[index++] = actual.getNombre();
            actual = actual.getSiguiente();
        }
        
        // 2. Ordenar el arreglo (Requisito para la búsqueda binaria)
        java.util.Arrays.sort(arreglo, String.CASE_INSENSITIVE_ORDER);
        
        // 3. Búsqueda Binaria Clásica
        int inicio = 0;
        int fin = tamano - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int comparacion = arreglo[medio].compareToIgnoreCase(nombre);
            
            if (comparacion == 0) {
                return true; // Encontrado
            }
            if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return false;
    }

    // Algoritmo de Ordenamiento: Método de la Burbuja adaptado a Listas Enlazadas
    public void ordenarBurbujaAlfabeticamente() {
        if (tamano > 1) {
            boolean huboCambio;
            do {
                NodoEquipo actual = cabeza;
                NodoEquipo previo = null;
                NodoEquipo siguiente = cabeza.getSiguiente();
                huboCambio = false;

                while (siguiente != null) {
                    if (actual.getNombre().compareToIgnoreCase(siguiente.getNombre()) > 0) {
                        huboCambio = true;
                        
                        // Intercambiar nodos (no solo los valores, para demostrar dominio de punteros)
                        if (previo != null) {
                            NodoEquipo sig = siguiente.getSiguiente();
                            previo.setSiguiente(siguiente);
                            siguiente.setSiguiente(actual);
                            actual.setSiguiente(sig);
                        } else {
                            NodoEquipo sig = siguiente.getSiguiente();
                            cabeza = siguiente;
                            siguiente.setSiguiente(actual);
                            actual.setSiguiente(sig);
                        }
                        
                        // Desplazar punteros adecuadamente tras el intercambio
                        previo = siguiente;
                        siguiente = actual.getSiguiente();
                    } else {
                        previo = actual;
                        actual = siguiente;
                        siguiente = siguiente.getSiguiente();
                    }
                }
            } while (huboCambio);
        }
    }

    // Algoritmo de Ordenamiento: Método de Selección adaptado a Listas Enlazadas
    public void ordenarSeleccionAlfabeticamente() {
        if (cabeza == null || cabeza.getSiguiente() == null) return;
        
        NodoEquipo actual = cabeza;
        while (actual != null) {
            NodoEquipo min = actual;
            NodoEquipo iter = actual.getSiguiente();
            
            while (iter != null) {
                if (iter.getNombre().compareToIgnoreCase(min.getNombre()) < 0) {
                    min = iter;
                }
                iter = iter.getSiguiente();
            }
            
            if (min != actual) {
                // Intercambio simple de valores (para mantenerlo comprensible en selección)
                String temp = actual.getNombre();
                actual.setNombre(min.getNombre());
                min.setNombre(temp);
            }
            actual = actual.getSiguiente();
        }
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
