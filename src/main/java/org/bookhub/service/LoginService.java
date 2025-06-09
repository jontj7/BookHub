package org.bookhub.service;

import org.bookhub.dao.UsuarioDAO;
import org.bookhub.models.Usuario;

public class LoginService {

    private final UsuarioDAO usuarioDAO;

    public LoginService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String username, String password) {
        return usuarioDAO.obtenerPorCredenciales(username, password);
    }
}
