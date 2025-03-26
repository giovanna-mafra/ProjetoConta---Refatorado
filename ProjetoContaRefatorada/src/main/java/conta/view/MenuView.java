package conta.view;

import conta.controller.CadastroController;
import conta.model.dao.CategoriaDAO;
import conta.model.dao.ContaDAO;
import conta.model.dao.UsuarioDAO;
import java.util.Scanner;

public class MenuView {
    private Scanner scanner = new Scanner(System.in);  // Inicializando o scanner
    private CadastroController cadastroController;  // Definindo o controller

    public MenuView() {
        // Inicializando o CadastroController com os DAOs
        this.cadastroController = new CadastroController(new UsuarioDAO(), new ContaDAO(), new CategoriaDAO());
    }

    public void mostrarMenu() {  // Método para encapsular a lógica do menu
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
            scanner.nextLine();  // Consumir a quebra de linha restante

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
//                    listar();
                    break;
                case 3:
//                    excluir();
                    break;
                case 4:
//                    atualizar();
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
        scanner.nextLine();  // Consumir a quebra de linha restante

        switch (escolha) {
            case 1:
                cadastrarUsuario();
                break;
            case 2:
                cadastrarCategoria();
                break;
            case 3:
//                cadastrarTransacao();
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
        scanner.nextLine(); // Consumindo a quebra de linha

        String tipoConta = tipoContaOpcao == 1 ? "Corrente" : "Poupança";

        System.out.print("Informe o saldo inicial: R$ ");
        double saldoInicial = scanner.nextDouble();

        // Chamando o controller para cadastrar
        cadastroController.cadastrarUsuario(nome, email, senha, tipoConta, saldoInicial);
        System.out.println("Cadastro realizado com sucesso!");
    }

    private void cadastrarCategoria() {
        System.out.println("\nCadastro de Categoria");

        System.out.print("Tipo de Categoria: ");
        String tipoCategoria = scanner.nextLine();

        System.out.print("Informe o ID do Usuário: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();  // Consumir a quebra de linha restante

        System.out.print("Informe a senha do usuário: ");
        String senha = scanner.nextLine();

        // Chamando o controller para cadastrar a categoria
        cadastroController.cadastrarCategoria(tipoCategoria, usuarioId, senha);
    }

}
