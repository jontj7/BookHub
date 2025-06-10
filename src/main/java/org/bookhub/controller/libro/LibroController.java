package org.bookhub.controller.libro;

import org.bookhub.models.Libro;
import org.bookhub.service.LibroService;

import java.util.List;

public class LibroController {

    private LibroService libroService = new LibroService();

    public void mostrarLibros() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        for (Libro libro : libros) {
            System.out.println("📚 " + libro.getNombre() + " - Stock: " + libro.getStock());
        }
    }
}
