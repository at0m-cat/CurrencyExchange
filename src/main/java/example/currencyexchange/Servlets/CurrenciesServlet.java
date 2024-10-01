package example.currencyexchange.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.currencyexchange.Objects.Currencies;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(name = "example.currencyexchange.Objects.Currencies", value = "/currencies/*")
public class CurrenciesServlet extends HttpServlet {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/currencyexchange";
    static final String DATABASE_USER = "postgres";
    static final String DATABASE_PASSWORD = "postgres";
    static final String GET_ALL = "SELECT * FROM currencies";

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = response.getWriter();

        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(GET_ALL);

        ObjectMapper mapper = new ObjectMapper();



        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String code = resultSet.getString(2);
            String fullname = resultSet.getString(3);
            String sign = resultSet.getString(4);

            Currencies currencies = new Currencies(Integer.valueOf(id), code, fullname, Integer.valueOf(sign));
            writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(currencies));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }
}
