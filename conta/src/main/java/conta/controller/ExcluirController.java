package conta.controller;

import conta.model.dao.CategoriaDAO;
import conta.model.dao.TransacaoDAO;
import conta.model.dao.UsuarioDAO;
import conta.model.dao.ContaDAO;

public class ExcluirController {

    private UsuarioDAO usuarioDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;
    private ContaDAO contaDAO;

    public ExcluirController(UsuarioDAO usuarioDAO, ContaDAO contaDAO, CategoriaDAO categoriaDAO, TransacaoDAO transacaoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.contaDAO = contaDAO;
        this.categoriaDAO = categoriaDAO;
        this.transacaoDAO = transacaoDAO;
    }


    public void excluirUsuario(int usuarioId, String senha) {
        // Verificar se o usuário existe
        if (usuarioDAO.buscarUsuarioPorId(usuarioId) == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        // Verificar se a senha está correta
        if (!usuarioDAO.buscarUsuarioPorId(usuarioId).getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta.");
        }

        // Excluir categorias associadas ao usuário
        categoriaDAO.excluirPorUsuarioId(usuarioId);

        // Excluir transações associadas ao usuário
        transacaoDAO.excluirPorUsuarioId(usuarioId);

        // Excluir a conta associada ao usuário
        contaDAO.excluirPorUsuarioId(usuarioId);

        // Excluir o usuário
        usuarioDAO.excluir(usuarioId);

        System.out.println("Usuário, sua conta, categorias e transações foram excluídos com sucesso!");
    }
}
