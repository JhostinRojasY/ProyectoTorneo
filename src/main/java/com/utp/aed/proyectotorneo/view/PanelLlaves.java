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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 246, 248));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGenerarTorneo.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        btnGenerarTorneo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_generar.png"))); // NOI18N
        btnGenerarTorneo.setText("Generar Emparejamientos");
        btnGenerarTorneo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarTorneoActionPerformed(evt);
            }
        });
        add(btnGenerarTorneo, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 17, -1, -1));

        arbolTorneo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        arbolTorneo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arbolTorneoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(arbolTorneo);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 75, 289, 284));

        btnFinalizado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_campeon.png"))); // NOI18N
        btnFinalizado.setText("Torneo Finalizado");
        btnFinalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizadoActionPerformed(evt);
            }
        });
        add(btnFinalizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 18, -1, -1));

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
        add(txtBuscarEquipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 117, -1, -1));

        txtHistorial.setEditable(false);
        txtHistorial.setColumns(20);
        txtHistorial.setLineWrap(true);
        txtHistorial.setRows(5);
        txtHistorial.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtHistorial);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(408, 157, 188, 192));

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_limpiar.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 355, -1, -1));

        btnDeshacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_deshacer.png"))); // NOI18N
        btnDeshacer.setText("Deshacer");
        btnDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshacerActionPerformed(evt);
            }
        });
        add(btnDeshacer, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 377, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_buscar.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 107, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoLogin.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnFinalizadoActionPerformed(java.awt.event.ActionEvent evt) {
        // Lógica para cuando se hace clic en Torneo Finalizado
        if (partidoFinal != null && !partidoFinal.ganador.equals("Pendiente")) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "El gran campeón es: " + partidoFinal.ganador, 
                "Torneo Finalizado", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "El torneo aún no ha terminado.", 
                "Aviso", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void btnGenerarTorneoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarTorneoActionPerformed
    
       Object[] opciones = {"Fase de Grupos", "Eliminatorias Directas", "Cancelar"};
        int eleccionFormato = javax.swing.JOptionPane.showOptionDialog(
            this,
            "¿Qué formato deseas utilizar para el torneo?",
            "Formato de Emparejamientos",
            javax.swing.JOptionPane.DEFAULT_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            new javax.swing.ImageIcon(getClass().getResource("/img/icono_generar.png")), // Puedes usar null si no quieres icono
            opciones,
            opciones[0]
        );

        if (eleccionFormato == 2 || eleccionFormato == javax.swing.JOptionPane.CLOSED_OPTION) {
            return; 
        }

        ListaEnlazadaHistorial equiposParaLlaves = new ListaEnlazadaHistorial();

        if (eleccionFormato == 0) {
            equiposParaLlaves = obtenerClasificadosFaseGrupos();

            if (equiposParaLlaves.estaVacia()) {
                return; 
            }
            
        } else if (eleccionFormato == 1) {
            int totalEquipos = Inicio.listaEquipos.getTamano();
            
            if (totalEquipos < 2) {
                javax.swing.JOptionPane.showMessageDialog(this, "Se necesitan al menos 2 equipos para iniciar.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            java.util.ArrayList<String> todosLosEquipos = new java.util.ArrayList<>();
            for (int i = 0; i < totalEquipos; i++) {
                todosLosEquipos.add(Inicio.listaEquipos.obtener(i));
            }

            java.util.Collections.shuffle(todosLosEquipos);

            for (String eq : todosLosEquipos) {
                equiposParaLlaves.insertarFinal(eq);
            }
        }

        java.util.ArrayList<String> listaAleatoria = new java.util.ArrayList<>();
        NodoHistorial temp = equiposParaLlaves.cabeza;

        while (temp != null) {
            listaAleatoria.add(temp.mensaje);
            temp = temp.siguiente;
        }
        
        java.util.Collections.shuffle(listaAleatoria);

        ColaPartidos cola = new ColaPartidos();

        for (int i = 0; i < listaAleatoria.size(); i += 2) {
            String eq1 = listaAleatoria.get(i);
            String eq2 = "Pase Directo";

            if (i + 1 < listaAleatoria.size()) {
                eq2 = listaAleatoria.get(i + 1);
            }
            
            cola.encolar(new com.utp.aed.proyectotorneo.model.NodoPartido(eq1, eq2));
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
            llenarArbol(partidoFinal);
            btnGenerarTorneo.setEnabled(false);
            new com.utp.aed.proyectotorneo.dao.LlaveDAO().guardarArbol(partidoFinal);
            
            javax.swing.JOptionPane.showMessageDialog(this, "¡Sorteo finalizado! \nLas llaves se han generado.");
        }
    }


    private ListaEnlazadaHistorial obtenerClasificadosFaseGrupos() {
     ListaEnlazadaHistorial clasificados = new ListaEnlazadaHistorial();
        int totalEquipos = Inicio.listaEquipos.getTamano();

        if (totalEquipos < 2) {
            javax.swing.JOptionPane.showMessageDialog(this, "Se necesitan al menos 2 equipos para iniciar.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return clasificados;
        }

        java.util.ArrayList<String> equiposSorteados = new java.util.ArrayList<>();
        for (int i = 0; i < totalEquipos; i++) {
            equiposSorteados.add(Inicio.listaEquipos.obtener(i));
        }
        java.util.Collections.shuffle(equiposSorteados);

        int tamanoGrupo = 4; 
        int numGrupos = (int) Math.ceil((double) totalEquipos / tamanoGrupo);
        
        String[][] grupos = new String[numGrupos][tamanoGrupo];
        int cont = 0;
    
        for (int i = 0; i < numGrupos; i++) {
            for (int j = 0; j < tamanoGrupo; j++) {
                if (cont < totalEquipos) {
                    grupos[i][j] = equiposSorteados.get(cont);
                    cont++;
                } else {
                    grupos[i][j] = "Vacío";
                }
            }
        }

        for (int i = 0; i < numGrupos; i++) {
            int equiposReales = 0;
            java.util.ArrayList<String> equiposGrupo = new java.util.ArrayList<>();
            
            for (int j = 0; j < tamanoGrupo; j++) {
                if (!grupos[i][j].equals("Vacío")) {
                    equiposReales++;
                    equiposGrupo.add(grupos[i][j]);
                }
            }

            char letraGrupo = (char) ('A' + i);

            if (equiposReales == 1) {
                javax.swing.JOptionPane.showMessageDialog(this, equiposGrupo.get(0) + " (Sin rivales - Pasa directo)", "Grupo " + letraGrupo, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                clasificados.insertarFinal(equiposGrupo.get(0));
                continue;
            }

            java.util.ArrayList<String[]> emparejamientos = new java.util.ArrayList<>();
            for (int m = 0; m < equiposGrupo.size(); m++) {
                for (int n = m + 1; n < equiposGrupo.size(); n++) {
                    emparejamientos.add(new String[]{equiposGrupo.get(m), equiposGrupo.get(n)});
                }
            }

            String[] ganadoresPartido = new String[emparejamientos.size()];

            // NUEVO: Pila para guardar el orden de los partidos y poder deshacer
            java.util.Stack<Integer> pilaDeshacerGrupo = new java.util.Stack<>();

            javax.swing.JDialog dialogoGrupo = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), "Partidos - Grupo " + letraGrupo, true);
            dialogoGrupo.setLayout(new java.awt.BorderLayout());
            dialogoGrupo.setSize(450, 350);
            dialogoGrupo.setLocationRelativeTo(this);

            // Ahora permitimos cerrar con la 'X' para que funcione como "Cancelar Torneo"
            dialogoGrupo.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE); 

            javax.swing.JPanel panelCentro = new javax.swing.JPanel(new java.awt.GridLayout(emparejamientos.size(), 1, 5, 5));
            panelCentro.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

            javax.swing.JButton[] botonesPartidos = new javax.swing.JButton[emparejamientos.size()];
            for (int p = 0; p < emparejamientos.size(); p++) {
                final int index = p;
                String eq1 = emparejamientos.get(p)[0];
                String eq2 = emparejamientos.get(p)[1];

                botonesPartidos[index] = new javax.swing.JButton("Partido " + (index + 1) + ": " + eq1 + " vs " + eq2 + " (Pendiente)");
                botonesPartidos[index].setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
                
                botonesPartidos[index].addActionListener(e -> {
                    String[] opciones = {eq1, eq2};
                    int eleccion = javax.swing.JOptionPane.showOptionDialog(
                        dialogoGrupo, 
                        "¿Quién ganó el Partido " + (index + 1) + "?\n\n" + eq1 + "  VS  " + eq2, 
                        "Definir Ganador", 
                        javax.swing.JOptionPane.DEFAULT_OPTION, 
                        javax.swing.JOptionPane.QUESTION_MESSAGE, 
                        null, opciones, opciones[0]
                    );

                    if (eleccion >= 0) {
                        pilaDeshacerGrupo.push(index); // Guardar la acción en la pila
                        ganadoresPartido[index] = opciones[eleccion];
                        botonesPartidos[index].setText("Partido " + (index + 1) + ": " + eq1 + " vs " + eq2 + "  🏆 Ganó: " + opciones[eleccion]);
                        botonesPartidos[index].setBackground(new java.awt.Color(200, 255, 200)); 
                    }
                });
                panelCentro.add(botonesPartidos[index]);
            }

            javax.swing.JScrollPane scrollPanel = new javax.swing.JScrollPane(panelCentro);
            dialogoGrupo.add(scrollPanel, java.awt.BorderLayout.CENTER);

            // --- INICIO DE LA MODIFICACIÓN DE LOS BOTONES INFERIORES ---
            javax.swing.JPanel panelSur = new javax.swing.JPanel();
            javax.swing.JButton btnFinalizarGrupo = new javax.swing.JButton("Finalizar Grupo");
            javax.swing.JButton btnDeshacer = new javax.swing.JButton("Deshacer");
            
            btnFinalizarGrupo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            btnDeshacer.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

            // Bandera para saber si se presionó Finalizar correctamente
            boolean[] finalizadoCorrectamente = {false};

            btnFinalizarGrupo.addActionListener(e -> {
                boolean todosJugados = true;
                for (String g : ganadoresPartido) {
                    if (g == null) {
                        todosJugados = false;
                        break;
                    }
                }

                if (!todosJugados) {
                    javax.swing.JOptionPane.showMessageDialog(dialogoGrupo, "Aún hay partidos pendientes en este grupo. Completa todos los resultados.", "Partidos Incompletos", javax.swing.JOptionPane.WARNING_MESSAGE);
                } else {
                    finalizadoCorrectamente[0] = true; // Todo validado
                    dialogoGrupo.dispose();
                }
            });

            btnDeshacer.addActionListener(e -> {
                if (!pilaDeshacerGrupo.isEmpty()) {
                    int ultimoIndex = pilaDeshacerGrupo.pop(); // Sacar el último partido jugado
                    ganadoresPartido[ultimoIndex] = null; // Borrar el ganador
                    String eq1 = emparejamientos.get(ultimoIndex)[0];
                    String eq2 = emparejamientos.get(ultimoIndex)[1];
                    botonesPartidos[ultimoIndex].setText("Partido " + (ultimoIndex + 1) + ": " + eq1 + " vs " + eq2 + " (Pendiente)");
                    botonesPartidos[ultimoIndex].setBackground(null); // Restaurar el color original
                } else {
                    javax.swing.JOptionPane.showMessageDialog(dialogoGrupo, "No hay resultados registrados para deshacer.", "Aviso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            });

            panelSur.add(btnFinalizarGrupo);
            panelSur.add(btnDeshacer);
            dialogoGrupo.add(panelSur, java.awt.BorderLayout.SOUTH);

            dialogoGrupo.setVisible(true);

            // Si la ventana se cerró (con la 'X') sin finalizar correctamente, abortamos el torneo devolviendo lista vacía
            if (!finalizadoCorrectamente[0]) {
                return new ListaEnlazadaHistorial();
            }
            // --- FIN DE LA MODIFICACIÓN ---

            int[] puntos = new int[equiposGrupo.size()];
            
            for (String ganador : ganadoresPartido) {
                int indexGanador = equiposGrupo.indexOf(ganador);
                if (indexGanador != -1) {
                    puntos[indexGanador]++;
                }
            }

            java.util.ArrayList<Integer> indices = new java.util.ArrayList<>();
            for (int k = 0; k < equiposGrupo.size(); k++) {
                indices.add(k);
            }

            indices.sort((a, b) -> Integer.compare(puntos[b], puntos[a])); 

            String primero = equiposGrupo.get(indices.get(0));
            clasificados.insertarFinal(primero);

            String segundo = equiposGrupo.get(indices.get(1));
            clasificados.insertarFinal(segundo); 
 
            StringBuilder resumen = new StringBuilder();
            resumen.append("🏆 CLASIFICADOS GRUPO ").append(letraGrupo).append(" 🏆\n\n");
            resumen.append("1er Puesto: ").append(primero).append(" (").append(puntos[indices.get(0)]).append(" victorias)\n");
            resumen.append("2do Puesto: ").append(segundo).append(" (").append(puntos[indices.get(1)]).append(" victorias)\n");
            
            javax.swing.JOptionPane.showMessageDialog(this, resumen.toString(), "Resumen Grupo " + letraGrupo, javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

        return clasificados;
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
      if (partidoFinal == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay ningún torneo generado para deshacer.", "Aviso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // --- 1. MOSTRAR OPCIONES DE DESHACER ---
        Object[] opciones = {"Deshacer Último Partido", "Reiniciar Torneo Completo", "Cancelar"};
        int eleccion = javax.swing.JOptionPane.showOptionDialog(
            this,
            "¿Qué acción deseas realizar?",
            "Opciones de Deshacer",
            javax.swing.JOptionPane.DEFAULT_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            new javax.swing.ImageIcon(getClass().getResource("/img/icono_deshacer.png")), // Usa null si prefieres sin icono
            opciones,
            opciones[0]
        );

        // --- 2. LÓGICA SEGÚN LA ELECCIÓN ---
        if (eleccion == 0) {
            // Opción: Deshacer solo el último partido del árbol de llaves
            if (!historialAcciones.estaVacia()) {
                com.utp.aed.proyectotorneo.model.NodoPartido ultimoPartido = historialAcciones.pop();
                ultimoPartido.ganador = "Pendiente";

                // Actualizar el DAO para que el cambio persista
                com.utp.aed.proyectotorneo.dao.LlaveDAO dao = new com.utp.aed.proyectotorneo.dao.LlaveDAO();
                dao.actualizarNodo(ultimoPartido);

                // Refrescar el árbol visual
                javax.swing.tree.DefaultMutableTreeNode raizVisual = crearNodoVisual(partidoFinal);
                arbolTorneo.setModel(new javax.swing.tree.DefaultTreeModel(raizVisual));

                for (int i = 0; i < arbolTorneo.getRowCount(); i++) {
                    arbolTorneo.expandRow(i);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No hay resultados de partidos en las llaves para deshacer.", "Pila Vacía", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
            
        } else if (eleccion == 1) {
            // Opción: Reiniciar todo para volver a la fase de grupos o selección inicial
            int confirmar = javax.swing.JOptionPane.showConfirmDialog(
                this, 
                "¿Estás seguro de que deseas eliminar todo el progreso del torneo actual?\nEsto te permitirá volver a generar los grupos o eliminatorias desde cero.", 
                "Confirmar Reinicio", 
                javax.swing.JOptionPane.YES_NO_OPTION, 
                javax.swing.JOptionPane.WARNING_MESSAGE
            );

            if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
                // 1. Limpiar la variable raíz del árbol lógico
                partidoFinal = null;
                
                // 2. Limpiar el modelo visual del JTree
                arbolTorneo.setModel(null);
                
                // 3. Vaciar la pila de deshacer completamente
                while (!historialAcciones.estaVacia()) {
                    historialAcciones.pop();
                }
                
                // 4. Volver a habilitar el botón de generación y ocultar finalizado
                btnGenerarTorneo.setEnabled(true);
                btnFinalizado.setVisible(false);
                
                // Opcional: Si deseas limpiar el registro guardado en el archivo/BD
                // new com.utp.aed.proyectotorneo.dao.LlaveDAO().borrarArbol(); 
                
                // Limpiar área de historial de búsqueda
                txtHistorial.setText(" El torneo ha sido reiniciado.");
                
                javax.swing.JOptionPane.showMessageDialog(this, "El torneo ha sido reiniciado con éxito. Puedes volver a generar los emparejamientos.");
            }
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
