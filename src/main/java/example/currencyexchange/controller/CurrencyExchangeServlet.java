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
            Renderer.printErrorJson(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        try {
            String[] codeExchange = UserInputConfig.getCodeExchange(req.getPathInfo());
            if (codeExchange == null){
                Renderer.printErrorJson(resp, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String baseCode = codeExchange[0];
            String targetCode = codeExchange[1];
            ExchangeRates rates = ExchangeRatesDAO.findCodeRates(baseCode, targetCode);
            if (rates == null) {
                Renderer.printErrorJson(resp, HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Renderer.printJson(resp, rates);

        } catch (ArrayIndexOutOfBoundsException e) {
            Renderer.printErrorJson(resp, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        if (!DataBaseConfig.isConnection()) {
            Renderer.printErrorJson(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // todo: обработать ошибки, проверить пару, сделать обмен, создать новый объект

        String baseCode = req.getParameter("baseCurrencyCode");
        String targetCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");
        String[] params = {baseCode, targetCode, rate};

        if (!UserInputConfig.isCorrectPostParams(params)) {
            Renderer.printErrorJson(resp, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (ExchangeRatesDAO.findCodeRates(baseCode, targetCode) != null) {
            Renderer.printErrorJson(resp, HttpServletResponse.SC_CONFLICT);
            return;
        }

        ExchangeRatesDAO.setExchangeRate(baseCode, targetCode, rate);
        if (ExchangeRatesDAO.findCodeRates(baseCode, targetCode) != null) {
            Renderer.printErrorJson(resp, HttpServletResponse.SC_CREATED);
            return;
        }

//        Renderer.printJson(resp, er );
    }
}
