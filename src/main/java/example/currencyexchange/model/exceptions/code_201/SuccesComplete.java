package example.currencyexchange.model.exceptions.code_201;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class SuccesComplete extends RuntimeException{

    public SuccesComplete() {
        super("succesful");
    }

    public SuccesComplete(String message) {
        super(message);
    }
}
