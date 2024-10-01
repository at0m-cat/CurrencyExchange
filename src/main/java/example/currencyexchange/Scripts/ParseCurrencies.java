package example.currencyexchange.Scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.currencyexchange.Objects.Currencies;
import example.currencyexchange.Objects.utils.CurrenciesList;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParseCurrencies {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/currencyexchange";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "postgres";
    static final String GET_ALL = "SELECT * FROM currencies";

    /**
     * Connection to database -> table "currencyexchange"
     * @return List of currencies(id, code, fullname, sign)
     */
    @SneakyThrows
    public static List<Currencies> execute(){
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL);
        List<Currencies> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String code = resultSet.getString(2);
            String fullname = resultSet.getString(3);
            String sign = resultSet.getString(4);

            Currencies currencies = new Currencies(fullname, code, Integer.valueOf(id), Integer.valueOf(sign));
            list.add(currencies);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return list;
    }

}
