package example.currencyexchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonPropertyOrder({"id", "base_currency", "target_currency", "rate",})
public final class ExchangeDTO {

    private int id;
    @JsonProperty(value = "base_currency")
    private CurrencyDTO baseCurrency;
    @JsonProperty(value = "target_currency")
    private CurrencyDTO targetCurrency;
    private BigDecimal RATE;

}
