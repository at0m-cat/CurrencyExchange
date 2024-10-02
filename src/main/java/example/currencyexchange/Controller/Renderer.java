package example.currencyexchange.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static void execute(HttpServletResponse response, List<?> list){
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(list));
    }

}
