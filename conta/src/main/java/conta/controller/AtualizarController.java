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
        // Verifica se o usuário existe
        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }

        // Atualiza os dados do usuário
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        // Atualiza os dados da conta
        ContaModel conta = usuario.getConta();
        conta.setTipoConta(tipoConta);
        conta.setSaldo(saldo);

        // Atualiza no banco
        boolean usuarioAtualizado = usuarioDAO.atualizarUsuario(usuario);
        boolean contaAtualizada = contaDAO.atualizarConta(conta);

        return usuarioAtualizado && contaAtualizada;
    }

    public boolean atualizarCategoria(int usuarioId, String senha, String novoTipoCategoria) {
        // Verifica se o usuário existe
        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }

        // Verifica se a senha está correta
        if (!usuario.getSenha().equals(senha)) {
            System.out.println("Senha incorreta.");
            return false;
        }

        // Atualiza a categoria do usuário
        CategoriaModel categoria = categoriaDAO.buscarCategoriaPorUsuarioId(usuarioId);
        if (categoria == null) {
            System.out.println("Categoria não encontrada para o usuário.");
            return false;
        }

        // Atualiza o tipo de categoria
        categoria.setTipoCategoria(novoTipoCategoria);

        // Atualiza a categoria no banco de dados
        boolean categoriaAtualizada = categoriaDAO.atualizarCategoria(categoria);

        return categoriaAtualizada;
    }

    public boolean atualizarTransacao(int usuarioId, String senha, int transacaoId, double novoValor, String novoTipoTransacao) {
        // Verifica se o usuário existe
        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }

        // Verifica se a senha está correta
        if (!usuario.getSenha().equals(senha)) {
            System.out.println("Senha incorreta.");
            return false;
        }

        // Verifica se a transação existe
        TransacaoModel transacao = transacaoDAO.buscarTransacaoPorId(transacaoId);
        if (transacao == null) {
            System.out.println("Transação não encontrada.");
            return false;
        }

        // Atualiza os dados da transação
        transacao.setValor(novoValor);
        transacao.setTipoTransacao(novoTipoTransacao);

        // Atualiza a transação no banco de dados
        boolean transacaoAtualizada = transacaoDAO.atualizarTransacao(transacao);

        // Atualiza o saldo do usuário caso necessário
        transacaoDAO.atualizarSaldo(usuario, novoValor);

        return transacaoAtualizada;
    }
}
