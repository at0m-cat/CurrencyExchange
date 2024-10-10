package example.currencyexchange.model.exceptions.code_404;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class ObjectNotFound extends RuntimeException {

    public ObjectNotFound() {
        super("Object not found");
    }

    public ObjectNotFound(String message) {
        super(message);
    }
}
