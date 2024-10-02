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
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.NoSuchElementException;

@WebServlet(name = "CurrencyExchange", value = "/exchangerates/*")
public class CurrencyExchangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!DataBaseConfig.isConnectionValid()) {
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            return;
        }

        String pathInfo = req.getPathInfo();
        try {
            String[] parts = pathInfo.split("/");
            String codes = parts[1];

            String[] code = codes.split("(?<=\\G...)");
            String base_code = code[0].toUpperCase();
            String target_code = code[1].toUpperCase();

            ExchangeRates rates = ExchangeRatesDAO.findCodeRates(base_code, target_code);
            resp.setContentType("application/json");
            req.setCharacterEncoding("UTF-8");
            Renderer.printJson(resp, rates);

        } catch (ArrayIndexOutOfBoundsException e) {
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
        } catch (NoSuchElementException e) {
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_NOT_FOUND));
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // todo: обработать ошибки, проверить пару, сделать обмен, создать новый объект

        String baseCode = req.getParameter("baseCurrencyCode");
        String targetCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        if (ExchangeRatesDAO.isExist(baseCode, targetCode)) {
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_CONFLICT));
            return;
        }

        // todo: создать объект


//        // если в базе объект существует, то создаем и печатаем
//
//        ExchangeRates exchangeRates = ExchangeRatesDAO.findCodeRates(baseCode, targetCode);
//        Renderer.printJson(resp, exchangeRates);

    }
}
