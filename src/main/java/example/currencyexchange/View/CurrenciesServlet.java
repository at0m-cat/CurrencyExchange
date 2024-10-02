package example.currencyexchange.View;
import example.currencyexchange.Model.Currencies;
import example.currencyexchange.Controller.DataBaseFunction;
import example.currencyexchange.Controller.Renderer;
import example.currencyexchange.Model.Rates;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.SneakyThrows;
import java.sql.ResultSet;
import java.util.List;

@WebServlet(name = "Currencies", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {


    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Currencies> currenciesList = DataBaseFunction.getCurrencies();
        Renderer.execute(response, currenciesList);

//        List<Rates> ratesList = DataBaseFunction.getRates();
//        Renderer.execute(response, ratesList);

    }


    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }

}
