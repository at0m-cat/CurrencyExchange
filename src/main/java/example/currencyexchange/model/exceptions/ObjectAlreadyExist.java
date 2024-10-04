package example.currencyexchange.model.exceptions;

public class ObjectAlreadyExist extends Exception {

    public ObjectAlreadyExist() {
        super();
    }

    public ObjectAlreadyExist(String message) {
        super(message);
    }
}
