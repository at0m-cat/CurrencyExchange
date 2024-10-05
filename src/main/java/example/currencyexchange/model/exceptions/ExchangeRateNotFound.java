package example.currencyexchange.model.exceptions;

public class ExchangeRateNotFound extends RuntimeException {

    public ExchangeRateNotFound() {
        super();
    }

    public ExchangeRateNotFound(String message) {
        super(message);
    }
}
