/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.aed.proyectotorneo.model;


public class NodoPartido {
  
    public int id = -1; // ID de base de datos
    public String equipo1;
    public String equipo2;
    public String ganador;
    
    // Conexiones del árbol (Estructura de Datos)
    public NodoPartido partidoPrevio1; // Rama izquierda
    public NodoPartido partidoPrevio2; // Rama derecha
    
    // Constructor 1: Para la primera ronda (cuando ya sabemos quiénes juegan)
    public NodoPartido(String equipo1, String equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.ganador = "Pendiente";
        this.partidoPrevio1 = null;
        this.partidoPrevio2 = null;
    }
    
    // Constructor 2: Para rondas avanzadas (esperan a los ganadores de rondas previas)
    public NodoPartido(NodoPartido previo1, NodoPartido previo2) {
        this.equipo1 = "Esperando rival..."; 
        this.equipo2 = "Esperando rival...";
        this.ganador = "Pendiente";
        this.partidoPrevio1 = previo1;
        this.partidoPrevio2 = previo2;
    }

}