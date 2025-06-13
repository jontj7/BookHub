package org.bookhub.dao;

import org.bookhub.models.Libro;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibroDAOTest {

    private static LibroDAO libroDAO;

    @BeforeAll
    static void setup() {
        libroDAO = new LibroDAO();
    }

    @Test
    void listarLibros() {
        List<Libro> libros = libroDAO.listarLibros();
        assertNotNull(libros, "La lista de libros no debe ser nula");
        // Si la tabla está vacía, puede ser 0, pero si hay datos, > 0
        // assertTrue(libros.size() > 0, "Debe haber al menos un libro listado");
    }

    @Test
    void insertarLibro() {
        Libro libro = new Libro();
        libro.setNombre("Libro de prueba");
        libro.setIdAutor(1);       // Asegúrate que exista el autor con ID 1 en la base de datos
        libro.setIdCategoria(1);   // Asegúrate que exista la categoría con ID 1 en la base de datos
        libro.setStock(10);
        libro.setImagen("imagen.jpg");

        libroDAO.insertarLibro(libro);

        List<Libro> libros = libroDAO.listarLibros();
        boolean encontrado = libros.stream()
                .anyMatch(l -> l.getNombre().equals("Libro de prueba"));
        assertTrue(encontrado, "El libro insertado debe estar en la lista");
    }

    @Test
    void actualizarLibro() {
        // Insertar libro para actualizarlo
        Libro libro = new Libro();
        libro.setNombre("Libro para actualizar");
        libro.setIdAutor(1);
        libro.setIdCategoria(1);
        libro.setStock(5);
        libro.setImagen("imagen1.jpg");

        libroDAO.insertarLibro(libro);

        // Obtener el libro recién insertado (último en la lista o buscar por nombre)
        List<Libro> libros = libroDAO.listarLibros();
        Libro libroInsertado = libros.stream()
                .filter(l -> l.getNombre().equals("Libro para actualizar"))
                .findFirst()
                .orElse(null);

        assertNotNull(libroInsertado, "El libro debe existir para poder actualizarlo");

        // Actualizar datos
        libroInsertado.setNombre("Libro actualizado");
        libroInsertado.setStock(15);
        libroInsertado.setImagen("imagen_actualizada.jpg");

        libroDAO.actualizarLibro(libroInsertado);

        // Buscar el libro actualizado
        List<Libro> librosActualizados = libroDAO.listarLibros();
        boolean encontrado = librosActualizados.stream()
                .anyMatch(l -> l.getIdLibro() == libroInsertado.getIdLibro() &&
                        l.getNombre().equals("Libro actualizado") &&
                        l.getStock() == 15 &&
                        l.getImagen().equals("imagen_actualizada.jpg"));

        assertTrue(encontrado, "El libro debe haber sido actualizado correctamente");
    }

    @Test
    void eliminar() {
        // Insertar libro para eliminar
        Libro libro = new Libro();
        libro.setNombre("Libro para eliminar");
        libro.setIdAutor(1);
        libro.setIdCategoria(1);
        libro.setStock(7);
        libro.setImagen("imagen_eliminar.jpg");

        libroDAO.insertarLibro(libro);

        // Obtener el libro insertado
        List<Libro> libros = libroDAO.listarLibros();
        Libro libroInsertado = libros.stream()
                .filter(l -> l.getNombre().equals("Libro para eliminar"))
                .findFirst()
                .orElse(null);

        assertNotNull(libroInsertado, "El libro debe existir para eliminarlo");

        // Eliminar el libro (cambiar IdEstado a 2)
        LibroDAO.eliminar(libroInsertado.getIdLibro());

        // Verificar que ya no aparece en la lista de libros con IdEstado=1
        List<Libro> librosDespuesDeEliminar = libroDAO.listarLibros();
        boolean existe = librosDespuesDeEliminar.stream()
                .anyMatch(l -> l.getIdLibro() == libroInsertado.getIdLibro());

        assertFalse(existe, "El libro debe haber sido eliminado (IdEstado cambiado)");
    }
}
