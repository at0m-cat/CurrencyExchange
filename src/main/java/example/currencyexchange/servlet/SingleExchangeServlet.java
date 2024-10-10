package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.exceptions.code_201.SuccesComplete;
import example.currencyexchange.model.exceptions.code_400.IncorrectParams;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.service.CurrencyService;
import example.currencyexchange.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Stream;

@WebServlet(value = "/exchangerates/*")
public class SingleExchangeServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getRENDERER();
    private static final ExchangeService EXCHANGE_SERVICE = ExchangeService.getEXCHANGE_SERVICE();
    private static final CurrencyService CURRENCY_SERVICE = CurrencyService.getCURRENCY_SERVICE();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET", "POST" -> super.service(req, resp);
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
                    throw new IncorrectParams("'%s' - Invalid code type".formatted(code));
                }

                if (!code.toUpperCase().equals(code)) {
                    throw new IncorrectParams("%s - Error case, corrected: %s"
                            .formatted(code, code.toUpperCase()));
                }
            });

            if (baseCode.equalsIgnoreCase(targetCode)) {
                throw new IncorrectParams("Currency pairs match");
            }

            ExchangeDTO DTO = EXCHANGE_SERVICE.getByCode(baseCode + targetCode);
            RENDERER.print(resp, DTO);

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
                throw new IncorrectParams("rate is not double or null");
            }

            Stream.of(baseCode, targetCode, rateString)
                    .forEach(param ->
                    {
                        if (param == null || param.isEmpty()) {
                            throw new IncorrectParams("params equals empty");
                        }
                    });

            Stream.of(baseCode, targetCode)
                    .forEach(elem ->
                    {
                        if (!elem.equals(elem.toUpperCase())) {
                            throw new IncorrectParams("%s - param case error, correct: UpperCase".formatted(elem));
                        }
                    });

        } catch (IncorrectParams e) {
            resp.setStatus(400);
            RENDERER.print(resp, e);
        }

        try {

             try {
                 CURRENCY_SERVICE.getByCode(baseCode);
                 CURRENCY_SERVICE.getByCode(targetCode);

             } catch (ObjectNotFound e) {
                 resp.setStatus(404);
                 RENDERER.print(resp, e);
             }

            try {
                EXCHANGE_SERVICE.getByCode(baseCode + targetCode);
                throw new ObjectAlreadyExist();

            } catch (ObjectAlreadyExist e) {
                resp.setStatus(409);
                RENDERER.print(resp, e);

            } catch (ObjectNotFound e) {
                CurrencyDTO baseCurrency = CURRENCY_SERVICE.getByCode(baseCode);
                CurrencyDTO targetCurrency = CURRENCY_SERVICE.getByCode(targetCode);
                ExchangeDTO pairs = EXCHANGE_SERVICE.createDto(baseCurrency, targetCurrency, rate);
                EXCHANGE_SERVICE.addToBase(pairs);
                throw new SuccesComplete();
            }

        } catch (ObjectAlreadyExist e) {
            resp.setStatus(409);
            RENDERER.print(resp, e);

        } catch (SuccesComplete e) {
            resp.setStatus(201);
            RENDERER.print(resp, e);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);

        } catch (IncorrectParams e){
            resp.setStatus(400);
            RENDERER.print(resp, e);
        }


    }
}
