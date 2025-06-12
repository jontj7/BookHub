package org.bookhub.controller.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.bookhub.dao.LibroDAO;

import java.io.IOException;
import java.util.Map;

public class HomeController {

    @FXML private StackPane contenidoVista;

    @FXML private PieChart pieChartCategoria;
    @FXML private BarChart<String, Number> barChartTopLibros;
    @FXML private LineChart<String, Number> lineChartPrestamos;

    @FXML private Label labelTotalLibros;
    @FXML private Label labelPrestamosPendientes;
    @FXML private Label labelPrestamosDevueltos;

    @FXML private Label labelDashboard;

    private final LibroDAO libroDAO = new LibroDAO();

    @FXML
    private void initialize() {
        if (labelDashboard != null) {
            labelDashboard.setOnMouseClicked(event -> mostrarLibros());
            labelDashboard.setStyle("-fx-cursor: hand;");
        }

        cargarGraficas();
    }

    private void cargarGraficas() {
        // === PieChart: Categorías ===
        if (pieChartCategoria != null) {
            pieChartCategoria.getData().clear();
            Map<String, Integer> categorias = libroDAO.obtenerDistribucionPorCategoria();
            categorias.forEach((nombre, cantidad) ->
                    pieChartCategoria.getData().add(new PieChart.Data(nombre, cantidad))
            );

            // Total de libros
            int totalLibros = categorias.values().stream().mapToInt(Integer::intValue).sum();
            labelTotalLibros.setText(String.valueOf(totalLibros));
        }

        // === BarChart: Top libros ===
        if (barChartTopLibros != null) {
            barChartTopLibros.getData().clear();
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName("Top Libros");

            Map<String, Integer> topLibros = libroDAO.obtenerTopLibrosMasPrestados();
            topLibros.forEach((libro, prestamos) ->
                    serie.getData().add(new XYChart.Data<>(libro, prestamos))
            );
            barChartTopLibros.getData().add(serie);
        }

        // === LineChart: Préstamos por día ===
        if (lineChartPrestamos != null) {
            lineChartPrestamos.getData().clear();
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName("Préstamos por día");

            Map<String, Integer> prestamosPorDia = libroDAO.obtenerPrestamosPorDia();
            prestamosPorDia.forEach((dia, cantidad) ->
                    serie.getData().add(new XYChart.Data<>(dia, cantidad))
            );
            lineChartPrestamos.getData().add(serie);
        }

        // === KPIs de préstamos (Permitido / Retrasado) ===
        Map<String, Integer> estados = libroDAO.obtenerConteoPrestamosPorEstado();
        labelPrestamosPendientes.setText(String.valueOf(estados.getOrDefault("Retrasado", 0)));
        labelPrestamosDevueltos.setText(String.valueOf(estados.getOrDefault("Permitido", 0)));
    }

    // === Navegación ===
    @FXML private void irAInicio() {
        cargarVista("/org/bookhub/view/Graficas/Graficas.fxml");
    }

    @FXML private void mostrarLibros() {
        cargarVista("/org/bookhub/view/dashboard.fxml");
    }

    @FXML private void mostrarPrestamos() {
        cargarVista("/org/bookhub/view/prestamosview/prestamohome.fxml");
    }

    @FXML private void mostrarAutores() {
        cargarVista("/org/bookhub/view/autoresviews/autoreshome.fxml");
    }

    @FXML private void mostrarUsuarios() {
        cargarVista("/org/bookhub/view/usuariosviews/usuariohome.fxml");
    }

    @FXML private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/bookhub/view/login.fxml"));
            Stage stage = (Stage) contenidoVista.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar sesión");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVista(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent vista = loader.load();
            contenidoVista.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
