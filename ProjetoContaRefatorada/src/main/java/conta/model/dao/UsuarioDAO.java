package conta.model.dao;

import conta.model.UsuarioModel;

import java.sql.*;

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
        String sql = "SELECT * FROM usuario WHERE id = ?";
        UsuarioModel usuario = null;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Recuperando os dados do usuário
                int usuarioId = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                // Criando o objeto UsuarioModel com os dados recuperados
                usuario = new UsuarioModel(usuarioId, nome, email, senha, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }

        return usuario;
    }

}

