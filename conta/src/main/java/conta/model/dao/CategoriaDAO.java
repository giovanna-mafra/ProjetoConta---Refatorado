package conta.model.dao;

import conta.model.CategoriaModel;
import conta.model.ContaModel;
import conta.model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void cadastrarCategoria(CategoriaModel categoria) {
        String sql = "INSERT INTO categoria (tipoCategoria, usuario_id) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getTipoCategoria());
            stmt.setInt(2, categoria.getUsuario().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar categoria", e);
        }
    }

    public List<CategoriaModel> listarCategorias() {
        String sql = "SELECT c.id, c.tipoCategoria, u.id AS usuario_id, u.nome, u.email, u.senha, co.id AS conta_id, co.tipoConta, co.saldo "
                + "FROM categoria c "
                + "INNER JOIN usuario u ON c.usuario_id = u.id "
                + "INNER JOIN conta co ON u.conta_id = co.id";

        List<CategoriaModel> categorias = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int categoriaId = rs.getInt("id");
                String tipoCategoria = rs.getString("tipoCategoria");

                int usuarioId = rs.getInt("usuario_id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                int contaId = rs.getInt("conta_id");
                String tipoConta = rs.getString("tipoConta");
                double saldo = rs.getDouble("saldo");

                ContaModel conta = new ContaModel(contaId, tipoConta, saldo);
                UsuarioModel usuario = new UsuarioModel(usuarioId, nome, email, senha, conta);
                CategoriaModel categoria = new CategoriaModel(categoriaId, tipoCategoria, usuario);

                categorias.add(categoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias", e);
        }

        return categorias;
    }
}
