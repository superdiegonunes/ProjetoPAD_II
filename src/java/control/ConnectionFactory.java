package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author guto
 */
public class ConnectionFactory {

    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/Carrinho_Compras";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw e;
        }
    }
}
