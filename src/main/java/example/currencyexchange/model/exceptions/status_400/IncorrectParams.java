package example.currencyexchange.model.exceptions.status_400;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class IncorrectParams extends RuntimeException {

    public IncorrectParams() {
        super("Incorrect parameters");
    }

    public IncorrectParams(String message) {
        super(message);
    }

}
