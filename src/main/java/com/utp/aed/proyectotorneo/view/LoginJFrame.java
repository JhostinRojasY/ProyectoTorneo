package com.utp.aed.proyectotorneo.view;

import com.utp.aed.proyectotorneo.dao.UsuarioDAO;
import com.utp.aed.proyectotorneo.model.Usuario;
import javax.swing.JOptionPane;

public class LoginJFrame extends javax.swing.JFrame {

    public LoginJFrame() {
        initComponents();
        setSize(450, 420); 
        setLocationRelativeTo(null);
        
        this.getRootPane().setDefaultButton(btnLogin);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Torneo - Login");
        setBackground(new java.awt.Color(170, 220, 224));
        setIconImages(null);
        setResizable(false);
        getContentPane().setLayout(null);

        lblTitle.setFont(new java.awt.Font("ItalicT", 1, 20)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Bienvenido al Torneo");
        getContentPane().add(lblTitle);
        lblTitle.setBounds(70, 20, 310, 30);

        jLabel1.setFont(new java.awt.Font("Trophy", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuario:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 80, 80, 25);

        txtUsername.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        getContentPane().add(txtUsername);
        txtUsername.setBounds(120, 120, 190, 30);

        jLabel2.setFont(new java.awt.Font("Trophy", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Contraseña:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(70, 180, 140, 40);

        txtPassword.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        getContentPane().add(txtPassword);
        txtPassword.setBounds(120, 230, 190, 30);

        btnLogin.setFont(new java.awt.Font("Eras Bold ITC", 0, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_iniciar.png"))); // NOI18N
        btnLogin.setText("Iniciar Sesión");
        btnLogin.setContentAreaFilled(false);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin);
        btnLogin.setBounds(40, 290, 170, 40);

        btnRegistro.setFont(new java.awt.Font("Eras Bold ITC", 0, 12)); // NOI18N
        btnRegistro.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_registrar.png"))); // NOI18N
        btnRegistro.setText("Registrar Equipo");
        btnRegistro.setContentAreaFilled(false);
        btnRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistro);
        btnRegistro.setBounds(240, 290, 170, 40);

        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("¿Olvide Contraseña?");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(160, 350, 110, 16);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo_seleccion.png"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(-10, -180, 440, 550);

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
            this.dispose();
            SeleccionDeporte menuDeportes = new SeleccionDeporte(user); // Asegúrate de que el nombre coincida con tu archivo
        menuDeportes.setVisible(true);
        
        // Cerramos la ventana de Login
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
