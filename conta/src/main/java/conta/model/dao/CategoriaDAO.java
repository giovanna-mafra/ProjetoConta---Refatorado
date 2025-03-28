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

    public void excluirPorUsuarioId(int usuarioId) {
        String sql = "DELETE FROM categoria WHERE usuario_id = ?";

        try (Connection connection = ConexaoBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean excluir(int categoriaId) {
        String sql = "DELETE FROM categoria WHERE id = ?";

        try (Connection connection = ConexaoBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, categoriaId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Retorna true se a categoria foi excluída com sucesso
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retorna false em caso de erro
        }
    }

    public CategoriaModel buscarCategoriaPorUsuarioId(int usuarioId) {
        String sql = "SELECT id, tipoCategoria FROM categoria WHERE usuario_id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int categoriaId = rs.getInt("id");
                String tipoCategoria = rs.getString("tipoCategoria");

                return new CategoriaModel(categoriaId, tipoCategoria, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria do usuário", e);
        }

        return null;  // Caso não encontre
    }

    // Atualizar categoria
    public boolean atualizarCategoria(CategoriaModel categoria) {
        String sql = "UPDATE categoria SET tipoCategoria = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getTipoCategoria());
            stmt.setInt(2, categoria.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
