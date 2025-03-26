package conta.model;

public class ContaModel {
    private int id;
    private String tipoConta;
    private Double saldo;

    public ContaModel(int id, String tipoConta, Double saldo) {
        this.id = id;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
