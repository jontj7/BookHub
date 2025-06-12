package org.bookhub.controller.prestamos;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bookhub.models.Autor;
import org.bookhub.models.Prestamo;
import org.bookhub.dao.PrestamoDAO;
import org.bookhub.models.Usuario;
import org.bookhub.service.PrestamoService;

import org.bookhub.models.Libro;


import java.time.LocalDate;

public class PrestamoEditController {

    @FXML
    private ComboBox<Usuario> CbxIdUsuario;
    @FXML
    private ComboBox<Libro> CbxIdLibro;
    @FXML
    private DatePicker dateFechaPrestamo;
    @FXML
    private DatePicker dateFechaDevolucion;
    @FXML
    private Prestamo prestamoActual;

    public void setPrestamo(Prestamo prestamo) {
        this.prestamoActual = prestamo;

        int idUsuario = prestamo.getIdUsuario();
        for (Usuario u : CbxIdUsuario.getItems()) {
            if (u.getId() == idUsuario) {
                CbxIdUsuario.setValue(u);
                break;
            }
        }

        // Buscar libro por ID en el ComboBox y seleccionarlo
        int idLibro = prestamo.getIdLibro();
        for (Libro l : CbxIdLibro.getItems()) {
            if (l.getIdLibro() == idLibro) {
                CbxIdLibro.setValue(l);
                break;
            }
        }
        dateFechaPrestamo.setValue(prestamo.getFechaPrestamo());
        dateFechaDevolucion.setValue(prestamo.getFechaDevolucion());
    }

    private final PrestamoService prestamoService = new PrestamoService();
    @FXML
    public void initialize() {
        CbxIdUsuario.getItems().addAll(prestamoService.obtenerUsuariosActivos());
        CbxIdLibro.getItems().addAll(prestamoService.obtenerLibrosActivos());
    }

    @FXML
    private void guardarCambios() {
        try {
            Usuario usuarioSeleccionado = CbxIdUsuario.getValue();
            Libro libroSeleccionado = CbxIdLibro.getValue();

            if (usuarioSeleccionado == null || libroSeleccionado == null) {
                mostrarAlerta("Campos incompletos", "Debe seleccionar un usuario y un libro.");
                return;
            }

            int idUsuario = usuarioSeleccionado.getId();
            int idLibro = libroSeleccionado.getIdLibro();

            LocalDate fechaPrestamo = dateFechaPrestamo.getValue();
            LocalDate fechaDevolucion = dateFechaDevolucion.getValue();

            if (fechaPrestamo == null || fechaDevolucion == null) {
                mostrarAlerta("Campos incompletos", "Todos los campos deben estar llenos.");
                return;
            }

            prestamoActual.setIdUsuario(idUsuario);
            prestamoActual.setIdLibro(idLibro);
            prestamoActual.setFechaPrestamo(fechaPrestamo);
            prestamoActual.setFechaDevolucion(fechaDevolucion);

            PrestamoDAO.editar(prestamoActual);
            cerrarVentana();

        } catch (NumberFormatException e) {
            mostrarAlerta("Formato incorrecto", "ID de usuario y libro deben ser números.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron guardar los cambios.");
        }
    }


    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) CbxIdLibro.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
