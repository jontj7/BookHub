package org.bookhub.dao;

import org.bookhub.models.Autor;
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
            ResultSet rs = stmt.executeQuery("SELECT IdAutor, Nombre FROM Autores");

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
}
