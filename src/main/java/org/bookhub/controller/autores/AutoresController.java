package org.bookhub.controller.autores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bookhub.dao.AutorDAO;
import org.bookhub.models.Autor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AutoresController implements Initializable {

    @FXML private TableView<Autor> tablaAutores;
    @FXML private TableColumn<Autor, Integer> colIdAutor;
    @FXML private TableColumn<Autor, String> colNombre;
    @FXML private TextField searchAutores;

    private ObservableList<Autor> listaAutores;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarAutores();

        searchAutores.setOnAction(event -> buscarAutores());

        searchAutores.textProperty().addListener((obs, oldText, newText) -> {
            buscarAutores(); // llama siempre que cambia el texto
        });

    }

    private void configurarColumnas() {
        colIdAutor.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    private void cargarAutores() {
        listaAutores = FXCollections.observableArrayList(AutorDAO.obtenerTodos());
        tablaAutores.setItems(listaAutores);
    }

    @FXML
    private void abrirFormularioAgregar() {
        abrirVentanaEmergente("/org/bookhub/view/autoresviews/nuevoautores.fxml");
        cargarAutores();
    }

    @FXML
    private void abrirFormularioEditar() {
        Autor seleccionado = tablaAutores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Edición", "Debes seleccionar un autor para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/autoresviews/editautores.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle el autor seleccionado
            EditarAutorController controller = loader.getController();
            controller.setAutor(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Autor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            cargarAutores(); // refrescar tabla

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error", "No se pudo abrir el formulario de edición.");
        }
    }

    @FXML
    private void eliminarAutor() {
        Autor seleccionado = tablaAutores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Eliminación", "Selecciona un autor para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eliminar Autor");
        confirm.setHeaderText("¿Estás seguro de eliminar a " + seleccionado.getNombre() + "?");
        confirm.setContentText("Esta acción no se puede deshacer.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                AutorDAO.eliminar(seleccionado.getId());
                cargarAutores();
            }
        });
    }

    private void abrirVentanaEmergente(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Autor");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Carga FXML", "No se pudo cargar la vista: " + ruta);
        }
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void buscarAutores() {
        String nombre = searchAutores.getText().trim();

        if (!nombre.isEmpty()) {
            listaAutores.setAll(AutorDAO.buscarPorNombre(nombre));
        } else {
            cargarAutores();
        }
    }

}
