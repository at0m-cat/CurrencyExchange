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
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CurrencyExchange", value = "/exchangerates/*")
public class CurrencyExchangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!DataBaseConfig.isConnectionValid()){
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            return;
        }

        String pathInfo = req.getPathInfo();
        try {
            String[] parts = pathInfo.split("/");
            String codes = parts[1];

            String[] code = codes.split("(?<=\\G...)");
            String base_code = code[0];
            String target_code = code[1];

            List<ExchangeRates> exchangeRates = DataBaseConfig.getExchangeRate();
            ExchangeRates rates = ExchangeRatesDAO.findCodeRates(exchangeRates, base_code, target_code);

            if (rates == null) {
                Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_NOT_FOUND));
                return;
            }

            resp.setContentType("application/json");
            req.setCharacterEncoding("UTF-8");
            Renderer.printJson(resp, rates);

        } catch (ArrayIndexOutOfBoundsException e) {
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
        }
    }
}
