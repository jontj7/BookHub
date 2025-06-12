package org.bookhub.controller.libro;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bookhub.dao.AutorDAO;
import org.bookhub.dao.CategoriaDAO;
import org.bookhub.dao.LibroDAO;
import org.bookhub.models.Autor;
import org.bookhub.models.Categoria;
import org.bookhub.models.Libro;

public class NuevoLibroController {

    @FXML private Button cancel;
    @FXML private TextField txtNombreLibro;
    @FXML private TextField txtCopias;
    @FXML private ComboBox<Autor> comboAutor;
    @FXML private ComboBox<Categoria> comboCategoria;

    // Inicializa los ComboBox con autores y categorías desde la BD
    @FXML
    public void initialize() {
        comboAutor.setItems(FXCollections.observableArrayList(AutorDAO.obtenerTodos()));
        comboCategoria.setItems(FXCollections.observableArrayList(CategoriaDAO.obtenerTodos()));
    }

    // Acción del botón "Cancelar"
    @FXML
    private void cancelar() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    // Acción del botón "Agregar libros"
    @FXML
    private void agregarLibro() {
        String nombre = txtNombreLibro.getText();
        Autor autor = comboAutor.getValue();
        Categoria categoria = comboCategoria.getValue();
        int copias;

        try {
            copias = Integer.parseInt(txtCopias.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Copias inválidas", "Debes ingresar un número válido para las copias.");
            return;
        }

        if (nombre.isEmpty() || autor == null || categoria == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obligatorios", "Por favor completa todos los campos.");
            return;
        }

        Libro libro = new Libro();
        libro.setNombre(nombre);
        libro.setIdAutor(autor.getId());
        libro.setIdCategoria(categoria.getId());
        libro.setStock(copias);
        libro.setImagen(null); // puedes asignar una imagen por defecto si usas rutas

        try {
            LibroDAO libroDAO = new LibroDAO(); // instanciamos DAO
            libroDAO.insertarLibro(libro);      // llamamos al método no estático
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Libro agregado correctamente.");
            cancelar(); // cerrar ventana
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar", e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
