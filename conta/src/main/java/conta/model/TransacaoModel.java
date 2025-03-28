package conta.model;

public class TransacaoModel {
    private int id;
    private double valor;
    private String tipoTransacao;
    private UsuarioModel usuario;

    public TransacaoModel(int id, double valor, String tipoTransacao, UsuarioModel usuario) {
        this.id = id;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
