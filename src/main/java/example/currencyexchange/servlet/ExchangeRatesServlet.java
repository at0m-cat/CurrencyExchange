package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.ObjectAlreadyExistException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
import example.currencyexchange.service.CurrencyService;
import example.currencyexchange.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@WebServlet(value = "/exchangerates")
public class ExchangeRatesServlet extends HttpServlet {
    private static final Renderer renderer = Renderer.getInstance();
    private static final ExchangeService exchangeService = ExchangeService.getInstance();
    private static final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET", "POST" -> super.service(req, resp);
            default ->
            {
                resp.setStatus(500);
                renderer.print(resp, new DataBaseNotAvailableException("%s: not available method"
                        .formatted(method)));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try
        {
            List<ExchangeDTO> exchangeDTOS = exchangeService.findAll();
            renderer.print(resp, exchangeDTOS);

        } catch (DataBaseNotAvailableException e) {
            resp.setStatus(500);
            renderer.print(resp, e);

        } catch (ObjectNotFoundException e) {
            resp.setStatus(404);
            renderer.print(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String baseCode = null;
        String targetCode = null;
        Double rate = null;

        try {
            baseCode = req.getParameter("baseCurrencyCode");
            targetCode = req.getParameter("targetCurrencyCode");
            String rateString = req.getParameter("rate");

            try {
                rate = Double.valueOf(rateString);
            } catch (NumberFormatException e) {
                throw new IncorrectParamsException("rate is not double or null");
            }

            Stream.of(baseCode, targetCode, rateString)
                    .forEach(param ->
                    {
                        if (param == null || param.isEmpty()) {
                            throw new IncorrectParamsException("params equals empty");
                        }
                    });

            Stream.of(baseCode, targetCode)
                    .forEach(elem ->
                    {
                        if (!elem.equals(elem.toUpperCase())) {
                            throw new IncorrectParamsException("%s - param case error, correct: UpperCase".formatted(elem));
                        }
                    });

        } catch (IncorrectParamsException e) {
            resp.setStatus(400);
            renderer.print(resp, e);
            return;
        }

        try {
            try {
                currencyService.findByCode(baseCode);
                currencyService.findByCode(targetCode);

            } catch (ObjectNotFoundException e) {
                resp.setStatus(404);
                renderer.print(resp, e);
                return;
            }

            try {
                exchangeService.findByCode(baseCode + targetCode);
                resp.setStatus(409);
                throw new ObjectAlreadyExistException();

            } catch (ObjectAlreadyExistException e) {
                resp.setStatus(409);
                renderer.print(resp, e);

            } catch (ObjectNotFoundException e) {
                CurrencyDTO baseCurrencyDTO = currencyService.findByCode(baseCode);
                CurrencyDTO targetCurrencyDTO = currencyService.findByCode(targetCode);
                ExchangeDTO pairs = exchangeService
                        .createDto(baseCurrencyDTO, targetCurrencyDTO, BigDecimal.valueOf(rate));
                exchangeService.save(pairs);
                resp.setStatus(201);
                renderer.print(resp, pairs);
            }

        } catch (ObjectAlreadyExistException e) {
            resp.setStatus(409);
            renderer.print(resp, e);

        } catch (DataBaseNotAvailableException e) {
            resp.setStatus(500);
            renderer.print(resp, e);

        } catch (IncorrectParamsException e) {
            resp.setStatus(400);
            renderer.print(resp, e);
        }
    }

}
