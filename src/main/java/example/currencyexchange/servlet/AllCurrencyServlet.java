package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/currencies")
public class AllCurrencyServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getRENDERER();
    private static final CurrencyService SERVICE = CurrencyService.getCURRENCY_SERVICE();

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
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CurrencyDTO> DTO = SERVICE.getAll();
            RENDERER.print(response, DTO);

        } catch (DataBaseNotAvailable e) {
            response.setStatus(500);
            RENDERER.print(response, e);

        } catch (ObjectNotFound e){
            response.setStatus(404);
            RENDERER.print(response, e);
        }
    }
}
