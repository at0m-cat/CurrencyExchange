package example.currencyexchange.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleCurrency {

    private final String FULL_NAME;
    private final String CODE;
    private final Integer ID;
    private final Integer SIGN;

}
