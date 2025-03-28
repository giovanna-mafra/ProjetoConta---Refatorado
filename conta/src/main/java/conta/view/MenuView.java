package conta.view;

import conta.controller.AtualizarController;
import conta.controller.CadastroController;
import conta.controller.ExcluirController;
import conta.model.CategoriaModel;
import conta.model.TransacaoModel;
import conta.model.UsuarioModel;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.ContaDAO;
import conta.model.dao.TransacaoDAO;
import conta.model.dao.UsuarioDAO;
import conta.controller.ListarController;


import java.util.List;
import java.util.Scanner;

public class MenuView {
    private Scanner scanner = new Scanner(System.in);
    private CadastroController cadastroController;
    private ListarController listarController;
    private ExcluirController excluirController;
    private AtualizarController atualizarController;



    public MenuView() {

        this.cadastroController = new CadastroController(new UsuarioDAO(), new ContaDAO(), new CategoriaDAO(), new TransacaoDAO());
        this.listarController = new ListarController(new UsuarioDAO(), new ContaDAO(), new CategoriaDAO(), new TransacaoDAO());
        this.excluirController = new ExcluirController(new UsuarioDAO(), new ContaDAO(), new CategoriaDAO(), new TransacaoDAO());
        this.atualizarController = new AtualizarController(new UsuarioDAO(), new ContaDAO(), new CategoriaDAO(), new TransacaoDAO());


    }

    public void mostrarMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Excluir");
            System.out.println("4. Atualizar");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    excluir();
                    break;
                case 4:
                    atualizar();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\nEscolha o que deseja cadastrar:");
        System.out.println("1. Usuário");
        System.out.println("2. Categoria");
        System.out.println("3. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                cadastrarUsuario();
                break;
            case 2:
                cadastrarCategoria();
                break;
            case 3:
                cadastrarTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void cadastrarUsuario() {
        System.out.println("\nCadastro de Usuário");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1. Corrente");
        System.out.println("2. Poupança");
        int tipoContaOpcao = scanner.nextInt();
        scanner.nextLine();

        String tipoConta = tipoContaOpcao == 1 ? "Corrente" : "Poupança";

        System.out.print("Informe o saldo inicial: R$ ");
        double saldoInicial = scanner.nextDouble();


        cadastroController.cadastrarUsuario(nome, email, senha, tipoConta, saldoInicial);
        System.out.println("Cadastro realizado com sucesso!");
    }

    private void cadastrarCategoria() {
        System.out.println("\nCadastro de Categoria");

        System.out.print("Tipo de Categoria: ");
        String tipoCategoria = scanner.nextLine();

        System.out.print("Informe o ID do Usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe a senha do usuário: ");
        String senha = scanner.nextLine();

        cadastroController.cadastrarCategoria(tipoCategoria, usuarioId, senha);
    }

    private void cadastrarTransacao() {
        System.out.println("\nCadastro de Transação");

        System.out.print("Informe o valor: R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Escolha o tipo de transação:");
        System.out.println("1. Despesa");
        System.out.println("2. Receita");
        int tipoTransacaoOpcao = scanner.nextInt();
        scanner.nextLine();

        String tipoTransacao = tipoTransacaoOpcao == 1 ? "Despesa" : "Receita";

        System.out.print("Informe o ID do Usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe a senha do usuário: ");
        String senha = scanner.nextLine();


        cadastroController.cadastrarTransacao(valor, tipoTransacao, usuarioId, senha);
    }

    private void listar() {
        System.out.println("\nEscolha o que deseja listar:");
        System.out.println("1. Usuário");
        System.out.println("2. Categoria");
        System.out.println("3. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                listarUsuarios();
                break;
            case 2:
                listarCategorias();
                break;
            case 3:
                listarTransacoes();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void listarUsuarios() {
        System.out.println("\nLista de Usuários:");

        List<UsuarioModel> usuarios = listarController.listarUsuarios();

        for (UsuarioModel usuario : usuarios) {
            System.out.print("ID: " + usuario.getId() + " | ");
            System.out.print("Nome: " + usuario.getNome() + " | ");
            System.out.print("Email: " + usuario.getEmail() + " | ");
            System.out.print("Tipo de Conta: " + usuario.getConta().getTipoConta() + " | ");
            System.out.print("Saldo: R$ " + usuario.getConta().getSaldo());
            System.out.println();
        }
    }

    private void listarCategorias() {
        System.out.println("\nLista de Categorias:");

        ListarController listarController = new ListarController(new UsuarioDAO(), new ContaDAO(), new CategoriaDAO(), new TransacaoDAO());
        List<CategoriaModel> categorias = listarController.listarCategorias();

        for (CategoriaModel categoria : categorias) {
            System.out.print("ID: " + categoria.getId() + " | ");
            System.out.print("Tipo de Categoria: " + categoria.getTipoCategoria() + " | ");
            System.out.print("Usuário ID: " + categoria.getUsuario().getId() + " | ");
            System.out.print("Nome do Usuário: " + categoria.getUsuario().getNome() + " | ");
            System.out.print("Tipo de Conta: " + categoria.getUsuario().getConta().getTipoConta() + " | ");
            System.out.print("Saldo: R$ " + categoria.getUsuario().getConta().getSaldo());
            System.out.println();
        }
    }

    private void listarTransacoes() {
        System.out.println("\nLista de Transações");

        System.out.print("Informe o ID do Usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe a senha do usuário: ");
        String senha = scanner.nextLine();

        try {

            List<TransacaoModel> transacoes = listarController.listarTransacoesPorUsuario(usuarioId, senha);

            if (transacoes.isEmpty()) {
                System.out.println("Nenhuma transação encontrada para o usuário.");
            } else {
                for (TransacaoModel transacao : transacoes) {
                    System.out.println("ID: " + transacao.getId() + " | Tipo: " + transacao.getTipoTransacao() + " | Valor: R$ " + transacao.getValor());
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\nEscolha o que deseja excluir:");
        System.out.println("1. Usuário");
        System.out.println("2. Categoria");
        System.out.println("3. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                excluirUsuario();
                break;
            case 2:
                excluirCategoria();
                break;
            case 3:
                excluirTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void excluirUsuario() {
        System.out.println("\nExcluir Usuário");

        System.out.print("Informe o ID do Usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe a senha do usuário: ");
        String senha = scanner.nextLine();

        try {

            excluirController.excluirUsuario(usuarioId, senha);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void excluirCategoria() {
        System.out.println("\nExclusão de Categoria");

        System.out.print("Informe o ID da categoria a ser excluída: ");
        int categoriaId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe o ID do usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Informe a senha do usuário: ");
        String senha = scanner.nextLine();


        boolean sucesso = excluirController.excluirCategoria(categoriaId, usuarioId, senha);

        if (sucesso) {
            System.out.println("Categoria excluída com sucesso!");
        } else {
            System.out.println("Falha ao excluir categoria.");
        }
    }

    private void excluirTransacao() {
        System.out.print("Informe o ID da transação que deseja excluir: ");
        int transacaoId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe o ID do usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Informe a senha do usuário: ");
        String senha = scanner.nextLine();

        boolean sucesso = excluirController.excluirTransacao(transacaoId, usuarioId, senha);
        if (sucesso) {
            System.out.println("Transação excluída com sucesso!");
        } else {
            System.out.println("Erro ao excluir a transação. Verifique os dados fornecidos.");
        }
    }

    private void atualizar() {
        System.out.println("\nEscolha o que deseja atualizar:");
        System.out.println("1. Usuário");
        System.out.println("2. Categoria");
        System.out.println("3. Transação");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                atualizarUsuario();
                break;
            case 2:
                atualizarCategoria();
                break;
            case 3:
                atualizarTransacao();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void atualizarUsuario() {
        System.out.println("\nAtualizar Usuário");

        System.out.print("Informe o ID do Usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1. Corrente");
        System.out.println("2. Poupança");
        int tipoContaOpcao = scanner.nextInt();
        scanner.nextLine();

        String tipoConta = tipoContaOpcao == 1 ? "Corrente" : "Poupança";

        System.out.print("Informe o saldo: R$ ");
        double saldo = scanner.nextDouble();


        boolean sucesso = atualizarController.atualizarUsuario(usuarioId, nome, email, senha, tipoConta, saldo);

        if (sucesso) {
            System.out.println("Usuário e conta atualizados com sucesso!");
        } else {
            System.out.println("Falha ao atualizar usuário e conta.");
        }
    }

    public void atualizarCategoria() {
        System.out.print("Digite o ID do usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o novo tipo de categoria: ");
        String novoTipoCategoria = scanner.nextLine();

        boolean resultado = atualizarController.atualizarCategoria(usuarioId, senha, novoTipoCategoria);

        if (resultado) {
            System.out.println("Categoria atualizada com sucesso!");
        } else {
            System.out.println("Falha ao atualizar a categoria.");
        }
    }

    public void atualizarTransacao() {
        System.out.print("Digite o ID do usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o ID da transação: ");
        int transacaoId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o novo valor da transação: ");
        double novoValor = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o novo tipo de transação: ");
        String novoTipoTransacao = scanner.nextLine();

        boolean resultado = atualizarController.atualizarTransacao(usuarioId, senha, transacaoId, novoValor, novoTipoTransacao);

        if (resultado) {
            System.out.println("Transação atualizada com sucesso!");
        } else {
            System.out.println("Falha ao atualizar a transação.");
        }
    }




}
