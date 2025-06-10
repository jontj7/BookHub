package org.bookhub.auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.bookhub.models.Usuario;
import org.bookhub.models.UsuarioSesion;
import org.bookhub.service.LoginService;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private javafx.scene.control.Hyperlink linkCrearCuenta;


    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    private final LoginService loginService = new LoginService();

    @FXML
    private void abrirVistaRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/registrarseview/registrarsehome.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) linkCrearCuenta.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Crear cuenta");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de registro", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void handleLogin() {
        String usuario = txtUsername.getText();
        String contrasena = txtPassword.getText();

        Usuario usuarioObj = loginService.autenticar(usuario, contrasena);

        if (usuarioObj != null) {
            UsuarioSesion.idUsuario = usuarioObj.getId();
            UsuarioSesion.nombres = usuarioObj.getNombres();
            UsuarioSesion.apellidos = usuarioObj.getApellidos();
            UsuarioSesion.idRol = usuarioObj.getIdRol();

            cargarDashboard();
        } else {
            showAlert("Login incorrecto", "Usuario o contraseña no válidos", Alert.AlertType.ERROR);
        }
    }

    private void cargarDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/homeview/home.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista del dashboard", Alert.AlertType.ERROR);
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
