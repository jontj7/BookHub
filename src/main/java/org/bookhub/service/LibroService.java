package org.bookhub.service;

import org.bookhub.dao.LibroDAO;
import org.bookhub.models.Libro;

import java.util.List;

public class LibroService {

    private LibroDAO libroDAO = new LibroDAO();

    public List<Libro> obtenerTodosLosLibros() {
        return libroDAO.listarLibros();
    }
}
