package org.bookhub.dao;

import org.bookhub.models.Categoria;
import org.bookhub.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    public static List<Categoria> obtenerTodos() {
        List<Categoria> lista = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IdCategoria, Nombre FROM Categorias");

            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("IdCategoria"), rs.getString("Nombre")));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
