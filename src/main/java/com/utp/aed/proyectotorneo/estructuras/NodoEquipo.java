package com.utp.aed.proyectotorneo.estructuras;

public class NodoEquipo {
    private String nombre;
    private NodoEquipo siguiente;

    public NodoEquipo(String nombre) {
        this.nombre = nombre;
        this.siguiente = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public NodoEquipo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoEquipo siguiente) {
        this.siguiente = siguiente;
    }
}
