package example.currencyexchange.model.dao;

import example.currencyexchange.model.SingleCurrency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.sql.ResultSet;
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

    public static ExchangeRates findCodeRates(List<ExchangeRates> exchangeRates, String base_code, String target_code){

        for (ExchangeRates exchangeRate : exchangeRates) {
            String bc = exchangeRate.getBASE_CURRENCY().getCODE();
            String tar = exchangeRate.getTARGET_CURRENCY().getCODE();

            if (base_code.equals(bc) && target_code.equals(tar)){
                return exchangeRate;
            }
        }
        return null;
    }
}


