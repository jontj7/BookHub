package org.bookhub.controller.libro;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import org.bookhub.dao.AutorDAO;
import org.bookhub.models.Autor;
import org.bookhub.models.Libro;
import org.bookhub.service.LibroService;

import java.util.List;

public class LibroController {

    @FXML private TableView<Autor> tablaLibros;
    private LibroService libroService = new LibroService();

    public void mostrarLibros() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        for (Libro libro : libros) {
            System.out.println("📚 " + libro.getNombre() + " - Stock: " + libro.getStock());
        }
    }



    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
