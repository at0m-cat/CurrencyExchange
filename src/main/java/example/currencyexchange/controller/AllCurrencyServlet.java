package example.currencyexchange.controller;

import example.currencyexchange.model.Currencies;
import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.dao.CurrencyDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.util.List;

@WebServlet(name = "AllCurrency", value = "/currencies")
public class AllCurrencyServlet extends HttpServlet {

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (!DataBaseConfig.isConnection()) {
            Renderer.printMessage(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        List<Currencies> currenciesList = CurrencyDAO.getCurrencies();
        Renderer.print(response, currenciesList);
    }

}
