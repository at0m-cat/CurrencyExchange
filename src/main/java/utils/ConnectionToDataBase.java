package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDataBase {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/currencyexchange";
    static final String USER = "";
    static final String PASS = "";

    public static void main(String[] args) {

        System.out.println("Connect to database...");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL driver not found");

            throw new RuntimeException(e);
        }

        System.out.println("Connecting to database successfully...");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Database connection failed");

            throw new RuntimeException(e);
        }

        if (connection != null) {
            System.out.println("Database connection established");
        } else {
            System.out.println("Failed to establish connection");
        }


    }

}
