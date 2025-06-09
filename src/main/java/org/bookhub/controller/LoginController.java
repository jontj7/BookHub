package org.bookhub.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private void initialize() {

        // Aquí puedes inicializar cosas si quieres
    }

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if ("admin".equals(username) && "1234".equals(password)) {
            try {
                // Carga la nueva vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/dashboard.fxml"));
                Parent root = loader.load();

                // Cambia la escena
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Dashboard");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo cargar la vista del dashboard", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error de login", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
