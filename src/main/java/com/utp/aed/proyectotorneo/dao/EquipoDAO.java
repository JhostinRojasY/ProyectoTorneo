package com.utp.aed.proyectotorneo.dao;

import com.utp.aed.proyectotorneo.model.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public boolean registrarEquipo(String nombre) {
        String sql = "INSERT INTO Equipo (nombre, estado) VALUES (?, 'INSCRITO')";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar equipo: " + e.getMessage());
            return false;
        }
    }

    public List<String> obtenerTodosLosNombres() {
        List<String> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM Equipo ORDER BY id ASC";

        try (Connection con = ConexionDB.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                nombres.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener equipos: " + e.getMessage());
        }
        return nombres;
    }

    public boolean eliminarTodos() {
        String sql = "DELETE FROM Equipo";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            return ps.executeUpdate() >= 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar equipos: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorNombre(String nombre) {
        // Reemplaza 'Conexion' por el nombre real de tu clase de conexión a la BD
        String sql = "DELETE FROM Equipo WHERE nombre = ?";

        try (java.sql.Connection con = com.utp.aed.proyectotorneo.dao.ConexionDB.getConnection(); 
                java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0; // Retorna true si se eliminó el registro con éxito

        } catch (java.sql.SQLException e) {
            System.out.println("Error al eliminar equipo: " + e.getMessage());
            return false;
        }
    }
}
