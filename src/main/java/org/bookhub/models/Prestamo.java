package org.bookhub.models;

import java.time.LocalDate;

public class Prestamo {
    private int idPrestamo;
    private int idUsuario;
    private int idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String idEstado;  // Cambiado a idEstado para representar el estado como entero

    // Constructor vacío
    public Prestamo() {}

    // Constructor sin idPrestamo (para insertar)
    public Prestamo(int idUsuario, int idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String idEstado) {
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.idEstado = idEstado;
    }

    // Constructor completo con todos los campos
    public Prestamo(int idPrestamo, int idUsuario, int idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String idEstado) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.idEstado = idEstado;
    }

    // Getters y setters
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return idEstado;
    }

    public void setEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    private String nombreUsuario;
    private String nombreLibro;

    // getters y setters
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getNombreLibro() { return nombreLibro; }
    public void setNombreLibro(String nombreLibro) { this.nombreLibro = nombreLibro; }
}
