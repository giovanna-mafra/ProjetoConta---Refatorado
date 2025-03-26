package conta.controller;

import conta.model.ContaModel;
import conta.model.UsuarioModel;
import conta.model.dao.ContaDAO;
import conta.model.dao.UsuarioDAO;

public class CadastroController {
    private UsuarioDAO usuarioDAO;
    private ContaDAO contaDAO;

    public CadastroController(UsuarioDAO usuarioDAO, ContaDAO contaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.contaDAO = contaDAO;
    }

    public void cadastrarUsuario(String nome, String email, String senha, String tipoConta, Double saldo) {
        ContaModel conta = new ContaModel(0, tipoConta, saldo);  // Criando a conta
        UsuarioModel usuario = new UsuarioModel(0, nome, email, senha, conta);  // Associando o usuário com a conta

        usuarioDAO.cadastrarUsuario(usuario);  // Chamando o DAO para cadastrar
    }

}
