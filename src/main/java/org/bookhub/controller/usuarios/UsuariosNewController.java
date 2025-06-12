package org.bookhub.controller.usuarios;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bookhub.dao.UsuarioDAO;
import org.bookhub.models.Usuario;

public class UsuariosNewController {

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtContrasena;
    @FXML private TextField txtUsuario;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void onGuardar() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (nombres.isEmpty() || apellidos.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        // Generar el usuario automáticamente: 3 letras del nombre + 3 del apellido, en minúsculas
        String usuarioGenerado = (
                nombres.replaceAll("\\s+", "").substring(0, Math.min(3, nombres.length())) +
                        apellidos.replaceAll("\\s+", "").substring(0, Math.min(3, apellidos.length()))
        ).toLowerCase();

        Usuario nuevo = new Usuario();
        nuevo.setNombres(nombres);
        nuevo.setApellidos(apellidos);
        nuevo.setContrasena(contrasena);
        nuevo.setUsuario(usuarioGenerado);
        nuevo.setIdRol(2);     // Rol de usuario normal
        nuevo.setIdEstado(1);  // Estado activo

        boolean guardado = usuarioDAO.guardar(nuevo);
        if (guardado) {
            mostrarAlerta("✅ Usuario '" + usuarioGenerado + "' agregado correctamente.");
            cerrarVentana();
        } else {
            mostrarAlerta("❌ Error al guardar el usuario.");
        }
    }


    @FXML
    public void onCancelar() {
        cerrarVentana();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombres.getScene().getWindow();
        stage.close();
    }
}
