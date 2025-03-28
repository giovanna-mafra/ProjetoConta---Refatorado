package conta.controller;

import conta.model.CategoriaModel;
import conta.model.UsuarioModel;
import conta.model.ContaModel;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.UsuarioDAO;
import conta.model.dao.ContaDAO;

public class AtualizarController {

    private UsuarioDAO usuarioDAO;
    private ContaDAO contaDAO;
    private CategoriaDAO categoriaDAO;


    public AtualizarController(UsuarioDAO usuarioDAO, ContaDAO contaDAO, CategoriaDAO categoriaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.contaDAO = contaDAO;
        this.categoriaDAO = categoriaDAO;

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
}
