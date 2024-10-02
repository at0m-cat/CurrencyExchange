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
    public static boolean isConnectionValid() {
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

    /**
     * db name: "currencyexchange",
     * table name: "currencies"
     *
     * @return List Currencies
     */
    public static List<Currencies> getCurrencies() {
        String query = "select * from currencies";
        ResultSet resultSet = connect(query);
        return CurrencyDAO.parsing(resultSet);
    }

    /**
     * db name: "currencyexchange",
     * parse from table "exchangerates" and join table "currencies"
     *
     * @return List ExchangeRates ->
     * Base currency identifiers and data. Target currency identifiers and data. Exchange rate.
     */
    public static List<ExchangeRates> getExchangeRate() {
        String query = "SELECT e.id AS rate_id, c1.id AS base_id, c1.fullname AS base_name, c1.code AS base_code, " +
                "c1.sign AS base_sign, c2.id AS target_id, c2.fullname AS target_name, c2.code AS target_code, " +
                "c2.sign AS target_sign, e.rate " +
                "FROM exchangerates e " +
                "JOIN currencies c1 ON e.basecurrencyid = c1.id " +
                "JOIN currencies c2 ON e.targetcurrencyid = c2.id";
        ResultSet resultSet = connect(query);
        return ExchangeRatesDAO.parsing(resultSet);

    }

}
