package example.currencyexchange.model.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class DataBaseNotAvailable extends RuntimeException {

    public DataBaseNotAvailable(HttpServletResponse response, String message) {
        super(message);
        response.setStatus(500);
    }

    public DataBaseNotAvailable(HttpServletResponse response) {
        super("DataBase not available");
        response.setStatus(500);
    }
}
