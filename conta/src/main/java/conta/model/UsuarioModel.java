package conta.model;

public class UsuarioModel {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private ContaModel conta;

    public UsuarioModel(int id, String nome, String email, String senha, ContaModel conta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.conta = conta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ContaModel getConta() {
        return conta;
    }

    public void setConta(ContaModel conta) {
        this.conta = conta;
    }
}
