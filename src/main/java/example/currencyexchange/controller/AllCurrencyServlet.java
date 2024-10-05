package example.currencyexchange.controller;

import example.currencyexchange.model.Currencies;
import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.dao.CurrencyDAO;
import example.currencyexchange.model.exceptions.DataBaseNotAvailable;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AllCurrency", value = "/currencies")
public class AllCurrencyServlet extends HttpServlet {

    Renderer renderer = new Renderer();
    CurrencyDAO currencyDAO = new CurrencyDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            renderer.print(response, currencyDAO.getCurrencies());

        } catch (SQLException | NullPointerException e){
            renderer.print(response, new DataBaseNotAvailable(response));
        }

    }

}
