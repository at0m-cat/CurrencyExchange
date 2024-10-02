package example.currencyexchange.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExchangeRates {
    private final int ID;
    private final Currencies BASE_CURRENCY;
    private final Currencies TARGET_CURRENCY;
    private final double RATE;

}


