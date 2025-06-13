package org.bookhub.dao;

import org.bookhub.models.Prestamo;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoDAOTest {

    private PrestamoDAO dao;

    @BeforeEach
    void setUp() {
        dao = new PrestamoDAO();
    }

    @Test
    void insertar() {
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setIdUsuario(1);  // Asume que existe usuario con ID 1 en BD de prueba
        nuevoPrestamo.setIdLibro(3);    // Asume que existe libro con ID 1 en BD de prueba
        nuevoPrestamo.setFechaPrestamo(LocalDate.now());
        nuevoPrestamo.setFechaDevolucion(LocalDate.now().plusDays(7));
        nuevoPrestamo.setEstado("1");   // Asumiendo que estado es string, ejemplo "1"

        boolean resultado = dao.insertar(nuevoPrestamo);
        assertTrue(resultado, "Debería insertar un nuevo préstamo exitosamente");
    }





    @Test
    void inactivar() {
        // Insertar préstamo para inactivar
        Prestamo prestamo = new Prestamo();
        prestamo.setIdUsuario(1);
        prestamo.setIdLibro(4);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(7));
        prestamo.setEstado("1");
        boolean insertado = dao.insertar(prestamo);
        assertTrue(insertado);

        List<Prestamo> prestamos = dao.buscarTodos();
        Prestamo ultimoPrestamo = prestamos.get(prestamos.size() - 1);

        boolean resultado = dao.inactivar(ultimoPrestamo.getIdPrestamo());
        assertTrue(resultado, "Debería inactivar el préstamo correctamente");

        Prestamo prestamoInactivado = dao.buscarPorId(ultimoPrestamo.getIdPrestamo());
        assertEquals("2", prestamoInactivado.getEstado(), "El estado debería ser 2 luego de inactivar");
    }

    @Test
    void buscarTodos() {
        List<Prestamo> lista = dao.buscarTodos();
        assertNotNull(lista, "La lista de préstamos no debería ser nula");
        // Puede ser vacía si no hay datos, pero debe funcionar sin excepciones
    }
}
