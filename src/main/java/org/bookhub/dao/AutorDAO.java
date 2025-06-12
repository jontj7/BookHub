package org.bookhub.dao;

import org.bookhub.models.Autor;
import org.bookhub.models.Prestamo;
import org.bookhub.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    public static List<Autor> obtenerTodos() {
        List<Autor> lista = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IdAutor, Nombre FROM Autores WHERE IdEstado = 1");

            while (rs.next()) {
                lista.add(new Autor(rs.getInt("IdAutor"), rs.getString("Nombre")));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static void agregar(Autor autor) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Autores (Nombre) VALUES (?)")) {

            stmt.setString(1, autor.getNombre());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void editar(Autor autor) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Autores SET Nombre = ? WHERE IdAutor = ?")) {

            stmt.setString(1, autor.getNombre());
            stmt.setInt(2, autor.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void eliminar(int id) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Autores SET IdEstado = 2 WHERE IdAutor = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Autor> buscarPorNombre(String nombre) {
        List<Autor> autores = new ArrayList<>();

        String sql = "SELECT IdAutor, Nombre FROM Autores WHERE Nombre LIKE ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%"); // búsqueda parcial
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdAutor");
                String nombreAutor = rs.getString("Nombre");

                Autor autor = new Autor(id, nombreAutor);
                autores.add(autor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autores;
    }




}
