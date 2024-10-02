package example.currencyexchange.Controller;
import example.currencyexchange.Model.Currencies;
import example.currencyexchange.Model.Rates;
import lombok.SneakyThrows;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseFunction {

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
     * db name: "currencyexchange",
     * table name: "currencies"
     * @return List
     */
    public static List<Currencies> getCurrencies() {
        ResultSet resultSet = connect("SELECT * FROM currencies");
        return Currencies.parsing(resultSet);
    }

    /**
     * db name: "currencyexchange",
     * table name: "exchangerates"
     * @return List
     */
    public static List<Rates> getRates() {
        ResultSet resultSet = connect("SELECT * FROM exchangerates");
        return Rates.parsing(resultSet);
    }

}
