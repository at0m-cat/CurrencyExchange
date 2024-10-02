package example.currencyexchange.controller;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AllCurrency", value = "/currencies")
public class AllCurrencyServlet extends HttpServlet {


    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (!DataBaseConfig.isConnectionValid()){
            Renderer.printErrorJson(response, String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            return;
        }

        List<Currencies> currenciesList = DataBaseConfig.getCurrencies();
        Renderer.printJson(response, currenciesList);

    }



}
