package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
import example.currencyexchange.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Stream;

@WebServlet(value = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private static final Renderer renderer = Renderer.getInstance();
    private static final ExchangeService exchangeService = ExchangeService.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET" -> super.service(req, resp);
            case "PATCH" -> this.doPatch(req, resp);
            default -> {
                resp.setStatus(500);
                renderer.print(resp, new DataBaseNotAvailableException("%s: not available method"
                        .formatted(method)));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (req.getPathInfo().equals("/")) {
                throw new IncorrectParamsException("No currency pair");
            }

            String[] args = req.getPathInfo().split("/");
            String[] codes = args[1].split("(?<=\\G...)");

            if (codes.length != 2) {
                throw new IncorrectParamsException("Error when entering currency");
            }

            String baseCode = codes[0];
            String targetCode = codes[1];

            Stream.of(codes).forEach(code -> {

                if (code.length() < 3) {
                    throw new IncorrectParamsException("Invalid code type");
                }

                if (!code.toUpperCase().equals(code)) {
                    throw new IncorrectParamsException("%s - Error case codes");
                }
            });

            if (baseCode.equalsIgnoreCase(targetCode)) {
                throw new IncorrectParamsException("Currency pairs match");
            }

            ExchangeDTO exchangeDTO = exchangeService.findByCode(baseCode + targetCode);
            renderer.print(resp, exchangeDTO);

        } catch (IncorrectParamsException e) {
            resp.setStatus(400);
            renderer.print(resp, e);
        } catch (DataBaseNotAvailableException e) {
            resp.setStatus(500);
            renderer.print(resp, e);
        } catch (ObjectNotFoundException e) {
            resp.setStatus(404);
            renderer.print(resp, e);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getPathInfo().equals("/")) {
                throw new IncorrectParamsException("No currency pair");
            }

            String parameter = req.getParameter("rate");
            String[] args = req.getPathInfo().split("/");
            String[] codes = args[1].split("(?<=\\G...)");

            if (codes.length != 2) {
                throw new IncorrectParamsException("Invalid code type");
            }

            Stream.of(codes).forEach(code -> {
                if (code.length() < 3) {
                    throw new IncorrectParamsException("Invalid code length");
                }

                if (!code.toUpperCase().equals(code)) {
                    throw new IncorrectParamsException("Error case codes");
                }

            });

            if (parameter.isEmpty()) {
                throw new IncorrectParamsException("Rate is empty");
            }

            try {
                Double.valueOf(parameter);

            } catch (NumberFormatException e) {
                throw new IncorrectParamsException("Rate is not double");
            }

            String baseCode = codes[0];
            String targetCode = codes[1];
            Double rate = Double.valueOf(parameter);

            if (baseCode.equalsIgnoreCase(targetCode)) {
                throw new IncorrectParamsException("Currency pairs match");
            }

            try {
                exchangeService.updateRate(baseCode, targetCode, rate);

            } catch (ObjectNotFoundException e) {
                resp.setStatus(404);
                renderer.print(resp, e);
                return;
            }

            ExchangeDTO dto = exchangeService.findByCode(baseCode + targetCode);
            renderer.print(resp, dto);

        } catch (IncorrectParamsException e) {
            resp.setStatus(400);
            renderer.print(resp, e);

        } catch (DataBaseNotAvailableException e) {
            resp.setStatus(500);
            renderer.print(resp, e);

        } catch (ObjectNotFoundException e) {
            resp.setStatus(404);
            renderer.print(resp, e);

        }
    }
}
