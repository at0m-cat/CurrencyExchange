package example.currencyexchange.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.currencyexchange.model.Error;
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

    @SneakyThrows
    public static void printErrorJson(HttpServletResponse response, int errorType) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.setStatus(errorType);
        String message = getMessage(errorType);

        Error errorObj = new Error(message);
        printJson(response, errorObj);
    }

    private static String getMessage(int errorType) {
        String message;
        switch (errorType) {
            case 500 -> message = "DataBase unavailable";
            case 400 -> message = "A required form field is missing" +
                    " or currency code is missing from the address";
            case 404 -> message = "Not found";
            case 409 -> message = "A currency with this code already exists";
            case 201 -> message = "Success";
            default -> message = String.valueOf(errorType);

        }
        return message;
    }

}
