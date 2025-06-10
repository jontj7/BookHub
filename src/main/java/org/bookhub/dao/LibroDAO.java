package org.bookhub.dao;
import java.util.Map;
import java.util.LinkedHashMap;


import org.bookhub.models.Libro;
import org.bookhub.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libros";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("IdLibro"));
                libro.setNombre(rs.getString("Nombre"));
                libro.setIdAutor(rs.getInt("IdAutor"));
                libro.setIdCategoria(rs.getInt("IdCategoria"));
                libro.setStock(rs.getInt("Stock"));
                libro.setImagen(rs.getString("Imagen"));

                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public static void insertarLibro(Libro libro) {
        String sql = "INSERT INTO Libros (Nombre, IdAutor, IdCategoria, Stock, Imagen) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getNombre());
            stmt.setInt(2, libro.getIdAutor());
            stmt.setInt(3, libro.getIdCategoria());
            stmt.setInt(4, libro.getStock());
            stmt.setString(5, libro.getImagen());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Libro> listarLibrosConDetalles() {
        List<Libro> libros = new ArrayList<>();
        String sql = """
        SELECT l.IdLibro, l.Nombre, a.Nombre AS NombreAutor, c.Nombre AS NombreCategoria, l.Stock
        FROM Libros l
        JOIN Autores a ON l.IdAutor = a.IdAutor
        JOIN Categorias c ON l.IdCategoria = c.IdCategoria
    """;

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("IdLibro"));
                libro.setNombre(rs.getString("Nombre"));
                libro.setNombreAutor(rs.getString("NombreAutor"));
                libro.setNombreCategoria(rs.getString("NombreCategoria")); // <== NUEVO
                libro.setStock(rs.getInt("Stock"));
                libros.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libros;
    }

    public void actualizarLibro(Libro libro) {
        String sql = """
        UPDATE Libros
        SET Nombre = ?, IdAutor = ?, IdCategoria = ?, Stock = ?, Imagen = ?
        WHERE IdLibro = ?
    """;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getNombre());
            stmt.setInt(2, libro.getIdAutor());
            stmt.setInt(3, libro.getIdCategoria());
            stmt.setInt(4, libro.getStock());
            stmt.setString(5, libro.getImagen());
            stmt.setInt(6, libro.getIdLibro());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int obtenerIdAutorPorNombre(String nombreAutor) {
        String sql = "SELECT IdAutor FROM Autores WHERE Nombre = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreAutor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("IdAutor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // o lanzar excepción si prefieres
    }

    public int obtenerIdCategoriaPorNombre(String nombreCategoria) {
        String sql = "SELECT IdCategoria FROM Categorias WHERE Nombre = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreCategoria);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("IdCategoria");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Libros más prestados (Top 5)
    public Map<String, Integer> obtenerTopLibrosMasPrestados() {
        Map<String, Integer> topLibros = new LinkedHashMap<>();
        String sql = """
        SELECT l.Nombre, COUNT(p.IdPrestamo) AS TotalPrestamos
        FROM Prestamos p
        JOIN Libros l ON p.IdLibro = l.IdLibro
        GROUP BY l.Nombre
        ORDER BY TotalPrestamos DESC
        OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY
    """;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                topLibros.put(rs.getString("Nombre"), rs.getInt("TotalPrestamos"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topLibros;
    }

    // Porcentaje de libros por categoría
    public Map<String, Integer> obtenerDistribucionPorCategoria() {
        Map<String, Integer> categorias = new LinkedHashMap<>();
        String sql = """
        SELECT c.Nombre, COUNT(l.IdLibro) AS Cantidad
        FROM Libros l
        JOIN Categorias c ON l.IdCategoria = c.IdCategoria
        GROUP BY c.Nombre
    """;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categorias.put(rs.getString("Nombre"), rs.getInt("Cantidad"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }






}
