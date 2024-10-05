package example.currencyexchange.model.exceptions;

public class CurrencyNotFound extends RuntimeException {

    public CurrencyNotFound() {
        super();
    }

    public CurrencyNotFound(String message) {
        super(message);
    }
}
