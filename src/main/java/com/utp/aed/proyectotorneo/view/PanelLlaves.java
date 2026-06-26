package com.utp.aed.proyectotorneo.view;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class PanelLlaves extends javax.swing.JPanel {

    private com.utp.aed.proyectotorneo.model.NodoPartido partidoFinal;

    private PilaDeshacer historialAcciones = new PilaDeshacer();

    private javax.swing.tree.DefaultMutableTreeNode crearNodoVisual(com.utp.aed.proyectotorneo.model.NodoPartido partido) {
        if (partido == null) {
            return null;
        }

        String titulo = partido.ganador.equals("Pendiente")
                ? "<html><body><span style='color:#000000; font-weight:normal;'>" + partido.equipo1 + " VS " + partido.equipo2 + "</span></body></html>"
                : "<html><body><span style='color:#FFC107; font-weight:bold;'>🏆 " + partido.ganador + "</span></body></html>";

        NodoTorneo nodoVisual = new NodoTorneo(titulo, partido);

        if (partido.partidoPrevio1 != null) {
            nodoVisual.add(crearNodoVisual(partido.partidoPrevio1));
        }
        if (partido.partidoPrevio2 != null) {
            nodoVisual.add(crearNodoVisual(partido.partidoPrevio2));
        }

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
        if (raizLogica == null) {
            return;
        }

        NodoTorneo raizSwing = crearNodoSwing(raizLogica);
        DefaultTreeModel modelo = new DefaultTreeModel(raizSwing);
        arbolTorneo.setModel(modelo);

        for (int i = 0; i < arbolTorneo.getRowCount(); i++) {
            arbolTorneo.expandRow(i);
        }
    }

    private NodoTorneo crearNodoSwing(com.utp.aed.proyectotorneo.model.NodoPartido nodo) {
        if (nodo == null) {
            return null;
        }

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
        if (raiz == null) {
            return;
        }
        if (raiz.partidoPrevio1 == hijo) {
            raiz.equipo1 = ganador;
            dao.actualizarNodo(raiz);
            return;
        }
        if (raiz.partidoPrevio2 == hijo) {
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

        jLabel3 = new javax.swing.JLabel();
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

        jLabel3.setText("jLabel3");

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

        arbolTorneo.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        arbolTorneo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        arbolTorneo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arbolTorneoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(arbolTorneo);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 75, 430, 390));

        btnFinalizado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnFinalizado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_campeon.png"))); // NOI18N
        btnFinalizado.setText("Torneo Finalizado");
        btnFinalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizadoActionPerformed(evt);
            }
        });
        add(btnFinalizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 190, -1));

        txtBuscarEquipo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        add(txtBuscarEquipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 150, -1));

        txtHistorial.setEditable(false);
        txtHistorial.setColumns(20);
        txtHistorial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHistorial.setLineWrap(true);
        txtHistorial.setRows(5);
        txtHistorial.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtHistorial);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 230, 230));

        btnLimpiar.setFont(new java.awt.Font("Rossanova Personal Use Light", 1, 14)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_limpiar.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 410, 120, -1));

        btnDeshacer.setFont(new java.awt.Font("Rossanova Personal Use Light", 1, 14)); // NOI18N
        btnDeshacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_deshacer.png"))); // NOI18N
        btnDeshacer.setText("Deshacer");
        btnDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshacerActionPerformed(evt);
            }
        });
        add(btnDeshacer, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono_buscar.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 70, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoLogin.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnFinalizadoActionPerformed(java.awt.event.ActionEvent evt) {

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
        java.util.ArrayList<java.util.ArrayList<String>> listaGrupos = new java.util.ArrayList<>();

        int cont = 0;
        for (int i = 0; i < numGrupos; i++) {
            java.util.ArrayList<String> grupo = new java.util.ArrayList<>();
            for (int j = 0; j < tamanoGrupo; j++) {
                if (cont < totalEquipos) {
                    grupo.add(equiposSorteados.get(cont));
                    cont++;
                }
            }
            listaGrupos.add(grupo);
        }
        boolean[] grupoFinalizado = new boolean[numGrupos];
        String[][] ganadoresPorGrupo = new String[numGrupos][];

        javax.swing.JDialog dialogoPrincipal = new javax.swing.JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), "Selección de Fase de Grupos", true);
        dialogoPrincipal.setLayout(new java.awt.BorderLayout());
        dialogoPrincipal.setSize(400, 350);
        dialogoPrincipal.setLocationRelativeTo(this);
        dialogoPrincipal.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);

        javax.swing.JPanel panelBotonesGrupos = new javax.swing.JPanel(new java.awt.GridLayout(numGrupos, 1, 10, 10));
        panelBotonesGrupos.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

        javax.swing.JButton[] botonesGrupos = new javax.swing.JButton[numGrupos];

        for (int i = 0; i < numGrupos; i++) {
            final int indexGrupo = i;
            char letra = (char) ('A' + i);
            java.util.ArrayList<String> equiposGrupo = listaGrupos.get(i);

            if (equiposGrupo.size() == 1) {
                grupoFinalizado[i] = true;
                botonesGrupos[i] = new javax.swing.JButton("Grupo " + letra + " - Pasa Directo (" + equiposGrupo.get(0) + ")");
                botonesGrupos[i].setBackground(new java.awt.Color(200, 255, 200));
                botonesGrupos[i].setEnabled(false);
                panelBotonesGrupos.add(botonesGrupos[i]);
                continue;
            }

            botonesGrupos[i] = new javax.swing.JButton("Jugar Grupo " + letra + " (Pendiente)");
            botonesGrupos[i].setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

            java.util.ArrayList<String[]> emparejamientos = new java.util.ArrayList<>();
            for (int m = 0; m < equiposGrupo.size(); m++) {
                for (int n = m + 1; n < equiposGrupo.size(); n++) {
                    emparejamientos.add(new String[]{equiposGrupo.get(m), equiposGrupo.get(n)});
                }
            }
            ganadoresPorGrupo[i] = new String[emparejamientos.size()];

            botonesGrupos[i].addActionListener(e -> {
                abrirVentanaGrupo(letra, emparejamientos, ganadoresPorGrupo[indexGrupo], dialogoPrincipal, botonesGrupos[indexGrupo], grupoFinalizado, indexGrupo);
            });

            panelBotonesGrupos.add(botonesGrupos[i]);
        }

        javax.swing.JScrollPane scrollPrincipal = new javax.swing.JScrollPane(panelBotonesGrupos);
        dialogoPrincipal.add(scrollPrincipal, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel panelSurPrincipal = new javax.swing.JPanel();
        javax.swing.JButton btnContinuarLlaves = new javax.swing.JButton("Generar Llaves de Eliminatorias");
        btnContinuarLlaves.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

        boolean[] torneoAprobado = {false};

        btnContinuarLlaves.addActionListener(e -> {
            boolean todosListos = true;
            for (boolean fin : grupoFinalizado) {
                if (!fin) {
                    todosListos = false;
                    break;
                }
            }

            if (!todosListos) {
                javax.swing.JOptionPane.showMessageDialog(dialogoPrincipal, "Aún hay grupos pendientes. Debes jugar y guardar todos los grupos antes de generar las llaves.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            } else {
                torneoAprobado[0] = true;
                dialogoPrincipal.dispose();
            }
        });

        panelSurPrincipal.add(btnContinuarLlaves);
        dialogoPrincipal.add(panelSurPrincipal, java.awt.BorderLayout.SOUTH);

        dialogoPrincipal.setVisible(true);

        if (!torneoAprobado[0]) {
            return new ListaEnlazadaHistorial();
        }

        StringBuilder resumenGlobal = new StringBuilder();
        resumenGlobal.append("🏆 CLASIFICADOS A LA FASE ELIMINATORIA 🏆\n\n");

        for (int i = 0; i < numGrupos; i++) {
            java.util.ArrayList<String> equiposGrupo = listaGrupos.get(i);
            char letra = (char) ('A' + i);

            if (equiposGrupo.size() == 1) {
                clasificados.insertarFinal(equiposGrupo.get(0));
                resumenGlobal.append("Grupo ").append(letra).append(": ").append(equiposGrupo.get(0)).append(" (Pase directo)\n");
                continue;
            }

            int[] puntos = new int[equiposGrupo.size()];
            for (String ganador : ganadoresPorGrupo[i]) {
                if (ganador != null) {
                    int indexGanador = equiposGrupo.indexOf(ganador);
                    if (indexGanador != -1) {
                        puntos[indexGanador]++;
                    }
                }
            }

            java.util.ArrayList<Integer> indices = new java.util.ArrayList<>();
            for (int k = 0; k < equiposGrupo.size(); k++) {
                indices.add(k);
            }
            indices.sort((a, b) -> Integer.compare(puntos[b], puntos[a])); // Ordenar de mayor a menor

            String primero = equiposGrupo.get(indices.get(0));
            clasificados.insertarFinal(primero);

            String segundo = equiposGrupo.get(indices.get(1));
            clasificados.insertarFinal(segundo);

            resumenGlobal.append("Grupo ").append(letra).append(": 1° ").append(primero).append(" | 2° ").append(segundo).append("\n");
        }

        javax.swing.JOptionPane.showMessageDialog(this, resumenGlobal.toString(), "Resumen Fase de Grupos", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        return clasificados;
    }

    private void abrirVentanaGrupo(char letraGrupo, java.util.ArrayList<String[]> emparejamientos, String[] ganadoresPartido, javax.swing.JDialog parent, javax.swing.JButton botonGrupo, boolean[] grupoFinalizado, int indexGrupo) {

        java.util.Stack<Integer> pilaDeshacerGrupo = new java.util.Stack<>();

        javax.swing.JDialog dialogoGrupo = new javax.swing.JDialog(parent, "Partidos - Grupo " + letraGrupo, true);
        dialogoGrupo.setLayout(new java.awt.BorderLayout());
        dialogoGrupo.setSize(450, 350);
        dialogoGrupo.setLocationRelativeTo(parent);
        dialogoGrupo.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);

        javax.swing.JPanel panelCentro = new javax.swing.JPanel(new java.awt.GridLayout(emparejamientos.size(), 1, 5, 5));
        panelCentro.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        javax.swing.JButton[] botonesPartidos = new javax.swing.JButton[emparejamientos.size()];
        for (int p = 0; p < emparejamientos.size(); p++) {
            final int index = p;
            String eq1 = emparejamientos.get(p)[0];
            String eq2 = emparejamientos.get(p)[1];

            botonesPartidos[index] = new javax.swing.JButton();
            botonesPartidos[index].setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));

            if (ganadoresPartido[index] != null) {
                botonesPartidos[index].setText("Partido " + (index + 1) + ": " + eq1 + " vs " + eq2 + "  🏆 Ganó: " + ganadoresPartido[index]);
                botonesPartidos[index].setBackground(new java.awt.Color(200, 255, 200));
                pilaDeshacerGrupo.push(index);
            } else {
                botonesPartidos[index].setText("Partido " + (index + 1) + ": " + eq1 + " vs " + eq2 + " (Pendiente)");
            }

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
                    pilaDeshacerGrupo.push(index);
                    ganadoresPartido[index] = opciones[eleccion];
                    botonesPartidos[index].setText("Partido " + (index + 1) + ": " + eq1 + " vs " + eq2 + "  🏆 Ganó: " + opciones[eleccion]);
                    botonesPartidos[index].setBackground(new java.awt.Color(200, 255, 200));
                }
            });
            panelCentro.add(botonesPartidos[index]);
        }

        javax.swing.JScrollPane scrollPanel = new javax.swing.JScrollPane(panelCentro);
        dialogoGrupo.add(scrollPanel, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel panelSur = new javax.swing.JPanel();
        javax.swing.JButton btnFinalizarGrupo = new javax.swing.JButton("Guardar Grupo");
        javax.swing.JButton btnDeshacer = new javax.swing.JButton("Deshacer");

        btnFinalizarGrupo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        btnDeshacer.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

        btnFinalizarGrupo.addActionListener(e -> {
            boolean todosJugados = true;
            for (String g : ganadoresPartido) {
                if (g == null) {
                    todosJugados = false;
                    break;
                }
            }

            if (!todosJugados) {
                javax.swing.JOptionPane.showMessageDialog(dialogoGrupo, "Aún hay partidos pendientes. Debes completarlos para guardar el grupo.", "Incompleto", javax.swing.JOptionPane.WARNING_MESSAGE);
            } else {

                grupoFinalizado[indexGrupo] = true;
                botonGrupo.setText("Grupo " + letraGrupo + " - ¡Finalizado!");
                botonGrupo.setBackground(new java.awt.Color(150, 255, 150));
                dialogoGrupo.dispose();
            }
        });

        btnDeshacer.addActionListener(e -> {
            if (!pilaDeshacerGrupo.isEmpty()) {
                int ultimoIndex = pilaDeshacerGrupo.pop();
                ganadoresPartido[ultimoIndex] = null;
                String eq1 = emparejamientos.get(ultimoIndex)[0];
                String eq2 = emparejamientos.get(ultimoIndex)[1];

                botonesPartidos[ultimoIndex].setText("Partido " + (ultimoIndex + 1) + ": " + eq1 + " vs " + eq2 + " (Pendiente)");
                botonesPartidos[ultimoIndex].setBackground(null);

                grupoFinalizado[indexGrupo] = false;
                botonGrupo.setText("Jugar Grupo " + letraGrupo + " (Incompleto)");
                botonGrupo.setBackground(new java.awt.Color(255, 255, 200));
            } else {
                javax.swing.JOptionPane.showMessageDialog(dialogoGrupo, "No hay resultados registrados para deshacer.", "Aviso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panelSur.add(btnFinalizarGrupo);
        panelSur.add(btnDeshacer);
        dialogoGrupo.add(panelSur, java.awt.BorderLayout.SOUTH);

        dialogoGrupo.setVisible(true);
    }//GEN-LAST:event_btnGenerarTorneoActionPerformed

    private void arbolTorneoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbolTorneoMouseClicked
        if (evt.getClickCount() == 2) {
            if (usuarioActual != null && "Equipo".equalsIgnoreCase(usuarioActual.getRol().getNombre())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Solo el Administrador puede editar los resultados.", "Acceso Denegado", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            NodoTorneo nodoSel = (NodoTorneo) arbolTorneo.getLastSelectedPathComponent();
            if (nodoSel == null) {
                return;
            }

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
            txtHistorial.setText("\nEl equipo '" + equipoBuscado + "' no se \nencuentra registrado en el Torneo .");
            txtHistorial.setText("\nEl equipo '" + equipoBuscado + "' no se \nencuentra inscrito.");
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
            txtHistorial.setText(" PROGRESO DEL \n"
                    + " Equipo: " + equipoBuscado.toUpperCase() + "\n"
                    + "--------------------------------------\n"
                    + progreso.obtenerTextoCompleto());
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

        if (eleccion == 0) {
            if (!historialAcciones.estaVacia()) {
                com.utp.aed.proyectotorneo.model.NodoPartido ultimoPartido = historialAcciones.pop();
                ultimoPartido.ganador = "Pendiente";
                com.utp.aed.proyectotorneo.dao.LlaveDAO dao = new com.utp.aed.proyectotorneo.dao.LlaveDAO();
                dao.actualizarNodo(ultimoPartido);

                javax.swing.tree.DefaultMutableTreeNode raizVisual = crearNodoVisual(partidoFinal);
                arbolTorneo.setModel(new javax.swing.tree.DefaultTreeModel(raizVisual));

                for (int i = 0; i < arbolTorneo.getRowCount(); i++) {
                    arbolTorneo.expandRow(i);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No hay resultados de partidos en las llaves para deshacer.", "Pila Vacía", javax.swing.JOptionPane.WARNING_MESSAGE);
            }

        } else if (eleccion == 1) {

            int confirmar = javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar todo el progreso del torneo actual?\nEsto te permitirá volver a generar los grupos o eliminatorias desde cero.",
                    "Confirmar Reinicio",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
             
                partidoFinal = null;
                arbolTorneo.setModel(null);
                while (!historialAcciones.estaVacia()) {
                    historialAcciones.pop();
                }
                btnGenerarTorneo.setEnabled(true);
                btnFinalizado.setVisible(false);
                txtHistorial.setText(" El torneo ha sido reiniciado.");

                javax.swing.JOptionPane.showMessageDialog(this, "El torneo ha sido reiniciado con éxito. Puedes volver a generar los emparejamientos.");
            }
        }
    }//GEN-LAST:event_btnDeshacerActionPerformed

    private void buscarHistorial(com.utp.aed.proyectotorneo.model.NodoPartido nodo, String equipo, ListaEnlazadaHistorial progreso) {
        if (nodo == null) {
            return;
        }

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
    private javax.swing.JLabel jLabel3;
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
            if (frente == null) {
                return null;
            }

            com.utp.aed.proyectotorneo.model.NodoPartido extraido = frente.partido;
            frente = frente.siguiente;

            if (frente == null) {
                fin = null;
            }

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
            if (estaVacia()) {
                return null;
            }

            com.utp.aed.proyectotorneo.model.NodoPartido extraido = cima.partido;
            cima = cima.siguiente;
            return extraido;

        }
    }
}
