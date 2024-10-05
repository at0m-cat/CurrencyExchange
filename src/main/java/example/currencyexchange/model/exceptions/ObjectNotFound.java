package example.currencyexchange.model.exceptions;

public class ObjectNotFound extends RuntimeException {

    public ObjectNotFound() {
        super();
    }

    public ObjectNotFound(String message) {
        super(message);
    }

}
