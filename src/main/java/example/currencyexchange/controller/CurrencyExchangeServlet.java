package example.currencyexchange.controller;
import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.dao.ExchangeRates;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CurrencyExchange", value = "/exchangerates/*")
public class CurrencyExchangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        try {
            String[] parts = pathInfo.split("/");
            String codes = parts[1];

            String[] code = codes.split("(?<=\\G...)");
            String base_code = code[0];
            String target_code = code[1];

            List<ExchangeRates> exchangeRates = DataBaseConfig.getExchangeRate();
            ExchangeRates rates = ExchangeRates.findCodeRates(exchangeRates, base_code, target_code);

            if (rates == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            resp.setContentType("application/json");
            req.setCharacterEncoding("UTF-8");
            Renderer.printExchangeRates(resp, rates);

        } catch (ArrayIndexOutOfBoundsException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
