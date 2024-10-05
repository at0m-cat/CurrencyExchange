package example.currencyexchange.model.exceptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;


@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class IncorrectСurrenciesPair extends RuntimeException {


    public IncorrectСurrenciesPair(HttpServletResponse response, String message) {
        super(message);
        response.setStatus(400);
    }

    public IncorrectСurrenciesPair(HttpServletResponse response) {
        super("A required form field is missing");
        response.setStatus(400);
    }


}
