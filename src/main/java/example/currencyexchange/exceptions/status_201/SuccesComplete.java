package example.currencyexchange.exceptions.status_201;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class SuccesComplete extends RuntimeException{

    public SuccesComplete() {
        super("succesful");
    }

    public SuccesComplete(String message) {
        super(message);
    }
}
