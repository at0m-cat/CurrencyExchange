package example.currencyexchange.controller;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.config.UserInputConfig;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.model.dao.ExchangeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Exchange", value = "/exchange")
public class ExchangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");

        if (!DataBaseConfig.isConnection()) {
            Renderer.printMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        String[] params = {from, to, amount};

        if (!UserInputConfig.isNotNullParams(params)){
            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // todo: exchange create

        try {
            Exchange exchange = ExchangeDAO.getEx(from, to, Double.valueOf(amount));
            Renderer.print(resp, exchange);
        } catch (NullPointerException e) {
            Renderer.printMessage(resp, HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
