package conta.model.dao;

import conta.model.ContaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContaDAO {

    public void cadastrarConta(ContaModel conta) {
        String sql = "INSERT INTO conta (tipo_conta, saldo) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getTipoConta());
            stmt.setDouble(2, conta.getSaldo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar conta", e);
        }
    }

    public void excluirPorUsuarioId(int usuarioId) {
        String sql = "DELETE FROM conta WHERE id = (SELECT conta_id FROM usuario WHERE id = ?)";

        try (Connection connection = ConexaoBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean atualizarConta(ContaModel conta) {
        String sql = "UPDATE conta SET tipoConta = ?, saldo = ? WHERE id = ?";

        try (Connection connection = ConexaoBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, conta.getTipoConta());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setInt(3, conta.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
