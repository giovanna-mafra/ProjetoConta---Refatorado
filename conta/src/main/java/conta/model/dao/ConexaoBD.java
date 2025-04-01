package conta.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static ConexaoBD instance;
    private static final String URL = "jdbc:mysql://localhost:3306/ContaFinanceira";
    private static final String USER = "root";
    private static final String PASSWORD = "Nave31@03";

    private ConexaoBD() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConexaoBD getInstance() {
        if (instance == null) {
            synchronized (ConexaoBD.class) {
                if (instance == null) {
                    instance = new ConexaoBD(); //
                }
            }
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
