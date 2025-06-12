package org.bookhub.controller.usuarios;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.bookhub.dao.UsuarioDAO;
import org.bookhub.models.Usuario;

public class UsuarioDeleteController {

    private Usuario usuarioSeleccionado;

    public void setUsuario(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
    }
    @FXML private javafx.scene.control.Button btnEliminar;


    @FXML
    private void onCancelar() {
        Stage stage = (Stage) btnEliminar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onGuardar() {
        if (usuarioSeleccionado != null) {
            UsuarioDAO dao = new UsuarioDAO();
            dao.eliminar(usuarioSeleccionado.getId());

            mostrarAlerta("✅ Usuario eliminado correctamente.");

            Stage stage = (Stage) btnEliminar.getScene().getWindow();
            stage.close();
        } else {
            mostrarAlerta("⚠️ No hay usuario seleccionado para eliminar.");
        }
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
