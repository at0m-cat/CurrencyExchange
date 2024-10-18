package example.currencyexchange.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class DataBaseNotAvailableException extends RuntimeException {

    public DataBaseNotAvailableException() {
        super("Database not available");
    }

    public DataBaseNotAvailableException(String message) {
        super(message);
    }

}
