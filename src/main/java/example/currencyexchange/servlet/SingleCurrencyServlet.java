package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.exceptions.code_201.SuccesComplete;
import example.currencyexchange.model.exceptions.code_400.IncorrectParams;
import example.currencyexchange.model.exceptions.code_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Stream;

@WebServlet(value = "/currencies/*")
public class SingleCurrencyServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getRENDERER();
    private static final CurrencyService SERVICE = CurrencyService.getCURRENCY_SERVICE();

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
            String[] args = req.getPathInfo().split("/");
            if (args.length != 2) {
                throw new IncorrectParams("Incorrect code of currency");
            }

            String code = args[1];
            if (code.length() != 3) {
                throw new IncorrectParams("Incorrect length code of currency");
            }

            CurrencyDTO currencyDTO = SERVICE.getByCode(code);

            if (currencyDTO == null) {
                throw new ObjectNotFound("Currency not found");
            }

            RENDERER.print(resp, currencyDTO);

        } catch (IncorrectParams e) {
            resp.setStatus(400);
            RENDERER.print(resp, e);

        } catch (ObjectNotFound e) {
            resp.setStatus(404);
            RENDERER.print(resp, e);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String code = req.getParameter("code");
            String name = req.getParameter("name");
            String sign = req.getParameter("sign");

            Stream.of(code, name, sign)
                    .forEach(elem ->
                    {
                        if (elem == null || elem.isEmpty()) {
                            throw new IncorrectParams("params equals null or empty");
                        }
                    });


            if (code != code.toUpperCase()){
                throw new IncorrectParams("%s - incorrect case code, correct: UpperCase".formatted(code));
            }

            try {
                CurrencyDTO currencyDTO = SERVICE.createDto(name, code, Integer.valueOf(sign));
                SERVICE.addToBase(currencyDTO);
            } catch (NumberFormatException e){
                throw new IncorrectParams("%s - incorrect param sign".formatted(sign));
            }

            throw new SuccesComplete();

        } catch (IncorrectParams e) {
            resp.setStatus(400);
            RENDERER.print(resp, e);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);

        } catch (ObjectAlreadyExist e) {
            resp.setStatus(409);
            RENDERER.print(resp, e);

        } catch (SuccesComplete e){
            resp.setStatus(201);
            RENDERER.print(resp, e);
        }
    }
}
