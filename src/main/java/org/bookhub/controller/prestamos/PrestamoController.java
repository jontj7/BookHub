package org.bookhub.controller.prestamos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PrestamoController {

    @FXML
    private void abrirFormularioNuevoPrestamo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/prestamosview/prestamoNuevo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nuevo prestamo");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            mostrarError("No se pudo abrir el formulario de nuevo libro", e.getMessage());
        }
    }

    @FXML
    private void abrirFormularioEditarPrestamo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/prestamosview/prestamoedit.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Editar prestamo");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            mostrarError("No se pudo abrir el formulario de nuevo libro", e.getMessage());
        }
    }

    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
