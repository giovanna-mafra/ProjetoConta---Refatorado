package conta.model;

public class CategoriaModel {
    private int id;
    private String tipoCategoria;
    private UsuarioModel usuario;

    public CategoriaModel(int id, String tipoCategoria, UsuarioModel usuario) {
        this.id = id;
        this.tipoCategoria = tipoCategoria;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
