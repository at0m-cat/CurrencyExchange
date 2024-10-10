package example.currencyexchange.model.exceptions.code_400;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class IncorrectParams extends RuntimeException {

    public IncorrectParams() {
        super("Incorrect parameters");
    }

    public IncorrectParams(String message) {
        super(message);
    }

}
