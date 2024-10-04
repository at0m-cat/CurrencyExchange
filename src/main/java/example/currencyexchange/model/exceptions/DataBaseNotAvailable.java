package example.currencyexchange.model.exceptions;

public class DataBaseNotAvailable extends Exception {

    public DataBaseNotAvailable() {
        super();
    }

    public DataBaseNotAvailable(String message) {
        super(message);
    }
}
