package com.utp.aed.proyectotorneo.view;

import com.utp.aed.proyectotorneo.dao.UsuarioDAO;
import javax.swing.JOptionPane;

public class RegistroEquipoJFrame extends javax.swing.JFrame {

    private LoginJFrame loginFrame;

    public RegistroEquipoJFrame(LoginJFrame loginFrame) {
        this.loginFrame = loginFrame;
        initComponents();
        setSize(400, 320); 
        setLocationRelativeTo(null);
    }

    public RegistroEquipoJFrame() {
        initComponents();
        setSize(400, 320); 
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnRegistrar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Nuevo Equipo");
        setResizable(false);
        getContentPane().setLayout(null);

        lblTitle.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTitle.setText("Registrar Cuenta de Equipo");
        getContentPane().add(lblTitle);
        lblTitle.setBounds(80, 20, 250, 30);

        jLabel1.setText("Usuario:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 80, 80, 25);
        getContentPane().add(txtUsername);
        txtUsername.setBounds(150, 80, 150, 25);

        jLabel2.setText("Contraseña:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(50, 130, 80, 25);
        getContentPane().add(txtPassword);
        txtPassword.setBounds(150, 130, 150, 25);

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistrar);
        btnRegistrar.setBounds(120, 180, 140, 30);

        btnVolver.setText("Volver al Login");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        getContentPane().add(btnVolver);
        btnVolver.setBounds(120, 220, 140, 30);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        boolean exito = dao.registrarUsuarioEquipo(username, password);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Equipo registrado correctamente. Ya puede iniciar sesión.");
            volver();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar. El usuario puede que ya exista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        volver();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void volver() {
        this.dispose();
        if (loginFrame != null) {
            loginFrame.setVisible(true);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroEquipoJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
