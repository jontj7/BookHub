package org.bookhub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class DashboardController {

    @FXML
    private void handleLogout() {
        try {
            // Carga la vista de login para cerrar sesión
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/login.fxml"));
            Parent root = loader.load();

            // Obtén la ventana actual (Stage) desde cualquier nodo (usamos btnCerrarSesion como ejemplo si tienes referencia)
            Stage stage = (Stage) root.getScene().getWindow();

            // Si no tienes botón referenciado aquí, puedes obtener la Stage así:
            // Stage stage = (Stage) someNode.getScene().getWindow();

            // Alternativamente, usa la ventana activa (hay trucos, pero depende de tu diseño)

            // Para que funcione sin nodo, puedes pasar la Stage desde el login al dashboard o mantenerla estática.
            // Aquí, por ejemplo, si no tienes Stage, intenta:
            Stage currentStage = (Stage) root.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Login");
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista de login");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
