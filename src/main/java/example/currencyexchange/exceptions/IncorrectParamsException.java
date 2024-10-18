package example.currencyexchange.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class IncorrectParamsException extends RuntimeException {

    public IncorrectParamsException() {
        super("Incorrect parameters");
    }

    public IncorrectParamsException(String message) {
        super(message);
    }

}
