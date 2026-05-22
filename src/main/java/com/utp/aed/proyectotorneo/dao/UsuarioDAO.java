package com.utp.aed.proyectotorneo.dao;

import com.utp.aed.proyectotorneo.model.Rol;
import com.utp.aed.proyectotorneo.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario login(String username, String password) {
        String sql = "SELECT u.id, u.username, u.password, r.id as rol_id, r.nombre as rol_nombre " +
                     "FROM Usuario u " +
                     "JOIN Rol r ON u.rol_id = r.id " +
                     "WHERE u.username = ? AND u.password = ?";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol(rs.getInt("rol_id"), rs.getString("rol_nombre"));
                    return new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rol);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null;
    }

    public boolean registrarUsuarioEquipo(String username, String password) {
        String sql = "INSERT INTO Usuario (username, password, rol_id) VALUES (?, ?, (SELECT id FROM Rol WHERE nombre = 'Equipo'))";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            int filas = ps.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario de equipo: " + e.getMessage());
            return false;
        }
    }
}
