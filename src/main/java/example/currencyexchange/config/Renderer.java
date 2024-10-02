package example.currencyexchange.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import java.io.PrintWriter;

public class Renderer {

    /**
     * Generic method for outputting any object in JSON format
     * @param response HttpServletResponse to send the response
     * @param data Object to output in JSON
     * @param <T> Object type
     */
    @SneakyThrows
    public static <T> void printJson(HttpServletResponse response, T data) {
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
        writer.flush();
    }

}
