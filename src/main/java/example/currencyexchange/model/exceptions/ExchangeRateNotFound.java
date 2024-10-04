package example.currencyexchange.model.exceptions;

public class ExchangeRateNotFound extends Exception {

    public ExchangeRateNotFound() {
        super();
    }

    public ExchangeRateNotFound(String message) {
        super(message);
    }
}
