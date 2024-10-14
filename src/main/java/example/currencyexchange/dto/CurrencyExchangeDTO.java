package example.currencyexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import example.currencyexchange.service.CurrencyExchangeService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonPropertyOrder({"base_currency", "target_currency", "rate", "amount", "converted_amount"})
public class CurrencyExchangeDTO {

    public CurrencyExchangeDTO(){
    }

    @JsonProperty(value = "base_currency")
    private CurrencyDTO baseCurrency;
    @JsonProperty(value = "target_currency")
    private CurrencyDTO targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    @JsonProperty(value = "converted_amount")
    private BigDecimal convertedAmount;
    
}
