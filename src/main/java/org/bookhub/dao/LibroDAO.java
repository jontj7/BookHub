package org.bookhub.dao;

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
}
