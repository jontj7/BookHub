package org.bookhub.controller.prestamos;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bookhub.models.Prestamo;
import org.bookhub.models.Libro;
import org.bookhub.models.Usuario;
import org.bookhub.service.PrestamoService;

import java.time.LocalDate;

public class PrestamoNuevoController {

    @FXML
    private ComboBox<Libro> comboLibros;

    @FXML
    private ComboBox<Usuario> comboUsuarios;

    @FXML
    private DatePicker dateFechaPrestamo;

    @FXML
    private DatePicker dateFechaDevolucion;

    @FXML
    private Button btnAgregarPrestamo;

    private final PrestamoService prestamoService = new PrestamoService();

    @FXML
    public void initialize() {
        comboLibros.getItems().addAll(prestamoService.obtenerLibrosActivos());
        comboUsuarios.getItems().addAll(prestamoService.obtenerUsuariosActivos());

        dateFechaPrestamo.setValue(LocalDate.now());

        btnAgregarPrestamo.setOnAction(e -> agregarPrestamo());
    }

    private void agregarPrestamo() {
        Libro libro = comboLibros.getValue();
        Usuario usuario = comboUsuarios.getValue();
        LocalDate fechaPrestamo = dateFechaPrestamo.getValue();
        LocalDate fechaDevolucion = dateFechaDevolucion.getValue();

        if (libro == null || usuario == null || fechaDevolucion == null) {
            mostrarAlerta("Completa todos los campos.");
            return;
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setIdLibro(libro.getIdLibro());
        prestamo.setIdUsuario(usuario.getId());
        prestamo.setFechaPrestamo(fechaPrestamo);     // LocalDate directo
        prestamo.setFechaDevolucion(fechaDevolucion); // LocalDate directo
        prestamo.setEstado("1");


        boolean exito = prestamoService.insertarPrestamo(prestamo);
        if (exito) {
            mostrarAlerta("✅ Préstamo registrado con éxito.");
            cerrarVentana();
        } else {
            mostrarAlerta("❌ Error al registrar el préstamo.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnAgregarPrestamo.getScene().getWindow();
        stage.close();
    }
}
