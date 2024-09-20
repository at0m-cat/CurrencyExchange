package example.currencyexchange;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(name = "Currencies", value = "/currencies/*")
public class CurrenciesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/currencyexchange");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from currencies");

        while (resultSet.next()) {


            out.println(resultSet.getString("fullname"));
//            out.println(resultSet.getString("code"));

        }

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }
}
