package example.currencyexchange.model.exceptions.code_404;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class CurrencyNotFound extends RuntimeException {

    public CurrencyNotFound() {
        super("Currency not found");
    }

    public CurrencyNotFound(String message) {
        super(message);
    }
}
