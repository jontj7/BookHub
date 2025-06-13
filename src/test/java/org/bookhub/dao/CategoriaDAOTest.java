package org.bookhub.dao;

import org.bookhub.models.Categoria;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaDAOTest {

    @Test
    void obtenerTodos() {
        List<Categoria> categorias = CategoriaDAO.obtenerTodos();

        assertNotNull(categorias, "La lista de categorías no debería ser nula");
        assertFalse(categorias.isEmpty(), "La lista de categorías no debería estar vacía");

        // Puedes agregar una validación extra para los datos, si tienes categorías de prueba
        Categoria primera = categorias.get(0);
        assertTrue(primera.getId() > 0, "El IdCategoria debería ser mayor que cero");
        assertNotNull(primera.getNombre(), "El nombre de la categoría no debería ser nulo");
        assertFalse(primera.getNombre().isEmpty(), "El nombre de la categoría no debería estar vacío");
    }
}
