package example.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CurrencyExchange {

    private final Currency BASE_CURRENCY;
    private final Currency TARGET_CURRENCY;
    private final Double RATE;
    private final Double AMOUNT;
    private final Double CONVERTED_AMOUNT;

}
