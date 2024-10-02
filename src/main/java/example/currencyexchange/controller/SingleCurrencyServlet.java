package example.currencyexchange.controller;
import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.dao.CurrencyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import java.io.IOException;
import java.util.NoSuchElementException;

@WebServlet(name = "SingeCurrency", value = "/currencies/*")
public class SingleCurrencyServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        if (!DataBaseConfig.isConnectionValid()){
            Renderer.printErrorJson(resp, String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            return;
        }

        String pathInfo = req.getPathInfo();

        try {
            String[] parts = pathInfo.split("/");
            String code = parts[1];

            Currencies currency = CurrencyDAO.findCodeCurrency(code);

            resp.setContentType("application/json");
            req.setCharacterEncoding("UTF-8");
            Renderer.printJson(resp, currency);

        } catch (ArrayIndexOutOfBoundsException e) {
            Renderer.printErrorJson(resp,String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
        } catch (NoSuchElementException e){
            Renderer.printErrorJson(resp,String.valueOf(HttpServletResponse.SC_NOT_FOUND));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // todo: сверить с базой данных для добавления объекта

//        String fullName = req.getParameter("name");
        String code = req.getParameter("code");
//        Integer id = Integer.valueOf(req.getParameter("id"));
//        Integer sign = Integer.valueOf(req.getParameter("sign"));

        Currencies currencies = CurrencyDAO.findCodeCurrency(code);
        Renderer.printJson(resp, currencies);


//        Currencies currencies = new Currencies(fullName, code, id, sign);
//
//        Renderer.printJson(resp, currencies);

    }
}
