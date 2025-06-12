
package org.bookhub.service;
import org.bookhub.dao.PrestamoDAO;
import org.bookhub.models.Libro;
import org.bookhub.models.Prestamo;
import org.bookhub.models.Usuario;

import java.util.List;

public class PrestamoService {

    private final PrestamoDAO prestamoDAO;

    public PrestamoService() {
        this.prestamoDAO = new PrestamoDAO();
    }

    // Insertar un nuevo préstamo
    public boolean insertarPrestamo(Prestamo prestamo) {
        // Aquí puedes agregar validaciones si necesitas
        return prestamoDAO.insertar(prestamo);
    }

    // Editar un préstamo existente
    public boolean editarPrestamo(Prestamo prestamo) {
        return prestamoDAO.editar(prestamo);
    }

    // Inactivar un préstamo (cambia estado a 2)
    public boolean inactivarPrestamo(int idPrestamo) {
        return prestamoDAO.inactivar(idPrestamo);
    }

    // Buscar un préstamo por ID
    public Prestamo obtenerPrestamoPorId(int id) {
        return prestamoDAO.buscarPorId(id);
    }

    // Obtener todos los préstamos
    public List<Prestamo> listarPrestamos() {
        return prestamoDAO.buscarTodos();
    }

    // Buscar préstamos con filtro por nombre de libro y estado
    public List<Prestamo> buscarPrestamosConFiltro(String nombreLibro, int estado) {
        return prestamoDAO.buscarConFiltro(nombreLibro, estado);
    }

    public List<Usuario> obtenerUsuariosActivos() {
        return prestamoDAO.obtenerUsuariosActivos();
    }

    public List<Libro> obtenerLibrosActivos() {
        return prestamoDAO.obtenerLibrosActivos();
    }
}
