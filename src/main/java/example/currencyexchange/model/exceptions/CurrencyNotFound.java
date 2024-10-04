package example.currencyexchange.model.exceptions;

public class CurrencyNotFound extends Exception {

    public CurrencyNotFound() {
        super();
    }

    public CurrencyNotFound(String message) {
        super(message);
    }
}
