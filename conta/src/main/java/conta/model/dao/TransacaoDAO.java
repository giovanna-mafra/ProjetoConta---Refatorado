package conta.model.dao;

import conta.model.TransacaoModel;
import conta.model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    public void cadastrarTransacao(TransacaoModel transacao) {
        String sql = "INSERT INTO transacao (valor, tipoTransacao, usuario_id) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, transacao.getValor());
            stmt.setString(2, transacao.getTipoTransacao());
            stmt.setInt(3, transacao.getUsuario().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar transação", e);
        }
    }

    public void atualizarSaldo(UsuarioModel usuario, double valor) {
        String sql = "UPDATE conta SET saldo = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, valor);
            stmt.setInt(2, usuario.getConta().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo", e);
        }
    }

    public List<TransacaoModel> listarTransacoesPorUsuario(int usuarioId) {
        String sql = "SELECT t.id, t.valor, t.tipoTransacao, u.id AS usuario_id " +
                "FROM transacao t " +
                "INNER JOIN usuario u ON t.usuario_id = u.id " +
                "WHERE u.id = ?";
        List<TransacaoModel> transacoes = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                double valor = rs.getDouble("valor");
                String tipoTransacao = rs.getString("tipoTransacao");

                // Recriar o modelo de transação, incluindo o usuário
                UsuarioModel usuario = new UsuarioModel(usuarioId, "", "", "", null);
                TransacaoModel transacao = new TransacaoModel(id, valor, tipoTransacao, usuario);
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar transações", e);
        }

        return transacoes;
    }

    public void excluirPorUsuarioId(int usuarioId) {
        String sql = "DELETE FROM transacao WHERE usuario_id = ?";

        try (Connection connection = ConexaoBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean excluirTransacao(int transacaoId) {
        String sql = "DELETE FROM transacao WHERE id = ?";

        try (Connection connection = ConexaoBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, transacaoId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Retorna true se a transação foi excluída com sucesso
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retorna false em caso de erro
        }
    }
}
