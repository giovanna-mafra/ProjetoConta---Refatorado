package conta.model.dao;

import conta.model.ContaModel;
import conta.model.UsuarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public void cadastrarUsuario(UsuarioModel usuario) {
        String sqlConta = "INSERT INTO conta (tipoConta, saldo) VALUES (?, ?)";
        String sqlUsuario = "INSERT INTO usuario (nome, email, senha, conta_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {
            // Cadastrar a conta
            try (PreparedStatement stmtConta = conn.prepareStatement(sqlConta, Statement.RETURN_GENERATED_KEYS)) {
                stmtConta.setString(1, usuario.getConta().getTipoConta());
                stmtConta.setDouble(2, usuario.getConta().getSaldo());
                stmtConta.executeUpdate();

                // Obter o id da conta inserida
                ResultSet rs = stmtConta.getGeneratedKeys();
                if (rs.next()) {
                    int contaId = rs.getInt(1); // Id da conta inserida

                    // Agora cadastrar o usuário com o id da conta
                    try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                        stmtUsuario.setString(1, usuario.getNome());
                        stmtUsuario.setString(2, usuario.getEmail());
                        stmtUsuario.setString(3, usuario.getSenha());
                        stmtUsuario.setInt(4, contaId); // Vinculando a conta_id ao usuário
                        stmtUsuario.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
    }


    public UsuarioModel buscarUsuarioPorId(int id) {
        String sql = "SELECT u.id AS usuario_id, u.nome, u.email, u.senha, c.id AS conta_id, c.tipoConta, c.saldo "
                + "FROM usuario u "
                + "INNER JOIN conta c ON u.conta_id = c.id "
                + "WHERE u.id = ?";
        UsuarioModel usuario = null;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                int contaId = rs.getInt("conta_id");
                String tipoConta = rs.getString("tipoConta");
                double saldo = rs.getDouble("saldo");

                ContaModel conta = new ContaModel(contaId, tipoConta, saldo);
                usuario = new UsuarioModel(usuarioId, nome, email, senha, conta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }

        return usuario;
    }

    public List<UsuarioModel> buscarTodosUsuarios() {
        String sql = "SELECT u.id AS usuario_id, u.nome, u.email, u.senha, c.id AS conta_id, c.tipoConta, c.saldo "
                + "FROM usuario u "
                + "INNER JOIN conta c ON u.conta_id = c.id";

        List<UsuarioModel> usuarios = new ArrayList<>();

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                int contaId = rs.getInt("conta_id");
                String tipoConta = rs.getString("tipoConta");
                double saldo = rs.getDouble("saldo");

                ContaModel conta = new ContaModel(contaId, tipoConta, saldo);
                UsuarioModel usuario = new UsuarioModel(usuarioId, nome, email, senha, conta);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários", e);
        }

        return usuarios;
    }

    public void excluir(int usuarioId) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection connection = ConexaoBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



