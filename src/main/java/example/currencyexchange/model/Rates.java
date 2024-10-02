package example.currencyexchange.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Rates {
    private final Integer ID;
    private final String BASE_CURRENCY_ID;
    private final String TARGET_CURRENCY_ID;
    private final Double RATE;

}
