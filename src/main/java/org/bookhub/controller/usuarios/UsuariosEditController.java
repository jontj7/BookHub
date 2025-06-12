package org.bookhub.controller.usuarios;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bookhub.dao.UsuarioDAO;
import org.bookhub.models.Usuario;
import javafx.event.ActionEvent;

public class UsuariosEditController {

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtContrasena;


    private Usuario usuarioActual;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        txtNombres.setText(usuario.getNombres());
        txtApellidos.setText(usuario.getApellidos());
        txtContrasena.setText(usuario.getContrasena());
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String contrasena = txtContrasena.getText();

        // Validación
        if (nombres == null || nombres.isEmpty() ||
                apellidos == null || apellidos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos requeridos");
            alert.setHeaderText(null);
            alert.setContentText("Todos los campos son obligatorios.");
            alert.showAndWait();
            return;
        }

        // Asignar valores al usuario actual
        usuarioActual.setNombres(nombres);
        usuarioActual.setApellidos(apellidos);
        usuarioActual.setContrasena(contrasena);

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