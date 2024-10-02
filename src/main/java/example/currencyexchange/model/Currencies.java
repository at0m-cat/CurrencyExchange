package example.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Currencies {
    private final String FULL_NAME;
    private final String CODE;
    private final Integer ID;
    private final Integer SIGN;

}
