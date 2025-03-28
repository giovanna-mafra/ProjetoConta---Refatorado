package conta.controller;

import conta.model.CategoriaModel;
import conta.model.TransacaoModel;
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


    public List<UsuarioModel> listarUsuarios() {
        return usuarioDAO.buscarTodosUsuarios();
    }

    public List<CategoriaModel> listarCategorias() {

        return categoriaDAO.listarCategorias();
    }

    public List<TransacaoModel> listarTransacoesPorUsuario(int usuarioId, String senha) {

        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            return transacaoDAO.listarTransacoesPorUsuario(usuarioId);
        } else {
            throw new RuntimeException("Senha incorreta ou usuário não encontrado.");
        }
    }

}
