package conta.controller;

import conta.model.CategoriaModel;
import conta.model.TransacaoModel;
import conta.model.UsuarioModel;
import conta.model.ContaModel;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.TransacaoDAO;
import conta.model.dao.UsuarioDAO;
import conta.model.dao.ContaDAO;

public class AtualizarController {

    private UsuarioDAO usuarioDAO;
    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;



    public AtualizarController(UsuarioDAO usuarioDAO, ContaDAO contaDAO, CategoriaDAO categoriaDAO, TransacaoDAO transacaoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.contaDAO = contaDAO;
        this.categoriaDAO = categoriaDAO;
        this.transacaoDAO = transacaoDAO;


    }

    public boolean atualizarUsuario(int usuarioId, String nome, String email, String senha, String tipoConta, double saldo) {

        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        ContaModel conta = usuario.getConta();
        conta.setTipoConta(tipoConta);
        conta.setSaldo(saldo);

        boolean usuarioAtualizado = usuarioDAO.atualizarUsuario(usuario);
        boolean contaAtualizada = contaDAO.atualizarConta(conta);

        return usuarioAtualizado && contaAtualizada;
    }

    public boolean atualizarCategoria(int usuarioId, String senha, String novoTipoCategoria) {

        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }


        if (!usuario.getSenha().equals(senha)) {
            System.out.println("Senha incorreta.");
            return false;
        }


        CategoriaModel categoria = categoriaDAO.buscarCategoriaPorUsuarioId(usuarioId);
        if (categoria == null) {
            System.out.println("Categoria não encontrada para o usuário.");
            return false;
        }

        categoria.setTipoCategoria(novoTipoCategoria);

        boolean categoriaAtualizada = categoriaDAO.atualizarCategoria(categoria);

        return categoriaAtualizada;
    }

    public boolean atualizarTransacao(int usuarioId, String senha, int transacaoId, double novoValor, String novoTipoTransacao) {

        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }

        if (!usuario.getSenha().equals(senha)) {
            System.out.println("Senha incorreta.");
            return false;
        }

        TransacaoModel transacao = transacaoDAO.buscarTransacaoPorId(transacaoId);
        if (transacao == null) {
            System.out.println("Transação não encontrada.");
            return false;
        }

        transacao.setValor(novoValor);
        transacao.setTipoTransacao(novoTipoTransacao);

        boolean transacaoAtualizada = transacaoDAO.atualizarTransacao(transacao);

        transacaoDAO.atualizarSaldo(usuario, novoValor);

        return transacaoAtualizada;
    }
}
