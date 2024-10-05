package example.currencyexchange.controller;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.ExchangeRates;
import example.currencyexchange.model.dao.ExchangeRatesDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.util.List;

@WebServlet(name = "AllExchangeRates", value = "/exchangerates")
public class AllCurrencyExchangeServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//
//        if (!DataBaseConfig.isConnection()) {
////            Renderer.printMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return;
//        }
//
//        List<ExchangeRates> exchangeRates = ExchangeRatesDAO.getExchangeRate();
////        Renderer.print(resp, exchangeRates);
    }
}
