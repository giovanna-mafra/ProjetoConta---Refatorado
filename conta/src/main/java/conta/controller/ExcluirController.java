package conta.controller;

import conta.model.UsuarioModel;
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

        if (usuarioDAO.buscarUsuarioPorId(usuarioId) == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }


        if (!usuarioDAO.buscarUsuarioPorId(usuarioId).getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta.");
        }


        categoriaDAO.excluirPorUsuarioId(usuarioId);


        transacaoDAO.excluirPorUsuarioId(usuarioId);


        contaDAO.excluirPorUsuarioId(usuarioId);

        usuarioDAO.excluir(usuarioId);

        System.out.println("Usuário, sua conta, categorias e transações foram excluídos com sucesso!");
    }

    public boolean excluirCategoria(int categoriaId, int usuarioId, String senha) {

        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            System.out.println("Senha incorreta ou usuário não encontrado.");
            return false;
        }

        return categoriaDAO.excluir(categoriaId);
    }

    public boolean excluirTransacao(int transacaoId, int usuarioId, String senha) {

        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            return false;
        }

        return transacaoDAO.excluirTransacao(transacaoId);
    }

}
