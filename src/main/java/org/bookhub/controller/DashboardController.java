package org.bookhub.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bookhub.controller.libro.EditLibroController;
import org.bookhub.dao.LibroDAO;
import org.bookhub.models.Libro;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro, Integer> colId;
    @FXML private TableColumn<Libro, String> colNombre;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, Integer> colStock;

    @FXML private TextField labelingresarlibros;
    @FXML private ComboBox<String> comboAutores;
    @FXML private ComboBox<String> comboCategorias;
    @FXML private Button btnlogout;




    private ObservableList<Libro> listaLibros;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        cargarLibros();
        configurarFiltros();

    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void cargarLibros() {
        LibroDAO dao = new LibroDAO();
        listaLibros = FXCollections.observableArrayList(dao.listarLibrosConDetalles());
        tablaLibros.setItems(listaLibros);

        // Llena ComboBox únicos
        ObservableList<String> autores = FXCollections.observableArrayList();
        ObservableList<String> categorias = FXCollections.observableArrayList();

        for (Libro libro : listaLibros) {
            if (!autores.contains(libro.getNombreAutor())) {
                autores.add(libro.getNombreAutor());
            }
            if (!categorias.contains(libro.getNombreCategoria())) {
                categorias.add(libro.getNombreCategoria());
            }
        }

        comboAutores.setItems(autores);
        comboCategorias.setItems(categorias);
    }

    private void configurarFiltros() {
        FilteredList<Libro> filtro = new FilteredList<>(listaLibros, p -> true);

        labelingresarlibros.textProperty().addListener((obs, oldVal, newVal) -> {
            filtro.setPredicate(libro -> {
                String texto = newVal.toLowerCase();
                return libro.getNombre().toLowerCase().contains(texto);
            });
        });

        comboAutores.setOnAction(e -> {
            String autor = comboAutores.getValue();
            filtro.setPredicate(libro -> autor == null || autor.isEmpty() || libro.getNombreAutor().equals(autor));
        });

        comboCategorias.setOnAction(e -> {
            String categoria = comboCategorias.getValue();
            filtro.setPredicate(libro -> categoria == null || categoria.isEmpty() || libro.getNombreCategoria().equals(categoria));
        });

        tablaLibros.setItems(filtro);
    }

    // Abrir formulario "Nuevo Libro"
    @FXML private void abrirFormularioNuevoLibro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/dashoptions/nuevoLibro.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nuevo Libro");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            cargarLibros(); // Refrescar después de cerrar
        } catch (IOException e) {
            mostrarError("No se pudo abrir el formulario de nuevo libro", e.getMessage());
        }
    }

    @FXML
    private void abrirFormularioEdicion() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Edición", "Debes seleccionar un libro para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/dashoptions/edit.fxml"));
            Parent root = loader.load();

            EditLibroController controller = loader.getController();
            controller.setLibro(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Libro");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            cargarLibros(); // refrescar
        } catch (IOException e) {
            mostrarError("Error al abrir el formulario de edición", e.getMessage());
        }
    }


    @FXML private void confirmarEliminacionLibro() {
        System.out.println("Eliminar libro...");
    }

    @FXML private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/login.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) btnlogout.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Iniciar sesión");
            currentStage.show();
        } catch (IOException e) {
            mostrarError("Error al cerrar sesión", e.getMessage());
        }
    }

    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    // Métodos vacíos de navegación
    @FXML private void abrirVistaLibros() {}
    @FXML private void abrirVistaPrestamos() {}
    @FXML private void abrirVistaAutores() {}
    @FXML private void abrirVistaUsuarios() {}
}
