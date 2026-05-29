package com.utp.aed.proyectotorneo.view;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class PanelLlaves extends javax.swing.JPanel {

    private com.utp.aed.proyectotorneo.model.NodoPartido partidoFinal;

    
    private PilaDeshacer historialAcciones = new PilaDeshacer();
    
    private javax.swing.tree.DefaultMutableTreeNode crearNodoVisual(com.utp.aed.proyectotorneo.model.NodoPartido partido) {
        if (partido == null) return null;

       String titulo = partido.ganador.equals("Pendiente") ? 
                        "<html><body><span style='color:#000000; font-weight:normal;'>" + partido.equipo1 + " VS " + partido.equipo2 + "</span></body></html>" : 
                        "<html><body><span style='color:#FFC107; font-weight:bold;'>🏆 " + partido.ganador + "</span></body></html>";

        NodoTorneo nodoVisual = new NodoTorneo(titulo, partido);

        if (partido.partidoPrevio1 != null) nodoVisual.add(crearNodoVisual(partido.partidoPrevio1));
        if (partido.partidoPrevio2 != null) nodoVisual.add(crearNodoVisual(partido.partidoPrevio2));

        return nodoVisual;
    }

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
        btnDeshacer = new javax.swing.JButton();

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

        btnDeshacer.setText("Deshacer");
        btnDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshacerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGenerarTorneo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFinalizado)
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(btnDeshacer)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtBuscarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnLimpiar)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinalizado)
                    .addComponent(btnGenerarTorneo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(txtBuscarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeshacer)))
                .addContainerGap(70, Short.MAX_VALUE))
        );    
}// </editor-fold>//GEN-END:initComponents

    private void btnGenerarTorneoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarTorneoActionPerformed

        ColaPartidos cola = new ColaPartidos();
  
        for (int i = 0; i < Inicio.listaEquipos.getTamano(); i += 2) {
            String eq1 = Inicio.listaEquipos.obtener(i);
            String eq2 = (i + 1 < Inicio.listaEquipos.getTamano()) ? Inicio.listaEquipos.obtener(i + 1) : "Pase Directo";
            cola.encolar(new com.utp.aed.proyectotorneo.model.NodoPartido(eq1, eq2));
        } 
        
        java.util.ArrayList<com.utp.aed.proyectotorneo.model.NodoPartido> rondaActual = new java.util.ArrayList<>();
        
        for (int i = 0; i < Inicio.listaEquipos.getTamano(); i += 2) {
            String eq1 = Inicio.listaEquipos.obtener(i);
            String eq2 = (i + 1 < Inicio.listaEquipos.getTamano()) ? Inicio.listaEquipos.obtener(i + 1) : "Pase Directo";
            rondaActual.add(new com.utp.aed.proyectotorneo.model.NodoPartido(eq1, eq2));

        }

        while (cola.obtenerTamaño() > 1) {
            com.utp.aed.proyectotorneo.model.NodoPartido p1 = cola.desencolar();
            com.utp.aed.proyectotorneo.model.NodoPartido p2 = cola.desencolar();

            if (p2 == null) {
                p2 = new com.utp.aed.proyectotorneo.model.NodoPartido("Pase Directo", "Pase Directo");
            }

            cola.encolar(new com.utp.aed.proyectotorneo.model.NodoPartido(p1, p2));
        }


        if (!cola.estaVacia()) {
            partidoFinal = cola.desencolar();
            
            javax.swing.tree.DefaultMutableTreeNode raizVisual = crearNodoVisual(partidoFinal);
            arbolTorneo.setModel(new javax.swing.tree.DefaultTreeModel(raizVisual));
            
            for (int i = 0; i < arbolTorneo.getRowCount(); i++) {
                arbolTorneo.expandRow(i);
            }
            btnGenerarTorneo.setEnabled(false);


        if (!rondaActual.isEmpty()) {
            partidoFinal = rondaActual.get(0);
            llenarArbol(partidoFinal);
            btnGenerarTorneo.setEnabled(false);
            

            new com.utp.aed.proyectotorneo.dao.LlaveDAO().guardarArbol(partidoFinal);
        }
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
                    
                    historialAcciones.push(partido);
                    
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
            txtHistorial.setText(" Por favor, ingresa el nombre\n de un equipo válido.");
            return;
        }

        if (!Inicio.listaEquipos.buscar(equipoBuscado)) {
            txtHistorial.setText(" Búsqueda Secuencial:\nEl equipo '" + equipoBuscado + "' no se \nencuentra en la Lista Enlazada.");
            return;
        }

        if (partidoFinal == null) {
            txtHistorial.setText(" El torneo aún no ha sido generado.");
            return;
        }


    ListaEnlazadaHistorial progreso = new ListaEnlazadaHistorial();
    buscarHistorial(partidoFinal, equipoBuscado, progreso);

    if (progreso.estaVacia()) {
    txtHistorial.setText(" No se encontraron registros para:\n '" + equipoBuscado + "'");
} else {
    txtHistorial.setText(" PROGRESO DEL \n" +
                         " Equipo: " + equipoBuscado.toUpperCase() + "\n" +
                         "--------------------------------------\n" + 
                         progreso.obtenerTextoCompleto());
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

    private void btnDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshacerActionPerformed
        // TODO add your handling code here:
        if (!historialAcciones.estaVacia()) {

        com.utp.aed.proyectotorneo.model.NodoPartido ultimoPartido = historialAcciones.pop();

        ultimoPartido.ganador = "Pendiente";

        javax.swing.tree.DefaultMutableTreeNode raizVisual = crearNodoVisual(partidoFinal);
        arbolTorneo.setModel(new javax.swing.tree.DefaultTreeModel(raizVisual));

        for (int i = 0; i < arbolTorneo.getRowCount(); i++) {
            arbolTorneo.expandRow(i);
        }
        
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "No hay más acciones para deshacer.");
    }
    }//GEN-LAST:event_btnDeshacerActionPerformed
    
    private void buscarHistorial(com.utp.aed.proyectotorneo.model.NodoPartido nodo, String equipo, ListaEnlazadaHistorial progreso) {
      if (nodo == null) return;

        buscarHistorial(nodo.partidoPrevio1, equipo, progreso);
        buscarHistorial(nodo.partidoPrevio2, equipo, progreso);


   if (nodo.equipo1.equalsIgnoreCase(equipo) || nodo.equipo2.equalsIgnoreCase(equipo)) {
    String rival = nodo.equipo1.equalsIgnoreCase(equipo) ? nodo.equipo2 : nodo.equipo1;
        
        if (nodo.ganador.equals("Pendiente")) {
            progreso.insertarFinal(" Próximo partido: Pendiente vs " + rival);
        } else if (nodo.ganador.equalsIgnoreCase(equipo)) {
            if (nodo == partidoFinal) {
                progreso.insertarFinal(" Victoria en la Final vs " + rival);
            } else {
                progreso.insertarFinal(" Victoria vs " + rival);
            }
        } else {
            progreso.insertarFinal(" Derrota vs " + rival + " (Eliminado)");
        }
    }
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arbolTorneo;
    private javax.swing.JButton btnDeshacer;
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

    class NodoHistorial {
        String mensaje;
        NodoHistorial siguiente;

        public NodoHistorial(String mensaje) {
            this.mensaje = mensaje;
            this.siguiente = null;
        }
    }

    class ListaEnlazadaHistorial {
        NodoHistorial cabeza;

        public ListaEnlazadaHistorial() {
            this.cabeza = null;
        }

        public void insertarFinal(String mensaje) {
            NodoHistorial nuevo = new NodoHistorial(mensaje);
            if (cabeza == null) {
                cabeza = nuevo;
            } else {
                NodoHistorial actual = cabeza;
                while (actual.siguiente != null) {
                    actual = actual.siguiente;
                }
                actual.siguiente = nuevo;
            }
        }
        
        public boolean eliminarPorTexto(String textoBuscado) {
            if (cabeza == null) {
                return false; 
            }

            if (cabeza.mensaje.toLowerCase().contains(textoBuscado.toLowerCase())) {
                cabeza = cabeza.siguiente; 
                return true;
            }

            NodoHistorial actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.siguiente.mensaje.toLowerCase().contains(textoBuscado.toLowerCase())) {
                    actual.siguiente = actual.siguiente.siguiente; 
                    return true;
                }
                actual = actual.siguiente; 
            }

            return false; 
        }
        
        public boolean estaVacia() {
            return cabeza == null;
        }

        public String obtenerTextoCompleto() {
            StringBuilder sb = new StringBuilder();
            NodoHistorial actual = cabeza;
            while (actual != null) {
                sb.append(actual.mensaje).append("\n");
                actual = actual.siguiente;
            }
            return sb.toString();
        }
    }

    class NodoColaPartido {
        com.utp.aed.proyectotorneo.model.NodoPartido partido;
        NodoColaPartido siguiente;

        public NodoColaPartido(com.utp.aed.proyectotorneo.model.NodoPartido partido) {
            this.partido = partido;
            this.siguiente = null;
        }
    }

    class ColaPartidos {
        NodoColaPartido frente;
        NodoColaPartido fin;
        int tamaño; 
        public ColaPartidos() {
            this.frente = null;
            this.fin = null;
            this.tamaño = 0;
        }

        public void encolar(com.utp.aed.proyectotorneo.model.NodoPartido partido) {
            NodoColaPartido nuevo = new NodoColaPartido(partido);
            if (frente == null) {
                frente = nuevo;
                fin = nuevo;
            } else {
                fin.siguiente = nuevo;
                fin = nuevo;
            }
            tamaño++;
        }

        public com.utp.aed.proyectotorneo.model.NodoPartido desencolar() {
            if (frente == null) return null;
            
            com.utp.aed.proyectotorneo.model.NodoPartido extraido = frente.partido;
            frente = frente.siguiente;
            
            if (frente == null) fin = null;
            
            tamaño--;
            return extraido;
        }

        public boolean estaVacia() {
            return frente == null;
        }
        
        public int obtenerTamaño() {
            return tamaño;
        }
    }

    class NodoPilaPartido {
        com.utp.aed.proyectotorneo.model.NodoPartido partido;
        NodoPilaPartido siguiente;

        public NodoPilaPartido(com.utp.aed.proyectotorneo.model.NodoPartido partido) {
            this.partido = partido;
            this.siguiente = null;
        }
    }

    class PilaDeshacer {
        NodoPilaPartido cima;

        public boolean estaVacia() {
            return cima == null;
        }

        public void push(com.utp.aed.proyectotorneo.model.NodoPartido partido) {
            NodoPilaPartido nuevo = new NodoPilaPartido(partido);
            nuevo.siguiente = cima;
            cima = nuevo;
        }

        public com.utp.aed.proyectotorneo.model.NodoPartido pop() {
            if (estaVacia()) return null;
            
            com.utp.aed.proyectotorneo.model.NodoPartido extraido = cima.partido;
            cima = cima.siguiente;
            return extraido;

        }
    }
}
