package example.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@AllArgsConstructor
@Getter
@Setter
public class CurrencyExchange {

    public CurrencyExchange(){
    }

    private  Currency baseCurrency;
    private  Currency targetCurrency;
    private  Double rate;
    private  Double amount;
    private  Double convertedAmount;

}
