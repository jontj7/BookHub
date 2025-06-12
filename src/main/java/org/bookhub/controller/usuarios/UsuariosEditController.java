package org.bookhub.controller.usuarios;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bookhub.dao.UsuarioDAO;
import org.bookhub.models.Usuario;

public class UsuariosEditController {

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtContrasena;
    @FXML private TextField txtUsuario;

    private Usuario usuarioActual;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        txtNombres.setText(usuario.getNombres());
        txtApellidos.setText(usuario.getApellidos());
        txtUsuario.setText(usuario.getUsuario());
        txtContrasena.setText(usuario.getContrasena());
    }

    @FXML
    public void onGuardar() {
        usuarioActual.setNombres(txtNombres.getText());
        usuarioActual.setApellidos(txtApellidos.getText());
        usuarioActual.setUsuario(txtUsuario.getText());
        usuarioActual.setContrasena(txtContrasena.getText());

        boolean actualizado = usuarioDAO.actualizar(usuarioActual);
        if (actualizado) {
            mostrarAlerta("✅ Usuario actualizado correctamente.");
            cerrarVentana();
        } else {
            mostrarAlerta("❌ Error al actualizar el usuario.");
        }
    }

    @FXML
    public void onCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombres.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
