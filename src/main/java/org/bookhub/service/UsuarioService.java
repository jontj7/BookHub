package org.bookhub.service;

import org.bookhub.dao.UsuarioDAO;
import org.bookhub.models.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean registrarUsuario(Usuario usuario) {
        // Validar si el usuario ya existe (opcional)
        if (usuarioDAO.existeUsuario(usuario.getNombres(), usuario.getApellidos())) {
            return false;
        }

        // Guardar usuario nuevo
        return usuarioDAO.guardar(usuario);
    }
}
