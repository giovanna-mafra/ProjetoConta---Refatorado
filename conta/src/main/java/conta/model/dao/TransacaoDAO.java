package conta.model.dao;

import conta.model.TransacaoModel;
import conta.model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
