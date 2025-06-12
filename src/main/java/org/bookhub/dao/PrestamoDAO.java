package org.bookhub.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bookhub.models.Prestamo;
import org.bookhub.models.Usuario;
import org.bookhub.models.Libro;
import org.bookhub.utils.ConnectionManager;

public class PrestamoDAO {

    // INSERTAR un nuevo préstamo
    public boolean insertar(Prestamo prestamo) {
        String sql = "INSERT INTO Prestamos (IdUsuario, IdLibro, FechaPrestamo, FechaDevolucion, Estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getIdUsuario());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            stmt.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            stmt.setString(5, prestamo.getEstado());  // Cambiado a 'getEstado()'

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // EDITAR un préstamo existente
    public boolean editar(Prestamo prestamo) {
        String sql = "UPDATE Prestamos SET IdUsuario = ?, IdLibro = ?, FechaPrestamo = ?, FechaDevolucion = ?, Estado = ? WHERE IdPrestamo = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getIdUsuario());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            stmt.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            stmt.setString(5, prestamo.getEstado());  // Cambiado a 'getEstado()'
            stmt.setInt(6, prestamo.getIdPrestamo());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // INACTIVAR un préstamo (cambiar estado a 2)
    public boolean inactivar(int idPrestamo) {
        String sql = "UPDATE Prestamos SET Estado = 2 WHERE IdPrestamo = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPrestamo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // BUSCAR POR ID
    public Prestamo buscarPorId(int idPrestamo) {
        String sql = "SELECT * FROM Prestamos WHERE IdPrestamo = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPrestamo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrestamo(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // BUSCAR TODOS
    public List<Prestamo> buscarTodos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.IdPrestamo, p.IdUsuario, p.IdLibro, CONCAT(u.Nombres, ' ', u.Apellidos) AS nombreUsuario, l.Nombre AS nombreLibro, p.FechaPrestamo, p.FechaDevolucion, p.Estado FROM Prestamos p JOIN Usuarios u ON p.IdUsuario = u.IdUsuario JOIN Libros l ON p.IdLibro = l.IdLibro";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("IdPrestamo"));
                prestamo.setIdUsuario(rs.getInt("IdUsuario"));
                prestamo.setIdLibro(rs.getInt("IdLibro"));
                prestamo.setNombreUsuario(rs.getString("nombreUsuario"));
                prestamo.setNombreLibro(rs.getString("nombreLibro"));

                java.sql.Date fechaPrestamoSql = rs.getDate("FechaPrestamo");
                prestamo.setFechaPrestamo(fechaPrestamoSql != null ? fechaPrestamoSql.toLocalDate() : null);

                java.sql.Date fechaDevolucionSql = rs.getDate("FechaDevolucion");
                prestamo.setFechaDevolucion(fechaDevolucionSql != null ? fechaDevolucionSql.toLocalDate() : null);

                prestamo.setEstado(rs.getString("Estado"));

                prestamos.add(prestamo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prestamos;
    }


    // BUSCAR CON FILTRO (por nombre de libro o estado)
    public List<Prestamo> buscarConFiltro(String nombreLibro, int estado) {
        String sql = """
            SELECT p.* FROM Prestamos p
            JOIN Libros l ON p.IdLibro = l.IdLibro
            WHERE l.Titulo LIKE ? AND p.Estado = ?
        """;

        List<Prestamo> lista = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombreLibro + "%");
            stmt.setInt(2, estado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToPrestamo(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Método auxiliar para convertir ResultSet en objeto Prestamo
    private Prestamo mapResultSetToPrestamo(ResultSet rs) throws SQLException {
        Prestamo p = new Prestamo();
        p.setIdPrestamo(rs.getInt("IdPrestamo"));
        p.setIdUsuario(rs.getInt("IdUsuario"));
        p.setIdLibro(rs.getInt("IdLibro"));

        Date fechaPrestamo = rs.getDate("FechaPrestamo");
        if (fechaPrestamo != null) {
            p.setFechaPrestamo(fechaPrestamo.toLocalDate());
        }

        Date fechaDevolucion = rs.getDate("FechaDevolucion");
        if (fechaDevolucion != null) {
            p.setFechaDevolucion(fechaDevolucion.toLocalDate());
        }

        p.setEstado(rs.getString("Estado"));  // Cambiado a setEstado()
        return p;
    }

    // OBTENER lista de usuarios activos (Estado = 1)
    public List<Usuario> obtenerUsuariosActivos() {
        String sql = "SELECT IdUsuario, Nombres, Apellidos FROM Usuarios WHERE Estado = 1";
        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("IdUsuario"));
                // Concatenar nombres y apellidos para mostrar nombre completo
                u.setNombres(rs.getString("Nombres") + " " + rs.getString("Apellidos"));
                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // OBTENER lista de libros activos (Estado = 1)
    public List<Libro> obtenerLibrosActivos() {
        String sql = "SELECT IdLibro, Titulo FROM Libros WHERE Estado = 1";
        List<Libro> lista = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro l = new Libro();
                l.setIdLibro(rs.getInt("IdLibro"));
                l.setNombre(rs.getString("Titulo"));  // Cambiado a "Titulo"
                lista.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
