package example.currencyexchange.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.currencyexchange.model.SingleCurrency;
import example.currencyexchange.model.dao.ExchangeRates;
import example.currencyexchange.model.dao.Rates;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import java.io.PrintWriter;
import java.util.List;

public class Renderer {

    /**
     * Write table to JSON format
     * @param response HttpServletResponse
     * @param list List data
     */
    @SneakyThrows
    public static void printList(HttpServletResponse response, List<?> list){
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(list));
    }
    @SneakyThrows
    public static void printCurrency(HttpServletResponse response, SingleCurrency singleCurrency){
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(singleCurrency));
    }

    @SneakyThrows
    public static void printExchangeRates(HttpServletResponse response, ExchangeRates rates){
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(rates));

    }

}
