package example.currencyexchange.model.exceptions.status_404;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class ObjectNotFound extends RuntimeException {

    public ObjectNotFound() {
        super("Object not found");
    }

    public ObjectNotFound(String message) {
        super(message);
    }
}
