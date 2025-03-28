package conta.controller;

import conta.model.UsuarioModel;
import conta.model.ContaModel;
import conta.model.dao.UsuarioDAO;
import conta.model.dao.ContaDAO;

public class AtualizarController {

    private UsuarioDAO usuarioDAO;
    private ContaDAO contaDAO;

    public AtualizarController(UsuarioDAO usuarioDAO, ContaDAO contaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.contaDAO = contaDAO;
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
}
