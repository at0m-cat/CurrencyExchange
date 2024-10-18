package example.currencyexchange.servlet;

import example.currencyexchange.config.Renderer;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
import example.currencyexchange.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    private static final Renderer renderer = Renderer.getInstance();
    private static final CurrencyService service = CurrencyService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String[] args = req.getPathInfo().split("/");
            if (args.length != 2) {
                throw new IncorrectParamsException("Incorrect code of currency");
            }

            String code = args[1];
            if (code.length() != 3) {
                throw new IncorrectParamsException("Incorrect length code of currency");
            }

            CurrencyDTO currencyDTO = service.findByCode(code);

            if (currencyDTO == null) {
                throw new ObjectNotFoundException("Currency not found");
            }

            renderer.print(resp, currencyDTO);

        } catch (IncorrectParamsException e) {
            resp.setStatus(400);
            renderer.print(resp, e);

        } catch (ObjectNotFoundException e) {
            resp.setStatus(404);
            renderer.print(resp, e);

        } catch (DataBaseNotAvailableException e) {
            resp.setStatus(500);
            renderer.print(resp, e);
        }
    }
}
