package conta.controller;

import conta.model.CategoriaModel;
import conta.model.UsuarioModel;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.ContaDAO;
import conta.model.dao.TransacaoDAO;
import conta.model.dao.UsuarioDAO;
import java.util.List;

public class ListarController {
    private UsuarioDAO usuarioDAO;
    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;

    public ListarController(UsuarioDAO usuarioDAO, ContaDAO contaDAO, CategoriaDAO categoriaDAO, TransacaoDAO transacaoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.contaDAO = contaDAO;
        this.categoriaDAO = categoriaDAO;
        this.transacaoDAO = transacaoDAO;    }


    // Método que retorna a lista de usuários
    public List<UsuarioModel> listarUsuarios() {
        return usuarioDAO.buscarTodosUsuarios();
    }

    public List<CategoriaModel> listarCategorias() {
        // Chamando o DAO para buscar todas as categorias
        return categoriaDAO.listarCategorias();
    }
}
