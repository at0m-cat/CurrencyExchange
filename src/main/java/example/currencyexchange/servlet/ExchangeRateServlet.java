package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.exceptions.status_201.SuccesComplete;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
import example.currencyexchange.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Stream;

@WebServlet(value = "/exchangerate/*")
public class ExchangeRateServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getInstance();
    private static final ExchangeService EXCHANGE_SERVICE = ExchangeService.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET" -> super.service(req, resp);
            case "PATCH" -> this.doPatch(req, resp);
            default -> {
                resp.setStatus(500);
                RENDERER.print(resp, new DataBaseNotAvailable("%s: not available method"
                        .formatted(method)));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (req.getPathInfo().equals("/")) {
                throw new IncorrectParams("No currency pair");
            }

            String[] args = req.getPathInfo().split("/");
            String[] codes = args[1].split("(?<=\\G...)");

            if (codes.length != 2) {
                throw new IncorrectParams("Error when entering currency");
            }

            String baseCode = codes[0];
            String targetCode = codes[1];

            Stream.of(codes).forEach(code -> {

                if (code.length() < 3) {
                    throw new IncorrectParams("Invalid code type");
                }

                if (!code.toUpperCase().equals(code)) {
                    throw new IncorrectParams("%s - Error case codes");
                }
            });

            if (baseCode.equalsIgnoreCase(targetCode)) {
                throw new IncorrectParams("Currency pairs match");
            }

            ExchangeDTO exchangeDTO = EXCHANGE_SERVICE.findByCode(baseCode + targetCode);
            RENDERER.print(resp, exchangeDTO);

        } catch (IncorrectParams e) {
            resp.setStatus(400);
            RENDERER.print(resp, e);
        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);
        } catch (ObjectNotFound e) {
            resp.setStatus(404);
            RENDERER.print(resp, e);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getPathInfo().equals("/")) {
                throw new IncorrectParams("No currency pair");
            }

            String parameter = req.getParameter("rate");
            String[] args = req.getPathInfo().split("/");
            String[] codes = args[1].split("(?<=\\G...)");

            if (codes.length != 2) {
                throw new IncorrectParams("Invalid code type");
            }

            Stream.of(codes).forEach(code -> {
                if (code.length() < 3) {
                    throw new IncorrectParams("Invalid code length");
                }

                if (!code.toUpperCase().equals(code)) {
                    throw new IncorrectParams("Error case codes");
                }

            });

            if (parameter.isEmpty()) {
                throw new IncorrectParams("Rate is empty");
            }

            try {
                Double.valueOf(parameter);

            } catch (NumberFormatException e) {
                throw new IncorrectParams("Rate is not double");
            }

            String baseCode = codes[0];
            String targetCode = codes[1];
            Double rate = Double.valueOf(parameter);

            if (baseCode.equalsIgnoreCase(targetCode)) {
                throw new IncorrectParams("Currency pairs match");
            }

            try {
                EXCHANGE_SERVICE.updateRate(baseCode, targetCode, rate);

            } catch (ObjectNotFound e) {
                resp.setStatus(404);
                RENDERER.print(resp, e);
                return;
            }

            ExchangeDTO dto = EXCHANGE_SERVICE.findByCode(baseCode + targetCode);
            RENDERER.print(resp, dto);

        } catch (IncorrectParams e) {
            resp.setStatus(400);
            RENDERER.print(resp, e);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);

        } catch (ObjectNotFound e) {
            resp.setStatus(404);
            RENDERER.print(resp, e);

        } catch (SuccesComplete e) {
            resp.setStatus(201);
            RENDERER.print(resp, e);
        }
    }
}
