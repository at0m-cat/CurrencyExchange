package example.currencyexchange.model.exceptions;

public class ObjectAlreadyExist extends RuntimeException {

    public ObjectAlreadyExist() {
        super();
    }

    public ObjectAlreadyExist(String message) {
        super(message);
    }
}
