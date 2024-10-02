package example.currencyexchange.config;

import example.currencyexchange.model.ExchangeRates;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.model.dao.CurrencyDAO;
import example.currencyexchange.model.dao.ExchangeRatesDAO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.List;

public class DataBaseConfig {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/currencyexchange";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "postgres";

    /**
     * Connection to dataBase
     * @param query Strinq
     * @return ResultSet
     */
    @SneakyThrows
    public static ResultSet connect(String query) {
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

        /**
         * Check if there is a connection
         * @return boolean
         */
        @SneakyThrows
        public static boolean isConnectionValid () {
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
