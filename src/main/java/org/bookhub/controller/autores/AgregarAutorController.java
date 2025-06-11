package org.bookhub.controller.autores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bookhub.dao.AutorDAO;
import org.bookhub.models.Autor;

public class AgregarAutorController {

    @FXML
    private TextField txtNombreAutor;

    @FXML
    private void guardarAutor() {
        String nombre = txtNombreAutor.getText().trim();

        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "El nombre del autor no puede estar vacío.");
            return;
        }

        Autor nuevoAutor = new Autor(); // ahora válido
        nuevoAutor.setNombre(nombre);
        AutorDAO.agregar(nuevoAutor);
        cerrarVentana();
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombreAutor.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
