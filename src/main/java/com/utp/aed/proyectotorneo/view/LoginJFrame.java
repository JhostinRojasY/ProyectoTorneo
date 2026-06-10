package com.utp.aed.proyectotorneo.view;

import com.utp.aed.proyectotorneo.dao.UsuarioDAO;
import com.utp.aed.proyectotorneo.model.Usuario;
import javax.swing.JOptionPane;

public class LoginJFrame extends javax.swing.JFrame {

    public LoginJFrame() {
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
        btnLogin = new javax.swing.JButton();
        btnRegistro = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Torneo - Login");
        setBackground(new java.awt.Color(170, 220, 224));
        setIconImages(null);
        setResizable(false);
        getContentPane().setLayout(null);

        lblTitle.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lblTitle.setText("Bienvenido al Torneo");
        getContentPane().add(lblTitle);
        lblTitle.setBounds(100, 20, 240, 30);

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

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_iniciar.png"))); // NOI18N
        btnLogin.setText("Iniciar Sesión");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin);
        btnLogin.setBounds(120, 180, 160, 40);

        btnRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_registrar.png"))); // NOI18N
        btnRegistro.setText("Registrar Equipo");
        btnRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistro);
        btnRegistro.setBounds(120, 230, 160, 40);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoLogin.png"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, 0, 400, 300);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + user.getUsername() + " (" + user.getRol().getNombre() + ")");
            Inicio inicio = new Inicio(user);
            inicio.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed
        RegistroEquipoJFrame registro = new RegistroEquipoJFrame(this);
        registro.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegistroActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegistro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
