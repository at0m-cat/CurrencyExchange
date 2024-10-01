//package utils;
//
//import lombok.Getter;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Currency;
//
//public class ConnectionToDataBase {
//
//    private final String DB_URL = "jdbc:postgresql://localhost:5432/currencyexchange";
//    private final String USER = "";
//    private final String PASS = "";
//
//
//    public static void main(String[] args) {
//        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
//        connectionToDataBase.connect();
//
//    }
//
//
//    public void connect() {
//
//        System.out.println("Connect to database...");
//
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("PostgreSQL driver not found");
//
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("Connecting to database successfully...");
//        Connection connection = null;
//
//
//        try {
//            connection = DriverManager.getConnection(DB_URL);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * from currencies");
//
//
//
//            while (resultSet.next()) {
//                String id = resultSet.getString(1);
//                String code = resultSet.getString(2);
//                String fullname = resultSet.getString(3);
//                String sign = resultSet.getString(4);
//
//                example.currencyexchange.Objects.Currencies input = new example.currencyexchange.Objects.Currencies(code, fullname, sign);
//                OutBD out = new OutBD(Integer.valueOf(id), input);
//
//                // return HashMap<ID, Currency> ???
//
//
//            }
//
//
//        } catch (SQLException e) {
//            System.out.println("Database connection failed");
//
//            throw new RuntimeException(e);
//        }
////
//        if (connection != null) {
//            System.out.println("Database connection established");
//        } else {
//            System.out.println("Failed to establish connection");
//        }
//
//    }
//
//}
