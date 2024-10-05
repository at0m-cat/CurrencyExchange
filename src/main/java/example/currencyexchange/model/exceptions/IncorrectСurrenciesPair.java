package example.currencyexchange.model.exceptions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;



@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class IncorrectСurrenciesPair extends RuntimeException {

    private String MESSAGE = "A required form field is missing";

    public IncorrectСurrenciesPair(HttpServletResponse response, String message) {
        super(message);
        this.MESSAGE = message;
        response.setStatus(400);
    }

    public IncorrectСurrenciesPair(HttpServletResponse response) {
        super();
        response.setStatus(400);
    }


}
