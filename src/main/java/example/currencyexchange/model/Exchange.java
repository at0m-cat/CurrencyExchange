package example.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Exchange {

    private final Currencies BASE_CURRENCY;
    private final Currencies TARGET_CURRENCY;
    private final Double RATE;
    private final Double AMOUNT;
    private final Double CONVERTED_AMOUNT;

}
