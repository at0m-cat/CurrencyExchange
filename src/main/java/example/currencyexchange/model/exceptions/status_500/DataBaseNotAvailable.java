package example.currencyexchange.model.exceptions.status_500;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class DataBaseNotAvailable extends RuntimeException {

    public DataBaseNotAvailable() {
        super("Database not available");
    }

    public DataBaseNotAvailable(String message) {
        super(message);
    }

}
