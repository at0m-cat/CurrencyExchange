package example.currencyexchange.model.exceptions;

public class IncorrectParams extends RuntimeException {
    public IncorrectParams() {
        super();
    }

    public IncorrectParams(String message) {
        super(message);
    }
}
