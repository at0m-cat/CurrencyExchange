package example.currencyexchange.model.exceptions.status_409;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class ObjectAlreadyExist extends RuntimeException {

    public ObjectAlreadyExist() {
        super("Object already exist");
    }

    public ObjectAlreadyExist(String message) {
        super(message);
    }
}
