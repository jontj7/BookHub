package org.bookhub.auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bookhub.models.Usuario;
import org.bookhub.service.UsuarioService;

import java.io.IOException;

public class RegistrarseController {



    @FXML private TextField txtUsername1;       // Nombres
    @FXML private TextField txtApellidos;   // Apellidos
    @FXML private PasswordField txtPassword11;  // Contraseña
    @FXML private Button btnregister;
    @FXML private Hyperlink linkLogin;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void handleRegister() {
        String nombres = txtUsername1.getText();
        String apellidos = txtApellidos.getText();
        String contrasena = txtPassword11.getText();

        if (nombres.isEmpty() || apellidos.isEmpty() || contrasena.isEmpty()) {
            showAlert("Campos vacíos", "Por favor completa todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setIdRol(2); // Por defecto rol usuario normal

        boolean registrado = usuarioService.registrarUsuario(nuevoUsuario);

        if (registrado) {
            showAlert("Registro exitoso", "Usuario creado correctamente. Ahora inicia sesión.", Alert.AlertType.INFORMATION);
            volverAlLogin();
        } else {
            showAlert("Error", "No se pudo registrar el usuario. Intenta de nuevo.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/bookhub/view/login.fxml"));
            Stage stage = (Stage) linkLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar sesión");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de login.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
