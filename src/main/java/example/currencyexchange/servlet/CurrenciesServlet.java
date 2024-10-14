package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.exceptions.status_201.SuccesComplete;
import example.currencyexchange.model.exceptions.status_400.IncorrectParams;
import example.currencyexchange.model.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;
import example.currencyexchange.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@WebServlet(value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CurrencyDTO> currencyDTOS = SERVICE.getAll();
            RENDERER.print(response, currencyDTOS);

        } catch (DataBaseNotAvailable e) {
            response.setStatus(500);
            RENDERER.print(response, e);

        } catch (ObjectNotFound e){
            response.setStatus(404);
            RENDERER.print(response, e);
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

            CurrencyDTO currencyDTO = SERVICE.createDto(name, code, sign);
            SERVICE.addToBase(currencyDTO);
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
