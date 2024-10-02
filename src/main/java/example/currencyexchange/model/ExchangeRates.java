package example.currencyexchange.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExchangeRates {
    private final int ID;
    private final SingleCurrency BASE_CURRENCY;
    private final SingleCurrency TARGET_CURRENCY;
    private final double RATE;

}


