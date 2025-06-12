package org.bookhub.controller.prestamos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bookhub.controller.autores.EditarAutorController;
import org.bookhub.dao.AutorDAO;
import org.bookhub.dao.PrestamoDAO;
import org.bookhub.models.Autor;
import org.bookhub.models.Prestamo;
import org.bookhub.service.PrestamoService;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

import java.util.List;

import java.io.IOException;


public class PrestamoController {

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private TableView<Prestamo> tablaPrestamos;
    private ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Prestamo, Integer> colIdPrestamo;

    @FXML
    private TableColumn<Prestamo, Integer> colIdUsuario;

    @FXML
    private TableColumn<Prestamo, String> colNombreCompleto;

    @FXML
    private TableColumn<Prestamo, String> colNombreLibro;

    @FXML
    private TableColumn<Prestamo, Integer> colIdLibro;

    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaPrestamo;

    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaDevolucion;

    @FXML
    private TableColumn<Prestamo, Integer> colIdEstado;

    @FXML
    private PrestamoService prestamoService = new PrestamoService();



    @FXML
    private void buscarPrestamosPorFechas() {
        LocalDate inicio = dpFechaInicio.getValue();
        LocalDate fin = dpFechaFin.getValue();

        if (inicio != null && fin != null && !inicio.isAfter(fin)) {
            List<Prestamo> resultados = PrestamoDAO.buscarPorFechas(inicio, fin);
            listaPrestamos.setAll(resultados);
            tablaPrestamos.setItems(listaPrestamos);
        } else {
            mostrarError("Fechas inválidas", "Asegúrate de que ambas fechas estén seleccionadas y que la fecha de inicio no sea después de la final.");
        }
    }

    @FXML
    public void initialize() {
        colIdPrestamo.setCellValueFactory(new PropertyValueFactory<>("idPrestamo"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colIdLibro.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colNombreLibro.setCellValueFactory(new PropertyValueFactory<>("nombreLibro"));        // nueva columna
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
        colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
        colIdEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarDatos();
    }

    private void cargarDatos() {
        // Obtener la lista desde el servicio
        ObservableList<Prestamo> prestamos = FXCollections.observableArrayList(prestamoService.listarPrestamos());

        // Llenar la tabla
        tablaPrestamos.setItems(prestamos);
    }

    @FXML
    private void abrirFormularioNuevoPrestamo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/prestamosview/prestamoNuevo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nuevo prestamo");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            mostrarError("No se pudo abrir el formulario de nuevo libro", e.getMessage());
        }
    }

    @FXML
    private void abrirFormularioEditar() {
        Prestamo seleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Edición", "Debes seleccionar un autor para editar.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bookhub/view/prestamosview/prestamoedit.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle el autor seleccionado
            PrestamoEditController controller = loader.getController();
            controller.setPrestamo(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Autor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            cargarDatos(); // refrescar tabla

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error", "No se pudo abrir el formulario de edición.");
        }


    }

    @FXML
    private void eliminarPrestamo() {
        Prestamo seleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Eliminación", "Selecciona un prestamo para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eliminar Autor");
        confirm.setHeaderText("¿Estás seguro de eliminar a " + seleccionado.getFechaPrestamo() + "?");
        confirm.setContentText("Esta acción no se puede deshacer.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                PrestamoDAO.eliminar(seleccionado.getIdPrestamo());
                cargarDatos();
            }
        });
    }


    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public PrestamoController() {
        this.prestamoService = new PrestamoService();
    }

    // Insertar un nuevo préstamo
    public boolean registrarPrestamo(Prestamo prestamo) {
        return prestamoService.insertarPrestamo(prestamo);
    }


    // Inactivar un préstamo (cambia estado a 2)
    public boolean inactivarPrestamo(int idPrestamo) {
        return prestamoService.inactivarPrestamo(idPrestamo);
    }

    // Obtener préstamo por ID
    public Prestamo obtenerPrestamoPorId(int id) {
        return prestamoService.obtenerPrestamoPorId(id);
    }

    // Obtener todos los préstamos
    public List<Prestamo> listarPrestamos() {
        return prestamoService.listarPrestamos();
    }

    // Buscar préstamos por filtro de nombre de libro y estado
    public List<Prestamo> buscarPrestamos(String nombreLibro, int estado) {
        return prestamoService.buscarPrestamosConFiltro(nombreLibro, estado);
    }



}
