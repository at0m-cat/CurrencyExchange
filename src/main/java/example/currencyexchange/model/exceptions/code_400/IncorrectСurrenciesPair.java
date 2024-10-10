package example.currencyexchange.model.exceptions.code_400;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletResponse;


@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class IncorrectСurrenciesPair extends RuntimeException {

    public IncorrectСurrenciesPair(){
        super("A required form field is missing");
    }

    public IncorrectСurrenciesPair(String message) {
        super(message);
    }



}
