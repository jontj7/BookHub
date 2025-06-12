package org.bookhub.models;

public class Autor {
    private int id;
    private String nombre;

    // Constructor vacío (necesario para usar setNombre())
    public Autor() {}

    // Constructor completo
    public Autor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return nombre;
    }
}
