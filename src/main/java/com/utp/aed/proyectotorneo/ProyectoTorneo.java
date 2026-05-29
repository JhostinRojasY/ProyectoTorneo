/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.utp.aed.proyectotorneo;

/**
 *
 * @author MSI
 */
public class ProyectoTorneo {

    public static void main(String[] args) {
        com.formdev.flatlaf.FlatLightLaf.setup(); // Activamos el modo claro (Estilo Web)
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new com.utp.aed.proyectotorneo.view.LoginJFrame().setVisible(true);
            }
        });
    }
}
