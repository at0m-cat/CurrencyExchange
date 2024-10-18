package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.exceptions.status_201.SuccesComplete;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
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
                renderer.print(resp, new DataBaseNotAvailable("%s: not available method"
                        .formatted(method)));
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CurrencyDTO> currencyDTOS = service.findAll();
            renderer.print(response, currencyDTOS);

        } catch (DataBaseNotAvailable e) {
            response.setStatus(500);
            renderer.print(response, e);

        } catch (ObjectNotFound e){
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
                            throw new IncorrectParams("params equals null or empty");
                        }
                    });

            if (code != code.toUpperCase()){
                throw new IncorrectParams("%s - incorrect case code, correct: UpperCase".formatted(code));
            }

            CurrencyDTO currencyDTO = service.createDto(name, code, sign);
            service.save(currencyDTO);
            throw new SuccesComplete();

        } catch (IncorrectParams e) {
            resp.setStatus(400);
            renderer.print(resp, e);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            renderer.print(resp, e);

        } catch (ObjectAlreadyExist e) {
            resp.setStatus(409);
            renderer.print(resp, e);

        } catch (SuccesComplete e){
            resp.setStatus(201);
            renderer.print(resp, e);
        }
    }
}
