package example.currencyexchange.controller;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.UserInputConfig;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.dao.CurrencyDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@WebServlet(name = "SingeCurrency", value = "/currencies/*")
public class SingleCurrencyServlet extends HttpServlet {

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
            String code = UserInputConfig.getCodeCurrency(req.getPathInfo());

            if (code == null) {
                Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Currencies currency = CurrencyDAO.findCodeCurrency(code);
            if (currency == null) {
                Renderer.printMessage(resp, HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Renderer.print(resp, currency);

        } catch (ArrayIndexOutOfBoundsException e) {
            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        if (!DataBaseConfig.isConnection()) {
            Renderer.printMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // todo: сверить с базой данных для добавления объекта

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");

        String[] params = {code, name, sign};

        if (!UserInputConfig.isCorrectPostParams(params)){
            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (CurrencyDAO.findCodeCurrency(code) != null) {
            Renderer.printMessage(resp, HttpServletResponse.SC_CONFLICT);
            return;
        }

        CurrencyDAO.setCurrency(name, code, sign);
        if (CurrencyDAO.findCodeCurrency(code) != null) {
            Renderer.printMessage(resp, HttpServletResponse.SC_CREATED);
            return;
        }

    }
}
