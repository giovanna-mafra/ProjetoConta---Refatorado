package conta.model.dao;

import conta.model.TransacaoModel;
import conta.model.UsuarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO extends ConexaoBD {

    // Método para cadastrar uma transação
    public void cadastrarTransacao(TransacaoModel transacao) {
        String sql = "INSERT INTO transacao (valor, tipoTransacao, usuario_id) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, transacao.getValor());
            stmt.setString(2, transacao.getTipoTransacao());
            stmt.setInt(3, transacao.getUsuario().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar transação", e);
        }
    }

    // Método para atualizar o saldo de um usuário
    public void atualizarSaldo(UsuarioModel usuario, double valor) {
        String sql = "UPDATE conta SET saldo = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, valor);
            stmt.setInt(2, usuario.getConta().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo", e);
        }
    }

    // Método para listar as transações de um usuário
    public List<TransacaoModel> listarTransacoesPorUsuario(int usuarioId) {
        String sql = "SELECT t.id, t.valor, t.tipoTransacao, u.id AS usuario_id " +
                "FROM transacao t " +
                "INNER JOIN usuario u ON t.usuario_id = u.id " +
                "WHERE u.id = ?";
        List<TransacaoModel> transacoes = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                double valor = rs.getDouble("valor");
                String tipoTransacao = rs.getString("tipoTransacao");

                UsuarioModel usuario = new UsuarioModel(usuarioId, "", "", "", null);
                TransacaoModel transacao = new TransacaoModel(id, valor, tipoTransacao, usuario);
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar transações", e);
        }

        return transacoes;
    }

    // Método para excluir todas as transações de um usuário
    public void excluirPorUsuarioId(int usuarioId) {
        String sql = "DELETE FROM transacao WHERE usuario_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir uma transação pelo ID
    public boolean excluirTransacao(int transacaoId) {
        String sql = "DELETE FROM transacao WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, transacaoId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar uma transação pelo ID
    public TransacaoModel buscarTransacaoPorId(int transacaoId) {
        String sql = "SELECT * FROM transacao WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transacaoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                double valor = rs.getDouble("valor");
                String tipoTransacao = rs.getString("tipoTransacao");
                int usuarioId = rs.getInt("usuario_id");

                UsuarioModel usuario = new UsuarioModel(usuarioId, "", "", "", null);
                return new TransacaoModel(id, valor, tipoTransacao, usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para atualizar uma transação
    public boolean atualizarTransacao(TransacaoModel transacao) {
        String sql = "UPDATE transacao SET valor = ?, tipoTransacao = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, transacao.getValor());
            stmt.setString(2, transacao.getTipoTransacao());
            stmt.setInt(3, transacao.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
