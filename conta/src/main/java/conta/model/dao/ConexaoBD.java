package conta.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/ContaFinanceira";
    private static final String USER = "root";
    private static final String PASSWORD = "Nave31@03";

    // Método protegido para obter conexão com o banco
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
