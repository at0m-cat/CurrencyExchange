package example.currencyexchange.controller;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.exceptions.IncorrectСurrenciesPair;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

@WebServlet(name = "CurrencyExchange", value = "/exchangerates/*")
public class CurrencyExchangeServlet extends HttpServlet {

    Renderer renderer = new Renderer();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {

        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");

        try {
            String[] args = req.getPathInfo().split("/");
            String[] codes = args[1].split("(?<=\\G...)");
            String baseCode = codes[0];
            String targetCode = codes[1];

            if (codes.length > 2) {
                throw new IncorrectСurrenciesPair(resp, "Error when entering currency");
            }

            Stream.of(codes).forEach(code -> {
                if (code.length() < 3){
                    throw new IncorrectСurrenciesPair(resp, "'%s' - Error code length".formatted(code));
                }

                if (!code.toUpperCase().equals(code)) {
                    throw new IncorrectСurrenciesPair(resp, "%s - Error case, corrected: %s"
                            .formatted(code, code.toUpperCase()));
                }

            });

//            renderer.print(resp, codes);


        }
        catch (ArrayIndexOutOfBoundsException e) {
            renderer.print(resp, new IncorrectСurrenciesPair(resp, "No currency"));

        } catch (IncorrectСurrenciesPair e) {
            renderer.print(resp, e);
        }


//        if (!isCorrectCodeExchange(codes)) {
//            throw new IllegalArgumentException();
//        }
//        return codes.split("(?<=\\G...)");


//
//        if (!DataBaseConfig.isConnection()) {
//            Renderer.printMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return;
//        }
//        try {
//            String[] codeExchange = UserInputConfig.getCodeExchange(req.getPathInfo());
//
//            String baseCode = codeExchange[0];
//            String targetCode = codeExchange[1];
//            ExchangeRates rates = ExchangeRatesDAO.findCodeRate(baseCode, targetCode);
//            if (rates == null) {
//                Renderer.printMessage(resp, HttpServletResponse.SC_NOT_FOUND);
//                return;
//            }
//            Renderer.print(resp, rates);
//
//        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
//            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
//        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
//
//        if (!DataBaseConfig.isConnection()) {
//            Renderer.printMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return;
//        }
//
//        String baseCode = req.getParameter("baseCurrencyCode");
//        String targetCode = req.getParameter("targetCurrencyCode");
//        String rate = req.getParameter("rate");
//        String[] params = {baseCode, targetCode, rate};
//
//        if (!UserInputConfig.isDoubleRate(rate)) {
//            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//
//        if (!UserInputConfig.isNotNullParams(params)) {
//            Renderer.printMessage(resp, HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//
//        try {
//            ExchangeRatesDAO.setExchangeRate(baseCode, targetCode, rate);
//            Renderer.printMessage(resp, HttpServletResponse.SC_CREATED);
//        } catch (NoSuchMethodException e) {
//            Renderer.printMessage(resp, HttpServletResponse.SC_CONFLICT);
//        }

    }
}
