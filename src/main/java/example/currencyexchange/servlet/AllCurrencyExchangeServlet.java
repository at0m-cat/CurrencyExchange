package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/exchangerates")
public class AllCurrencyExchangeServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getRENDERER();
    private static final ExchangeService SERVICE = ExchangeService.getEXCHANGE_SERVICE();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case "GET" -> super.service(req, resp);
            default ->
            {
                resp.setStatus(500);
                RENDERER.print(resp, new DataBaseNotAvailable("%s: not available method"
                        .formatted(method)));

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try
        {
            List<ExchangeDTO> exchangeDTOS = SERVICE.getAll();
            RENDERER.print(resp, exchangeDTOS);

        } catch (DataBaseNotAvailable e) {
            resp.setStatus(500);
            RENDERER.print(resp, e);
        }
    }
}
