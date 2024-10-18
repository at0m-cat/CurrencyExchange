package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
import example.currencyexchange.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    private static final Renderer RENDERER = Renderer.getInstance();
    private static final CurrencyService SERVICE = CurrencyService.getInstance();


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

            CurrencyDTO currencyDTO = SERVICE.findByCode(code);

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
}
