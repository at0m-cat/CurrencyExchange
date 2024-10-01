package example.currencyexchange.Scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.currencyexchange.Objects.Currencies;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.util.List;

public class Renderer {

    /**
     * Write table to JSON format
     * @param response HttpServletResponse
     * @param currencies List of currencies
     */
    @SneakyThrows
    public static void execute(HttpServletResponse response, List<Currencies> currencies){

        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(currencies));

    }

}
