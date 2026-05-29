/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.utp.aed.proyectotorneo.view;

/**
 *
 * @author MSI
 */
public class PanelConfiguracion extends javax.swing.JPanel {

    public PanelConfiguracion() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblPeligro = new javax.swing.JLabel();
        btnReiniciar = new javax.swing.JButton();

        setLayout(null);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setText("Configuración del Sistema");
        add(lblTitle);
        lblTitle.setBounds(30, 20, 500, 40);

        add(jSeparator1);
        jSeparator1.setBounds(30, 90, 500, 10);

        lblPeligro.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPeligro.setForeground(new java.awt.Color(204, 0, 0));
        lblPeligro.setText("Zona de Peligro");
        add(lblPeligro);
        lblPeligro.setBounds(30, 110, 200, 25);

        btnReiniciar.setText("⚠️ REINICIAR SISTEMA COMPLETO (Borrar DB)");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });
        add(btnReiniciar);
        btnReiniciar.setBounds(30, 150, 350, 40);
    }

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar todos los equipos y resultados actuales de la Base de Datos?\nEsta acción no se puede deshacer.",
                "Confirmar Reinicio",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);

        if (confirmacion == 0) {
            Inicio.listaEquipos.limpiar();
            com.utp.aed.proyectotorneo.dao.EquipoDAO dao = new com.utp.aed.proyectotorneo.dao.EquipoDAO();
            dao.eliminarTodos();
            new com.utp.aed.proyectotorneo.dao.LlaveDAO().eliminarArbol();
            Inicio.campeonActual = "";
            javax.swing.JOptionPane.showMessageDialog(this, "El torneo y la base de datos han sido reiniciados. Todo está en blanco.", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private javax.swing.JButton btnReiniciar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblPeligro;
    private javax.swing.JLabel lblTitle;
}
