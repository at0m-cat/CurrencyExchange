package example.currencyexchange.Servlets;
import example.currencyexchange.Objects.Currencies;
import example.currencyexchange.Objects.Rates;
import example.currencyexchange.Scripts.DataBaseFunction;
import example.currencyexchange.Scripts.Renderer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;


@WebServlet(name = "Currencies", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // currencyexchange
        // exchangerates

        ResultSet resultSet = DataBaseFunction
                .connect("currencyexchange", "SELECT * FROM currencies");

        List<Currencies> currencies = Currencies.parsing(resultSet);
        Renderer.execute(response, currencies);

//        List<Rates> rates = Rates.parsing(resultSet);
//        Renderer.execute(response, rates);
    }


    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
