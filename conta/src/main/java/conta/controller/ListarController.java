package conta.controller;

import conta.model.UsuarioModel;
import conta.model.dao.UsuarioDAO;
import java.util.List;

public class ListarController {
    private UsuarioDAO usuarioDAO;

    public ListarController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Método que retorna a lista de usuários
    public List<UsuarioModel> listarUsuarios() {
        return usuarioDAO.buscarTodosUsuarios();
    }
}
