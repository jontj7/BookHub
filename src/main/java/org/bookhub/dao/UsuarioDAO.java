package org.bookhub.dao;

import org.bookhub.models.Usuario;
import org.bookhub.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario obtenerPorCredenciales(String username, String password) {
        String sql = "SELECT IdUsuario, Nombres, Apellidos, Usuario, IdRol FROM Usuarios WHERE Usuario = ? AND Contraseña = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("IdUsuario"));
                u.setNombres(rs.getString("Nombres"));
                u.setApellidos(rs.getString("Apellidos"));
                u.setUsuario(rs.getString("Usuario"));
                u.setIdRol(rs.getInt("IdRol"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean guardar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (Nombres, Apellidos, Usuario, Contraseña, IdRol) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombres());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getUsuario());
            stmt.setString(4, usuario.getContrasena());
            stmt.setInt(5, usuario.getIdRol());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE Usuarios SET Nombres = ?, Apellidos = ?, Usuario = ?, IdRol = ?, IdEstado = ? WHERE IdUsuario = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombres());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getUsuario());
            stmt.setInt(4, usuario.getIdRol());
            stmt.setInt(5, usuario.getIdEstado());
            stmt.setInt(6, usuario.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idUsuario) {
        String sql = "UPDATE Usuarios SET IdEstado = 2 WHERE IdUsuario = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT IdUsuario, Nombres, Apellidos, Usuario, IdRol, IdEstado FROM Usuarios WHERE IdEstado = 1";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("IdUsuario"));
                u.setNombres(rs.getString("Nombres"));
                u.setApellidos(rs.getString("Apellidos"));
                u.setUsuario(rs.getString("Usuario"));
                u.setIdRol(rs.getInt("IdRol"));
                u.setIdEstado(rs.getInt("IdEstado"));
                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean existeUsuario(String nombres, String apellidos) {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE Nombres = ? AND Apellidos = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombres);
            stmt.setString(2, apellidos);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
