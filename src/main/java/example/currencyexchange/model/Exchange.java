package example.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Exchange {
    private final int id;
    private final Currency baseCurrency;
    private final Currency targetCurrency;
    private final BigDecimal rate;

}


