package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.CurrencyExchangeDTO;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
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
    private static final Renderer renderer = Renderer.getInstance();
    private static final CurrencyService currencyService = CurrencyService.getInstance();
    private static final CurrencyExchangeService currencyExchangeService = CurrencyExchangeService.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET" -> super.service(req, resp);
            default -> {
                resp.setStatus(500);
                renderer.print(resp, new DataBaseNotAvailableException("%s: not available method"
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
                throw new IncorrectParamsException("amount is not double type or null");
            }

            from = req.getParameter("from");
            to = req.getParameter("to");
            amount = Double.valueOf(req.getParameter("amount"));

            Stream.of(from, to).forEach(param -> {
                if (param == null || param.isEmpty()) {
                    throw new IncorrectParamsException();
                }
                if (!param.equals(param.toUpperCase())) {
                    throw new IncorrectParamsException("%s - param case error, correct: UpperCase".formatted(param));
                }
            });

        } catch (IncorrectParamsException e) {
            resp.setStatus(400);
            renderer.print(resp, e);
            return;
        }
        try {
            if (from.equals(to)) {
                CurrencyDTO currencyDTO = currencyService.findByCode(from);
                CurrencyExchangeDTO pair = currencyExchangeService
                        .createDto(currencyDTO, currencyDTO, BigDecimal.valueOf(1.0), BigDecimal.valueOf(amount));
                renderer.print(resp, pair);
                return;
            }
            CurrencyExchangeDTO currencyExchangeDTO = currencyExchangeService.findByCode(from + to);
            currencyExchangeDTO = currencyExchangeService
                    .setExchangeParameters(currencyExchangeDTO, BigDecimal.valueOf(amount));
            renderer.print(resp, currencyExchangeDTO);

        } catch (ObjectNotFoundException e) {
            resp.setStatus(404);
            renderer.print(resp, e);

        } catch (DataBaseNotAvailableException e) {
            resp.setStatus(500);
            renderer.print(resp, e);
        }
    }
}