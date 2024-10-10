package example.currencyexchange.model.exceptions.code_409;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class ObjectAlreadyExist extends RuntimeException {

    public ObjectAlreadyExist() {
        super("Object already exist");
    }

    public ObjectAlreadyExist(String message) {
        super(message);
    }
}
