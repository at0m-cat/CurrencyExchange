package example.currencyexchange.model.exceptions;

public class DataBaseNotAvailable extends RuntimeException {

    public DataBaseNotAvailable() {
        super();
    }

    public DataBaseNotAvailable(String message) {
        super(message);
    }
}
