package example.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Exchange {
    private final int ID;
    private final Currency BASE_CURRENCY;
    private final Currency TARGET_CURRENCY;
    private final BigDecimal RATE;

}


