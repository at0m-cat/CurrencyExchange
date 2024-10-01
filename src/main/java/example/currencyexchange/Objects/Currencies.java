package example.currencyexchange.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Currencies {

    private final Integer ID;
    private final String CODE;
    private final String FULL_NAME;
    private final Integer SIGN;

}
