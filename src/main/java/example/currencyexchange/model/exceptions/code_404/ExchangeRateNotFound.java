package example.currencyexchange.model.exceptions.code_404;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class ExchangeRateNotFound extends RuntimeException {

    public ExchangeRateNotFound() {
        super("Exchange rate not found");
    }

    public ExchangeRateNotFound(String message) {
        super(message);
    }
}
