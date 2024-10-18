package example.currencyexchange.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class ObjectAlreadyExistException extends RuntimeException {

    public ObjectAlreadyExistException() {
        super("Object already exist");
    }

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
