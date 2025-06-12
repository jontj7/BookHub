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
import javafx.event.ActionEvent;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UsuariosController {

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    @FXML
    private TextField labelingresarlibros;

    @FXML
    private TextField labelingresarlibros1;

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
    private void onNuevoUsuario(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/usuariosviews/usuarionew.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nuevo Usuario");
            stage.setScene(new Scene(root));

            // 🔁 Escucha cuando se cierre la ventana para recargar la tabla
            stage.setOnHiding(e -> cargarUsuarios());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("❌ No se pudo abrir el formulario de nuevo usuario.");
        }
    }



    @FXML
    private void onEditarUsuario(javafx.event.ActionEvent event) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("⚠️ Por favor, selecciona un usuario para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/usuariosviews/usuarioedit.fxml"));
            Parent root = loader.load();

            // Accede al controlador y pasa el usuario seleccionado
            UsuariosEditController controller = loader.getController();
            controller.setUsuario(seleccionado);

            // Crear nueva ventana de edición
            Stage stage = new Stage();
            stage.setTitle("Editar Usuario");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            // Recargar tabla al cerrar la ventana de edición
            stage.setOnHiding(e -> cargarUsuarios());

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("❌ Error al cargar la vista de edición del usuario.");
        }
    }


    @FXML
    private void onEliminarUsuario(ActionEvent event) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/usuariosviews/usuariodelete.fxml"));
                Parent root = loader.load();

                UsuarioDeleteController controller = loader.getController();
                controller.setUsuario(seleccionado);

                Stage stage = new Stage();
                stage.setTitle("Eliminar Usuario");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                // Opcional: recargar tabla al cerrar
                stage.setOnHiding(e -> cargarUsuarios());

            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("❌ Error al abrir la ventana de confirmación.");
            }
        } else {
            mostrarAlerta("⚠️ Selecciona un usuario para eliminar.");
        }
    }

}
