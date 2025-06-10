package org.bookhub.controller.libro;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bookhub.dao.LibroDAO;
import org.bookhub.models.Libro;

public class EditLibroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtAutor;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtStock;

    @FXML private Button btnCancelar;
    @FXML private Button btnguardar;

    private Libro libro;

    public void setLibro(Libro libro) {
        this.libro = libro;
        txtNombre.setText(libro.getNombre());
        txtAutor.setText(libro.getNombreAutor());
        txtCategoria.setText(libro.getNombreCategoria());
        txtStock.setText(String.valueOf(libro.getStock()));
    }

    @FXML
    private void guardar() {
        libro.setNombre(txtNombre.getText());

        LibroDAO dao = new LibroDAO();

        // Obtener los IDs reales por nombre
        int idAutor = dao.obtenerIdAutorPorNombre(txtAutor.getText());
        int idCategoria = dao.obtenerIdCategoriaPorNombre(txtCategoria.getText());

        // Validar existencia
        if (idAutor == -1 || idCategoria == -1) {
            System.out.println("Autor o categoría no encontrados");
            return;
        }

        libro.setIdAutor(idAutor);
        libro.setIdCategoria(idCategoria);
        libro.setStock(Integer.parseInt(txtStock.getText()));

        dao.actualizarLibro(libro);

        Stage stage = (Stage) btnguardar.getScene().getWindow();
        stage.close();
    }



    @FXML
    private void cancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }


}
