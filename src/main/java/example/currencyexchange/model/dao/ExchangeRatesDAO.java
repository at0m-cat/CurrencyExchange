package example.currencyexchange.model.dao;

import example.currencyexchange.model.Currencies;
import example.currencyexchange.model.ExchangeRates;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDAO {

    /**
     * Parsing currency in table "exchangerates"
     * @param rs ResultSet
     * @return List ExchangeRates
     */
    @SneakyThrows
    public static List<ExchangeRates> parsing(ResultSet rs){
        List<ExchangeRates> exchangeRates = new ArrayList<>();
        while(rs.next()){
            Currencies baseCurrency = new Currencies(
                    rs.getString("base_name"),
                    rs.getString("base_code"),
                    rs.getInt("base_id"),
                    rs.getInt("base_sign")
            );
            Currencies targetCurrency = new Currencies(
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

    /**
     * searches for an object in list "ExchangeRates" with target and base codes
     * @param exchangeRates List ExchangeRates
     * @param base_code String base code currency
     * @param target_code String target code currency
     * @return ExchangeRates object
     */
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
