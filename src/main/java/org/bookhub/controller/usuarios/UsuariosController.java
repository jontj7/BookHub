package org.bookhub.controller.usuarios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bookhub.dao.UsuarioDAO;
import org.bookhub.models.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UsuariosController {

    @FXML
    private TextField labelingresarlibros; // nombre

    @FXML
    private TextField labelingresarlibros1; // apellido

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, String> columnaNombre;

    @FXML
    private TableColumn<Usuario, String> columnaApellido;

    @FXML
    private TableColumn<Usuario, String> columnaUsuario;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ObservableList<Usuario> listaObservable;

    @FXML
    public void initialize() {
        configurarTabla();
        cargarUsuarios();
        agregarListenersBusqueda();
    }

    private void configurarTabla() {
        columnaNombre.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNombres()));
        columnaApellido.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getApellidos()));
        columnaUsuario.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getUsuario()));
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        listaObservable = FXCollections.observableArrayList(usuarios);
        tablaUsuarios.setItems(listaObservable);
    }

    private void agregarListenersBusqueda() {
        labelingresarlibros.textProperty().addListener((obs, oldVal, newVal) -> filtrarUsuarios());
        labelingresarlibros1.textProperty().addListener((obs, oldVal, newVal) -> filtrarUsuarios());
    }

    private void filtrarUsuarios() {
        String nombre = labelingresarlibros.getText().toLowerCase();
        String apellido = labelingresarlibros1.getText().toLowerCase();

        List<Usuario> filtrados = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getNombres().toLowerCase().contains(nombre)
                        && u.getApellidos().toLowerCase().contains(apellido))
                .collect(Collectors.toList());

        listaObservable.setAll(filtrados);
    }

    @FXML
    private void onNuevoUsuario(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/usuariosviews/usuarionew.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nuevo Usuario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("❌ No se pudo abrir el formulario de nuevo usuario.");
        }
    }

    @FXML
    private void onEditarUsuario(MouseEvent event) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/usuariosviews/usuarioedit.fxml"));
                Parent root = loader.load();

                UsuariosEditController controller = loader.getController();
                controller.setUsuario(seleccionado);

                Stage stage = new Stage();
                stage.setTitle("Editar Usuario");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("❌ No se pudo abrir el formulario de edición.");
            }
        } else {
            mostrarAlerta("Selecciona un usuario para editar.");
        }
    }

    @FXML
    private void onEliminarUsuario(MouseEvent event) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Estás seguro de eliminar este usuario?");
            confirmacion.setContentText("Usuario: " + seleccionado.getUsuario());

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (usuarioDAO.eliminar(seleccionado.getId())) {
                        listaObservable.remove(seleccionado);
                        mostrarAlerta("Usuario eliminado correctamente.");
                    } else {
                        mostrarAlerta("Error al eliminar usuario.");
                    }
                }
            });
        } else {
            mostrarAlerta("Selecciona un usuario para eliminar.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
