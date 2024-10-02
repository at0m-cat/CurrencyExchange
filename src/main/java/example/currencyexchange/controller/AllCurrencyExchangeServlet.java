package example.currencyexchange.controller;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.ExchangeRates;
import example.currencyexchange.model.dao.ExchangeRatesDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AllExchangeRates", value = "/exchangerates")
public class AllCurrencyExchangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (!DataBaseConfig.isConnectionValid()){
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            return;
        }

        List<ExchangeRates> exchangeRates = ExchangeRatesDAO.getExchangeRate();
        Renderer.printJson(resp, exchangeRates);
    }
}
