package org.bookhub.dao;

import org.bookhub.models.Autor;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AutorDAOTest {

    private static int autorIdPrueba;

    @Test
    @Order(1)
    void agregar() {
        Autor nuevoAutor = new Autor(0, "Autor de Prueba");
        AutorDAO.agregar(nuevoAutor);

        // Buscar por nombre para recuperar su ID
        List<Autor> resultados = AutorDAO.buscarPorNombre("Autor de Prueba");
        assertFalse(resultados.isEmpty(), "El autor debería haber sido agregado");

        autorIdPrueba = resultados.get(0).getId(); // Guardamos el ID para las siguientes pruebas
        assertNotEquals(0, autorIdPrueba);
    }

    @Test
    @Order(2)
    void obtenerTodos() {
        List<Autor> lista = AutorDAO.obtenerTodos();
        assertNotNull(lista);
        assertTrue(lista.size() > 0, "Debe haber al menos un autor activo");
    }

    @Test
    @Order(3)
    void editar() {
        Autor autorEditado = new Autor(autorIdPrueba, "Autor de Prueba Editado");
        AutorDAO.editar(autorEditado);

        List<Autor> resultados = AutorDAO.buscarPorNombre("Editado");
        assertTrue(!resultados.isEmpty(), "El autor editado debe encontrarse");
        assertEquals("Autor de Prueba Editado", resultados.get(0).getNombre());
    }


    @Test
    @Order(4)
    void buscarPorNombre() {
        List<Autor> resultados = AutorDAO.buscarPorNombre("Gabriel");
        assertFalse(resultados.isEmpty(), "Debería encontrar al menos un autor con 'Gabriel'");
    }

    @Test
    @Order(5)
    void eliminar() {
        AutorDAO.eliminar(autorIdPrueba);

        // Verifica que ya no esté en la lista de activos
        List<Autor> activos = AutorDAO.obtenerTodos();
        boolean sigueActivo = activos.stream().anyMatch(a -> a.getId() == autorIdPrueba);
        assertFalse(sigueActivo, "El autor debería haber sido marcado como inactivo");
    }
}
