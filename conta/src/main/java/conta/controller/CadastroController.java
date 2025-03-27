package conta.controller;

import conta.model.CategoriaModel;
import conta.model.ContaModel;
import conta.model.TransacaoModel;
import conta.model.UsuarioModel;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.ContaDAO;
import conta.model.dao.TransacaoDAO;
import conta.model.dao.UsuarioDAO;

public class CadastroController {
    private UsuarioDAO usuarioDAO;
    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;
    private TransacaoDAO transacaoDAO;


    public CadastroController(UsuarioDAO usuarioDAO, ContaDAO contaDAO, CategoriaDAO categoriaDAO, TransacaoDAO transacaoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.contaDAO = contaDAO;
        this.categoriaDAO = categoriaDAO;
        this.transacaoDAO = transacaoDAO;

    }

    public void cadastrarUsuario(String nome, String email, String senha, String tipoConta, Double saldo) {
        ContaModel conta = new ContaModel(0, tipoConta, saldo);  // Criando a conta
        UsuarioModel usuario = new UsuarioModel(0, nome, email, senha, conta);  // Associando o usuário com a conta

        usuarioDAO.cadastrarUsuario(usuario);  // Chamando o DAO para cadastrar
    }

    public void cadastrarCategoria(String tipoCategoria, int usuarioId, String senha) {
        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId); // Buscar o usuário pelo ID
        if (usuario != null && usuario.getSenha().equals(senha)) {
            CategoriaModel categoria = new CategoriaModel(0, tipoCategoria, usuario);  // Criando a categoria e associando ao usuário
            categoriaDAO.cadastrarCategoria(categoria);  // Chamando o DAO para cadastrar
            System.out.println("Categoria cadastrada com sucesso!");
        } else {
            System.out.println("Senha incorreta ou usuário não encontrado.");
        }
    }

    public void cadastrarTransacao(double valor, String tipoTransacao, int usuarioId, String senha) {
        UsuarioModel usuario = usuarioDAO.buscarUsuarioPorId(usuarioId);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            TransacaoModel transacao = new TransacaoModel(0, valor, tipoTransacao, usuario);
            transacaoDAO.cadastrarTransacao(transacao);

            // Atualizando o saldo do usuário conforme o tipo de transação
            if ("Despesa".equalsIgnoreCase(tipoTransacao)) {
                usuario.getConta().setSaldo(usuario.getConta().getSaldo() - valor);  // Descontando
            } else if ("Receita".equalsIgnoreCase(tipoTransacao)) {
                usuario.getConta().setSaldo(usuario.getConta().getSaldo() + valor);  // Acrescentando
            }

            // Atualiza o saldo no banco de dados
            transacaoDAO.atualizarSaldo(usuario, usuario.getConta().getSaldo());
            System.out.println("Transação registrada com sucesso!");
        } else {
            System.out.println("Senha incorreta ou usuário não encontrado.");
        }
    }

}
