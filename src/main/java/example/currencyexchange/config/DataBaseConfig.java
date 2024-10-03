package example.currencyexchange.config;

import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class DataBaseConfig {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/currencyexchange";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "postgres";

    /**
     * Connection to dataBase
     *
     * @param query SQL request
     * @param params parameters in SQL request, if any
     * @return ResultSet - if SQL query is "SELECT", otherwise "NULL"
     * @throws SQLException
     */
    @SneakyThrows
    public static ResultSet connect(String query, Object... params) throws SQLException {
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }

        if (query.trim().toUpperCase().startsWith("SELECT")) {
            return preparedStatement.executeQuery();
        } else {
            preparedStatement.executeUpdate();
            return null;
        }
    }

    /**
     * Check if there is a connection
     *
     * @return boolean
     */
    @SneakyThrows
    public static boolean isConnection() {
        Class.forName(JDBC_DRIVER);
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            if (connection != null) {
                return true;
            }
        } catch (PSQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


}
