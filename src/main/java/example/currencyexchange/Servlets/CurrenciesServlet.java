package example.currencyexchange.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.currencyexchange.Objects.Currencies;
import example.currencyexchange.Scripts.ParseCurrencies;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "Currencies", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        List<Currencies> currencies = ParseCurrencies.execute();
        if (currencies.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        PrintWriter out = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        out.write(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(currencies));

    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }
}
