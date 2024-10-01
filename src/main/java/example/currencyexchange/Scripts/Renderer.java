package example.currencyexchange.Scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.currencyexchange.Objects.Currencies;
import example.currencyexchange.Objects.Rates;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class Renderer {

    /**
     * Write table to JSON format
     * @param response HttpServletResponse
     * @param currencies List of currencies
     */
    @SneakyThrows
    public static void execute(HttpServletResponse response, List<Currencies> currencies){

        // todo: доработать сигнатуру метода - currencies/rates

        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(currencies.toArray()));
    }
}
