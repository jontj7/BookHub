package org.bookhub.models;

import javafx.beans.property.*;

public class Libro {
    private final IntegerProperty idLibro = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final IntegerProperty idAutor = new SimpleIntegerProperty();
    private final StringProperty nombreAutor = new SimpleStringProperty();
    private final IntegerProperty idCategoria = new SimpleIntegerProperty();
    private final StringProperty nombreCategoria = new SimpleStringProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();
    private final StringProperty imagen = new SimpleStringProperty();

    // Constructor vacío (necesario para instanciar Libro sin argumentos)
    public Libro() {
    }

    // ID Libro
    public int getIdLibro() { return idLibro.get(); }
    public void setIdLibro(int value) { idLibro.set(value); }
    public IntegerProperty idLibroProperty() { return idLibro; }

    // Nombre
    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    // ID Autor
    public int getIdAutor() { return idAutor.get(); }
    public void setIdAutor(int value) { idAutor.set(value); }
    public IntegerProperty idAutorProperty() { return idAutor; }

    // Nombre Autor
    public String getNombreAutor() { return nombreAutor.get(); }
    public void setNombreAutor(String value) { nombreAutor.set(value); }
    public StringProperty nombreAutorProperty() { return nombreAutor; }

    // ID Categoria
    public int getIdCategoria() { return idCategoria.get(); }
    public void setIdCategoria(int value) { idCategoria.set(value); }
    public IntegerProperty idCategoriaProperty() { return idCategoria; }

    // Nombre Categoria
    public String getNombreCategoria() { return nombreCategoria.get(); }
    public void setNombreCategoria(String value) { nombreCategoria.set(value); }
    public StringProperty nombreCategoriaProperty() { return nombreCategoria; }

    // Stock
    public int getStock() { return stock.get(); }
    public void setStock(int value) { stock.set(value); }
    public IntegerProperty stockProperty() { return stock; }

    // Imagen
    public String getImagen() { return imagen.get(); }
    public void setImagen(String value) { imagen.set(value); }
    public StringProperty imagenProperty() { return imagen; }
}
