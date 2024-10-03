package example.currencyexchange.controller;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.config.UserInputConfig;
import example.currencyexchange.model.ExchangeRates;
import example.currencyexchange.model.dao.ExchangeRatesDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@WebServlet(name = "CurrencyExchange", value = "/exchangerates/*")
public class CurrencyExchangeServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");

        if (!DataBaseConfig.isConnection()) {
            Renderer.printMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        try {
            String[] codeExchange = UserInputConfig.getCodeExchange(req.getPathInfo());

            String baseCode = codeExchange[0];
            String targetCode = codeExchange[1];
            ExchangeRates rates = ExchangeRatesDAO.findCodeRate(baseCode, targetCode);
            if (rates == null) {
                Renderer.printMessage(resp, HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Renderer.print(resp, rates);

        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        if (!DataBaseConfig.isConnection()) {
            Renderer.printMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        String baseCode = req.getParameter("baseCurrencyCode");
        String targetCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");
        String[] params = {baseCode, targetCode, rate};

        if (!UserInputConfig.isDoubleRate(rate)){
            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!UserInputConfig.isNotNullParams(params)) {
            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            ExchangeRatesDAO.setExchangeRate(baseCode, targetCode, rate);
            Renderer.printMessage(resp, HttpServletResponse.SC_CREATED);
        } catch (NoSuchMethodException e){
            Renderer.printMessage(resp, HttpServletResponse.SC_CONFLICT);
        }

    }
}
