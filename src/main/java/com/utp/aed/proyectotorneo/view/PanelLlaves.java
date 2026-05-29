package com.utp.aed.proyectotorneo.view;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class PanelLlaves extends javax.swing.JPanel {

    private com.utp.aed.proyectotorneo.model.NodoPartido partidoFinal;
    private com.utp.aed.proyectotorneo.model.Usuario usuarioActual;

    public PanelLlaves(com.utp.aed.proyectotorneo.model.Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
        
        if (usuarioActual != null && "Equipo".equalsIgnoreCase(usuarioActual.getRol().getNombre())) {
            btnGenerarTorneo.setVisible(false);
            btnLimpiar.setVisible(false);
        }
        
        com.utp.aed.proyectotorneo.dao.LlaveDAO dao = new com.utp.aed.proyectotorneo.dao.LlaveDAO();
        com.utp.aed.proyectotorneo.model.NodoPartido raiz = dao.cargarArbol();
        if (raiz != null) {
            this.partidoFinal = raiz;
            llenarArbol(this.partidoFinal);
            btnGenerarTorneo.setEnabled(false);
        }
    }

    private void llenarArbol(com.utp.aed.proyectotorneo.model.NodoPartido raizLogica) {
        if (raizLogica == null) return;
        
        NodoTorneo raizSwing = crearNodoSwing(raizLogica);
        DefaultTreeModel modelo = new DefaultTreeModel(raizSwing);
        arbolTorneo.setModel(modelo);
        
        for (int i = 0; i < arbolTorneo.getRowCount(); i++) {
            arbolTorneo.expandRow(i);
        }
    }
    
    private NodoTorneo crearNodoSwing(com.utp.aed.proyectotorneo.model.NodoPartido nodo) {
        if (nodo == null) return null;
        
        String texto = (nodo.ganador.equals("Pendiente")) 
            ? "[" + nodo.equipo1 + " vs " + nodo.equipo2 + "]"
            : "Ganador: " + nodo.ganador;
            
        NodoTorneo actual = new NodoTorneo(texto, nodo);
        
        if (nodo.partidoPrevio1 != null) {
            actual.add(crearNodoSwing(nodo.partidoPrevio1));
        }
        if (nodo.partidoPrevio2 != null) {
            actual.add(crearNodoSwing(nodo.partidoPrevio2));
        }
        
        return actual;
    }

    private void actualizarPadre(com.utp.aed.proyectotorneo.model.NodoPartido raiz, com.utp.aed.proyectotorneo.model.NodoPartido hijo, String ganador, com.utp.aed.proyectotorneo.dao.LlaveDAO dao) {
        if(raiz == null) return;
        if(raiz.partidoPrevio1 == hijo) {
            raiz.equipo1 = ganador;
            dao.actualizarNodo(raiz);
            return;
        }
        if(raiz.partidoPrevio2 == hijo) {
            raiz.equipo2 = ganador;
            dao.actualizarNodo(raiz);
            return;
        }
        actualizarPadre(raiz.partidoPrevio1, hijo, ganador, dao);
        actualizarPadre(raiz.partidoPrevio2, hijo, ganador, dao);
        
        if (this.partidoFinal == hijo) {
             btnFinalizado.setVisible(true); 
             Inicio.campeonActual = ganador;
             javax.swing.JOptionPane.showMessageDialog(this, "¡EL CAMPEÓN ES: " + ganador + "!", "FIN DEL TORNEO", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGenerarTorneo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        arbolTorneo = new javax.swing.JTree();
        btnFinalizado = new javax.swing.JButton();
        txtBuscarEquipo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHistorial = new javax.swing.JTextArea();
        btnLimpiar = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 246, 248));

        btnGenerarTorneo.setBackground(new java.awt.Color(198, 40, 40));
        btnGenerarTorneo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGenerarTorneo.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarTorneo.setText("Generar Emparejamientos");
        btnGenerarTorneo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarTorneoActionPerformed(evt);
            }
        });

        arbolTorneo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        arbolTorneo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arbolTorneoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(arbolTorneo);

        btnFinalizado.setText("🏆 Torneo Finalizado");

        txtBuscarEquipo.setText("Buscar Equipo...");
        txtBuscarEquipo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarEquipoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarEquipoFocusLost(evt);
            }
        });
        txtBuscarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarEquipoActionPerformed(evt);
            }
        });

        txtHistorial.setEditable(false);
        txtHistorial.setColumns(20);
        txtHistorial.setLineWrap(true);
        txtHistorial.setRows(5);
        txtHistorial.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtHistorial);

        btnLimpiar.setBackground(new java.awt.Color(100, 100, 100));
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar Búsqueda");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGenerarTorneo)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFinalizado)
                    .addComponent(txtBuscarEquipo)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerarTorneo)
                    .addComponent(btnFinalizado))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBuscarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarTorneoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarTorneoActionPerformed
        java.util.ArrayList<com.utp.aed.proyectotorneo.model.NodoPartido> rondaActual = new java.util.ArrayList<>();
        
        for (int i = 0; i < Inicio.listaEquipos.getTamano(); i += 2) {
            String eq1 = Inicio.listaEquipos.obtener(i);
            String eq2 = (i + 1 < Inicio.listaEquipos.getTamano()) ? Inicio.listaEquipos.obtener(i + 1) : "Pase Directo";
            rondaActual.add(new com.utp.aed.proyectotorneo.model.NodoPartido(eq1, eq2));
        }
        while (rondaActual.size() > 1) {
            java.util.ArrayList<com.utp.aed.proyectotorneo.model.NodoPartido> siguienteRonda = new java.util.ArrayList<>();
            for (int i = 0; i < rondaActual.size(); i += 2) {
                com.utp.aed.proyectotorneo.model.NodoPartido p1 = rondaActual.get(i);
                com.utp.aed.proyectotorneo.model.NodoPartido p2 = (i + 1 < rondaActual.size()) ? rondaActual.get(i + 1) : new com.utp.aed.proyectotorneo.model.NodoPartido("Pase Directo", "Pase Directo");
                siguienteRonda.add(new com.utp.aed.proyectotorneo.model.NodoPartido(p1, p2));
            }
            rondaActual = siguienteRonda;
        }
        if (!rondaActual.isEmpty()) {
            partidoFinal = rondaActual.get(0);
            llenarArbol(partidoFinal);
            btnGenerarTorneo.setEnabled(false);
            
            new com.utp.aed.proyectotorneo.dao.LlaveDAO().guardarArbol(partidoFinal);
        }
    }//GEN-LAST:event_btnGenerarTorneoActionPerformed

    private void arbolTorneoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbolTorneoMouseClicked
        if (evt.getClickCount() == 2) {
            if (usuarioActual != null && "Equipo".equalsIgnoreCase(usuarioActual.getRol().getNombre())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Solo el Administrador puede editar los resultados.", "Acceso Denegado", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            NodoTorneo nodoSel = (NodoTorneo) arbolTorneo.getLastSelectedPathComponent();
            if (nodoSel == null) return;
            
            com.utp.aed.proyectotorneo.model.NodoPartido partido = nodoSel.partidoLogico;
            
            if (partido.ganador.equals("Pendiente") && !partido.equipo1.startsWith("Esperando") && !partido.equipo2.startsWith("Esperando")) {
                String[] opciones = {partido.equipo1, partido.equipo2};
                int eleccion = javax.swing.JOptionPane.showOptionDialog(this, "¿Quién ganó este partido?", "Resultado", javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                if (eleccion >= 0) {
                    String ganador = opciones[eleccion];
                    partido.ganador = ganador;
                    
                    com.utp.aed.proyectotorneo.dao.LlaveDAO dao = new com.utp.aed.proyectotorneo.dao.LlaveDAO();
                    dao.actualizarNodo(partido);
                    actualizarPadre(partidoFinal, partido, ganador, dao);
                    llenarArbol(partidoFinal);
                }
            }
        }
    }//GEN-LAST:event_arbolTorneoMouseClicked

    private void txtBuscarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEquipoActionPerformed
        String equipoBuscado = txtBuscarEquipo.getText().trim();

        if (equipoBuscado.isEmpty() || equipoBuscado.equals("Buscar Equipo...")) {
            txtHistorial.setText("⚠️ Por favor, ingresa el nombre\n de un equipo válido.");
            return;
        }

        if (!Inicio.listaEquipos.buscar(equipoBuscado)) {
            txtHistorial.setText("❌ Búsqueda Secuencial:\nEl equipo '" + equipoBuscado + "' no se \nencuentra en la Lista Enlazada.");
            return;
        }

        if (partidoFinal == null) {
            txtHistorial.setText("❌ El torneo aún no ha sido generado.");
            return;
        }

        StringBuilder progreso = new StringBuilder();
        buscarHistorial(partidoFinal, equipoBuscado, progreso);

        if (progreso.length() == 0) {
            txtHistorial.setText(" No se encontraron registros para:\n '" + equipoBuscado + "'");
        } else {
            txtHistorial.setText(" PROGRESO DEL \n" +
                                 " Equipo: " + equipoBuscado.toUpperCase() + "\n" +
                                 "--------------------------------------\n" + 
                                 progreso.toString());
        }
    }//GEN-LAST:event_txtBuscarEquipoActionPerformed

    private void txtBuscarEquipoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarEquipoFocusGained
        if (txtBuscarEquipo.getText().equals("Buscar Equipo...")) {
            txtBuscarEquipo.setText("");
            txtBuscarEquipo.setForeground(java.awt.Color.BLACK);
        }
    }//GEN-LAST:event_txtBuscarEquipoFocusGained

    private void txtBuscarEquipoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarEquipoFocusLost
        if (txtBuscarEquipo.getText().isEmpty()) {
            txtBuscarEquipo.setText("Buscar Equipo...");
            txtBuscarEquipo.setForeground(java.awt.Color.GRAY); 
        }
    }//GEN-LAST:event_txtBuscarEquipoFocusLost

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtHistorial.setText(" Escribe un equipo arriba para\n ver su seguimiento aquí...");
        txtBuscarEquipo.setText("Buscar Equipo...");
        txtBuscarEquipo.setForeground(java.awt.Color.GRAY);
        this.requestFocus();
    }//GEN-LAST:event_btnLimpiarActionPerformed
    
    private void buscarHistorial(com.utp.aed.proyectotorneo.model.NodoPartido nodo, String equipo, StringBuilder progreso) {
        if (nodo == null) return;

        buscarHistorial(nodo.partidoPrevio1, equipo, progreso);
        buscarHistorial(nodo.partidoPrevio2, equipo, progreso);

        if (nodo.equipo1.equalsIgnoreCase(equipo) || nodo.equipo2.equalsIgnoreCase(equipo)) {
            String rival = nodo.equipo1.equalsIgnoreCase(equipo) ? nodo.equipo2 : nodo.equipo1;
            
            if (nodo.ganador.equals("Pendiente")) {
                progreso.append("▶ Próximo partido: Pendiente vs ").append(rival).append("\n");
            } else if (nodo.ganador.equalsIgnoreCase(equipo)) {
                if (nodo == partidoFinal) {
                    progreso.append(" Victoria en la Final vs ").append(rival).append("");
                } else {
                    progreso.append(" Victoria vs ").append(rival).append("");
                }
            } else {
                progreso.append("✗ Derrota vs ").append(rival).append(" (Eliminado)");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arbolTorneo;
    private javax.swing.JButton btnFinalizado;
    private javax.swing.JButton btnGenerarTorneo;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtBuscarEquipo;
    private javax.swing.JTextArea txtHistorial;
    // End of variables declaration//GEN-END:variables

    class NodoTorneo extends javax.swing.tree.DefaultMutableTreeNode {
        public com.utp.aed.proyectotorneo.model.NodoPartido partidoLogico;
        
        public NodoTorneo(String texto, com.utp.aed.proyectotorneo.model.NodoPartido partido) {
            super(texto); 
            this.partidoLogico = partido; 
        }
    }
}
