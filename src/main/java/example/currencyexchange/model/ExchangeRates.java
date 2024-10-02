package example.currencyexchange.model;

import example.currencyexchange.model.dao.Currencies;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ExchangeRates {

    private final int ID;
    private final SingleCurrency BASE_CURRENCY;
    private final SingleCurrency TARGET_CURRENCY;
    private final double RATE;


    @SneakyThrows
    public static List<ExchangeRates> parsing(ResultSet rs){
        List<ExchangeRates> exchangeRates = new ArrayList<>();
        while(rs.next()){
            SingleCurrency baseCurrency = new SingleCurrency(
                    rs.getString("base_name"),
                    rs.getString("base_code"),
                    rs.getInt("base_id"),
                    rs.getInt("base_sign")
            );
            SingleCurrency targetCurrency = new SingleCurrency(
                    rs.getString("target_name"),
                    rs.getString("target_code"),
                    rs.getInt("target_id"),
                    rs.getInt("target_sign")
            );

            ExchangeRates exchangeRate = new ExchangeRates(
                    rs.getInt("rate_id"),
                    baseCurrency,
                    targetCurrency,
                    rs.getDouble("rate")
            );
            exchangeRates.add(exchangeRate);
        }
        return exchangeRates;
    }
}


