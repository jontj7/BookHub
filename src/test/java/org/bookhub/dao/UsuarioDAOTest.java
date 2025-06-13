package org.bookhub.dao;

import org.bookhub.models.Usuario;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOTest {

    UsuarioDAO dao = new UsuarioDAO();

    @Test
    void guardar() {
        Usuario usuario = new Usuario();
        usuario.setNombres("Juan");
        usuario.setApellidos("Pérez");
        usuario.setUsuario("juanperez_test");
        usuario.setContrasena("1234");
        usuario.setIdRol(1); // Asegúrate de que este rol existe en la BD

        boolean guardado = dao.guardar(usuario);
        assertTrue(guardado, "El usuario debería haberse guardado correctamente.");
    }

    @Test
    void actualizar() {
        // Se asume que el usuario con ID 1 existe
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombres("Juan Modificado");
        usuario.setApellidos("Pérez Modificado");
        usuario.setUsuario("juanmod");
        usuario.setIdRol(1);
        usuario.setIdEstado(1);

        boolean actualizado = dao.actualizar(usuario);
        assertTrue(actualizado, "El usuario debería haberse actualizado correctamente.");
    }

    @Test
    void eliminar() {
        // Se asume que el usuario con ID 1 existe
        int idUsuario = 1;
        boolean eliminado = dao.eliminar(idUsuario);
        assertTrue(eliminado, "El usuario debería haberse marcado como eliminado (IdEstado = 2).");
    }

    @Test
    void listarTodos() {
        List<Usuario> usuarios = dao.listarTodos();
        assertNotNull(usuarios, "La lista no debería ser nula.");
        assertTrue(usuarios.size() >= 0, "La lista debería contener usuarios o estar vacía.");
    }
}
