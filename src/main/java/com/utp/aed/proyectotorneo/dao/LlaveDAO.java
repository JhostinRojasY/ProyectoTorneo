package com.utp.aed.proyectotorneo.dao;

import com.utp.aed.proyectotorneo.model.NodoPartido;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LlaveDAO {

    public int guardarArbol(NodoPartido nodo) {
        if (nodo == null) return -1;
        
        // Post-orden: guardamos primero los hijos para obtener sus IDs
        int idPrevio1 = guardarArbol(nodo.partidoPrevio1);
        int idPrevio2 = guardarArbol(nodo.partidoPrevio2);
        
        String sql = "INSERT INTO Llave (equipo1, equipo2, ganador, previo1_id, previo2_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, nodo.equipo1);
            ps.setString(2, nodo.equipo2);
            ps.setString(3, nodo.ganador);
            
            if (idPrevio1 != -1) ps.setInt(4, idPrevio1); else ps.setNull(4, Types.INTEGER);
            if (idPrevio2 != -1) ps.setInt(5, idPrevio2); else ps.setNull(5, Types.INTEGER);
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                nodo.id = rs.getInt(1);
                return nodo.id;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar nodo de árbol: " + e.getMessage());
        }
        return -1;
    }

    public void actualizarNodo(NodoPartido nodo) {
        if (nodo == null || nodo.id == -1) return;
        
        String sql = "UPDATE Llave SET equipo1 = ?, equipo2 = ?, ganador = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nodo.equipo1);
            ps.setString(2, nodo.equipo2);
            ps.setString(3, nodo.ganador);
            ps.setInt(4, nodo.id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar nodo: " + e.getMessage());
        }
    }

    public void eliminarArbol() {
        String sql = "DELETE FROM Llave";
        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al eliminar árbol: " + e.getMessage());
        }
    }

    public NodoPartido cargarArbol() {
        String sql = "SELECT * FROM Llave";
        Map<Integer, NodoPartido> mapaNodos = new HashMap<>();
        Map<Integer, Integer> mapaPrevio1 = new HashMap<>();
        Map<Integer, Integer> mapaPrevio2 = new HashMap<>();
        
        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String eq1 = rs.getString("equipo1");
                String eq2 = rs.getString("equipo2");
                String ganador = rs.getString("ganador");
                int p1 = rs.getInt("previo1_id");
                int p2 = rs.getInt("previo2_id");
                
                NodoPartido nodo = new NodoPartido(eq1, eq2);
                nodo.id = id;
                nodo.ganador = ganador;
                mapaNodos.put(id, nodo);
                
                if (!rs.wasNull() && p1 != 0) mapaPrevio1.put(id, p1);
                if (!rs.wasNull() && p2 != 0) mapaPrevio2.put(id, p2);
            }
            
            // Reconstruir enlaces
            for (Integer id : mapaNodos.keySet()) {
                NodoPartido nodo = mapaNodos.get(id);
                if (mapaPrevio1.containsKey(id)) {
                    nodo.partidoPrevio1 = mapaNodos.get(mapaPrevio1.get(id));
                }
                if (mapaPrevio2.containsKey(id)) {
                    nodo.partidoPrevio2 = mapaNodos.get(mapaPrevio2.get(id));
                }
            }
            
            // Buscar la raíz (el nodo que no es previo de nadie)
            for (NodoPartido posibleRaiz : mapaNodos.values()) {
                boolean esHijo = false;
                for (NodoPartido padre : mapaNodos.values()) {
                    if (padre.partidoPrevio1 == posibleRaiz || padre.partidoPrevio2 == posibleRaiz) {
                        esHijo = true;
                        break;
                    }
                }
                if (!esHijo) {
                    return posibleRaiz;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al cargar el árbol: " + e.getMessage());
        }
        return null; // Si no hay árbol guardado
    }
}
