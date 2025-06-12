package org.bookhub.controller.prestamos;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bookhub.models.Prestamo;
import org.bookhub.models.Libro;
import org.bookhub.models.Usuario;
import org.bookhub.service.PrestamoService;

import java.time.LocalDate;

public class PrestamoEditController {

    @FXML
    private ComboBox<Libro> comboLibros;

    @FXML
    private ComboBox<Usuario> comboUsuarios;

    @FXML
    private DatePicker dateFechaPrestamo;

    @FXML
    private DatePicker dateFechaDevolucion;

    @FXML
    private Button btnActualizarPrestamo;

    private final PrestamoService prestamoService = new PrestamoService();

    private Prestamo prestamoActual; // El préstamo que estamos editando

    @FXML
    public void initialize() {
        comboLibros.getItems().addAll(prestamoService.obtenerLibrosActivos());
        comboUsuarios.getItems().addAll(prestamoService.obtenerUsuariosActivos());

        // El botón para actualizar llama al método actualizarPrestamo()
        btnActualizarPrestamo.setOnAction(e -> actualizarPrestamo());
    }

    /**
     * Este método debe llamarse desde quien abra esta ventana
     * para pasar el préstamo que se va a editar y cargar los datos.
     */
    public void setPrestamo(Prestamo prestamo) {
        this.prestamoActual = prestamo;

        // Seleccionar en los ComboBox los elementos correspondientes al préstamo actual
        comboLibros.setValue(obtenerLibroPorId(prestamo.getIdLibro()));
        comboUsuarios.setValue(obtenerUsuarioPorId(prestamo.getIdUsuario()));

        // Cargar las fechas
        dateFechaPrestamo.setValue(prestamo.getFechaPrestamo());
        dateFechaDevolucion.setValue(prestamo.getFechaDevolucion());
    }

    private Libro obtenerLibroPorId(int idLibro) {
        return comboLibros.getItems().stream()
                .filter(libro -> libro.getIdLibro() == idLibro)
                .findFirst()
                .orElse(null);
    }

    private Usuario obtenerUsuarioPorId(int idUsuario) {
        return comboUsuarios.getItems().stream()
                .filter(usuario -> usuario.getId() == idUsuario)
                .findFirst()
                .orElse(null);
    }

    private void actualizarPrestamo() {
        Libro libro = comboLibros.getValue();
        Usuario usuario = comboUsuarios.getValue();
        LocalDate fechaPrestamo = dateFechaPrestamo.getValue();
        LocalDate fechaDevolucion = dateFechaDevolucion.getValue();

        if (libro == null || usuario == null || fechaDevolucion == null) {
            mostrarAlerta("Completa todos los campos.");
            return;
        }

        prestamoActual.setIdLibro(libro.getIdLibro());
        prestamoActual.setIdUsuario(usuario.getId());
        prestamoActual.setFechaPrestamo(fechaPrestamo);
        prestamoActual.setFechaDevolucion(fechaDevolucion);
        // Puedes actualizar también el estado si lo necesitas
        // prestamoActual.setIdEstado(...);

        boolean exito = prestamoService.editarPrestamo(prestamoActual);
        if (exito) {
            mostrarAlerta("✅ Préstamo actualizado con éxito.");
            cerrarVentana();
        } else {
            mostrarAlerta("❌ Error al actualizar el préstamo.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnActualizarPrestamo.getScene().getWindow();
        stage.close();
    }
}
