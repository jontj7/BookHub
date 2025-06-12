package org.bookhub.controller.autores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bookhub.dao.AutorDAO;
import org.bookhub.models.Autor;

public class EditarAutorController {

    @FXML
    private TextField txtNombreAutor;

    private Autor autorActual;

    public void setAutor(Autor autor) {
        this.autorActual = autor;
        txtNombreAutor.setText(autor.getNombre());
    }

    @FXML
    private void guardarCambios() {
        String nuevoNombre = txtNombreAutor.getText().trim();

        if (nuevoNombre.isEmpty()) {
            mostrarAlerta("Error", "El nombre del autor no puede estar vacío.");
            return;
        }

        autorActual.setNombre(nuevoNombre);
        AutorDAO.editar(autorActual);
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
