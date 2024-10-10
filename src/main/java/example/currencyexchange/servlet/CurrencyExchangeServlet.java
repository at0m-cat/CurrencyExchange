package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.CurrencyExchangeDTO;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.CurrencyExchange;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.model.exceptions.code_400.IncorrectParams;
import example.currencyexchange.model.exceptions.code_400.IncorrectСurrenciesPair;
import example.currencyexchange.model.exceptions.code_404.ExchangeRateNotFound;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.service.CurrencyExchangeService;
import example.currencyexchange.service.CurrencyService;
import example.currencyexchange.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.ExemptionMechanismException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

@WebServlet(value = "/exchange")
public class CurrencyExchangeServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getRENDERER();
    private static final CurrencyService CURRENCY_SERVICE = CurrencyService.getSERVICE();
    private static final ExchangeService EXCHANGE_SERVICE = ExchangeService.getSERVICE();
    private static final CurrencyExchangeService CURRENCY_EXCHANGE_SERVICE = CurrencyExchangeService.getSERVICE();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET" -> super.service(req, resp);
            default -> {
                resp.setStatus(500);
                RENDERER.print(resp, new DataBaseNotAvailable("%s: not available method"
                        .formatted(method)));

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String from = null;
        String to = null;
        Double amount = null;

        try {
            try {
                Double.valueOf(req.getParameter("amount"));

            } catch (NumberFormatException e) {
                throw new IncorrectParams("amount is not double type or null");
            }

            from = req.getParameter("from");
            to = req.getParameter("to");
            amount = Double.valueOf(req.getParameter("amount"));

            Stream.of(from, to).forEach(param -> {
                if (param == null || param.isEmpty()) {
                    throw new IncorrectParams();
                }
            });

            Stream.of(from, to).forEach(param -> {
                if (!param.equals(param.toUpperCase())) {
                    throw new IncorrectParams("%s - param case error, correct: UpperCase".formatted(param));
                }
            });

        } catch (IncorrectParams e) {
            resp.setStatus(400);
            RENDERER.print(resp, e);
            return;
        }
        try {
            if (from.equals(to)) {
                CurrencyDTO bc = CURRENCY_SERVICE.getByCode(from);
                CurrencyExchangeDTO pair = CURRENCY_EXCHANGE_SERVICE.createDto(bc, bc, 1.0, amount);
                RENDERER.print(resp, pair);
                return;
            }
            CurrencyExchangeDTO dto = CURRENCY_EXCHANGE_SERVICE.getByCode(from + to);
            dto = CURRENCY_EXCHANGE_SERVICE.setExchangeParameters(dto, amount);
            RENDERER.print(resp, dto);

        } catch (ObjectNotFound e) {
            resp.setStatus(404);
            RENDERER.print(resp, e);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);
        }
    }
}