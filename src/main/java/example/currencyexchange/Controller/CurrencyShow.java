package example.currencyexchange.Controller;

import example.currencyexchange.config.DataBaseFunction;
import example.currencyexchange.Model.dao.Currencies;
import example.currencyexchange.Model.SingleCurrency;
import example.currencyexchange.config.Renderer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SingeCurrency", value = "/currencies/*")
public class CurrencyShow extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        // todo: обращаться к базе данных, а не к классу

        try {
            String[] parts = pathInfo.split("/");
            String code = parts[1];

            List<Currencies> currenciesList = DataBaseFunction.getCurrencies();
            SingleCurrency currency = Currencies.findCodeCurrency(currenciesList, code);

            if (currency == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            Renderer.printCurrency(resp, currency);

        } catch (ArrayIndexOutOfBoundsException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
