package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.CurrencyExchangeDTO;
import example.currencyexchange.model.exceptions.status_400.IncorrectParams;
import example.currencyexchange.model.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;
import example.currencyexchange.service.CurrencyExchangeService;
import example.currencyexchange.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Stream;

@WebServlet(value = "/exchange")
public class ExchangeServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getRENDERER();
    private static final CurrencyService CURRENCY_SERVICE = CurrencyService.getCURRENCY_SERVICE();
    private static final CurrencyExchangeService CURRENCY_EXCHANGE_SERVICE = CurrencyExchangeService.getCURRENCY_EXCHANGE_SERVICE();

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
                CurrencyDTO currencyDTO = CURRENCY_SERVICE.getByCode(from);
                CurrencyExchangeDTO pair = CURRENCY_EXCHANGE_SERVICE
                        .createDto(currencyDTO, currencyDTO, BigDecimal.valueOf(1.0), BigDecimal.valueOf(amount));
                RENDERER.print(resp, pair);
                return;
            }
            CurrencyExchangeDTO currencyExchangeDTO = CURRENCY_EXCHANGE_SERVICE.getByCode(from + to);
            currencyExchangeDTO = CURRENCY_EXCHANGE_SERVICE
                    .setExchangeParameters(currencyExchangeDTO, BigDecimal.valueOf(amount));
            RENDERER.print(resp, currencyExchangeDTO);

        } catch (ObjectNotFound e) {
            resp.setStatus(404);
            RENDERER.print(resp, e);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);
        }
    }
}