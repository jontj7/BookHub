package org.bookhub.dao;

import org.bookhub.models.Usuario;
import org.bookhub.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario obtenerPorCredenciales(String username, String password) {
        String sql = "SELECT IdUsuario, Nombres, Apellidos, IdRol FROM Usuarios WHERE Usuario = ? AND Contraseña = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Aquí puedes aplicar hash si es necesario

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("IdUsuario"));
                u.setNombres(rs.getString("Nombres"));
                u.setApellidos(rs.getString("Apellidos"));
                u.setIdRol(rs.getInt("IdRol"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
