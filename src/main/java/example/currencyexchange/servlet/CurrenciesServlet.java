package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.ObjectAlreadyExistException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
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
    private static final Renderer renderer = Renderer.getInstance();
    private static final CurrencyService service = CurrencyService.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET", "POST" -> super.service(req, resp);
            default -> {
                resp.setStatus(500);
                renderer.print(resp, new DataBaseNotAvailableException("%s: not available method"
                        .formatted(method)));
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CurrencyDTO> currencyDTOS = service.findAll();
            renderer.print(response, currencyDTOS);

        } catch (DataBaseNotAvailableException e) {
            response.setStatus(500);
            renderer.print(response, e);

        } catch (ObjectNotFoundException e){
            response.setStatus(404);
            renderer.print(response, e);
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
                            throw new IncorrectParamsException("params equals null or empty");
                        }
                    });

            if (code != code.toUpperCase()){
                throw new IncorrectParamsException("%s - incorrect case code, correct: UpperCase".formatted(code));
            }

            CurrencyDTO currencyDTO = service.createDto(name, code, sign);
            service.save(currencyDTO);
            resp.setStatus(201);
            renderer.print(resp, service.findByCode(currencyDTO.getCode()));

        } catch (IncorrectParamsException e) {
            resp.setStatus(400);
            renderer.print(resp, e);

        } catch (DataBaseNotAvailableException e) {
            resp.setStatus(500);
            renderer.print(resp, e);

        } catch (ObjectAlreadyExistException e) {
            resp.setStatus(409);
            renderer.print(resp, e);

        }
    }
}
