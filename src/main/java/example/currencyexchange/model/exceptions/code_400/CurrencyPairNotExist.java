package example.currencyexchange.model.exceptions.code_400;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;


@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class CurrencyPairNotExist extends RuntimeException {

    public CurrencyPairNotExist() {
        super("Currency pair not exist");
    }


    public CurrencyPairNotExist(String message) {
        super(message);
    }
}
