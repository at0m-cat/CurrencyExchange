package example.currencyexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
@Setter
@JsonPropertyOrder({"id", "base_currency", "target_currency", "rate",})
public final class ExchangeDTO {

    private int id;
    @JsonProperty(value = "base_currency")
    private CurrencyDTO baseCurrency;
    @JsonProperty(value = "target_currency")
    private CurrencyDTO targetCurrency;
    private double RATE;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeDTO that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
