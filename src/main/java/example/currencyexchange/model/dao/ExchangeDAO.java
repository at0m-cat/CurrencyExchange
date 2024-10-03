package example.currencyexchange.model.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.Renderer;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.model.ExchangeRates;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ExchangeDAO {

    @SneakyThrows
    public static Exchange getExchange(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        String query = """
                SELECT e.id, e.rate, c1.id AS base_id,c1.fullname AS base_name,
                 c1.code AS base_code, c1.sign AS base_sign,
                 c2.id AS target_id, c2.fullname AS target_name,
                 c2.code AS target_code, c2.sign AS target_sign
                 FROM exchangerates e
                 JOIN currencies c1 ON e.basecurrencyid = c1.id
                 JOIN currencies c2 ON e.targetcurrencyid = c2.id
                 WHERE c1.code = ? AND c2.code = ?
                """;

        ResultSet rs = DataBaseConfig.connect(query, fromCurrencyCode.toUpperCase(), toCurrencyCode.toUpperCase());

        return parsing(rs, amount);
    }

    @SneakyThrows
    public static Exchange parsing(ResultSet rs, Double amount) {

        if (rs.next()) {
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
            Exchange exchange = new Exchange(
                    baseCurrency,
                    targetCurrency,
                    rs.getDouble("rate"),
                    amount,
                    (amount / rs.getDouble("rate"))
            );
            return exchange;
        }
        return null;
    }

    @SneakyThrows
    public static Exchange findExchange(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        String query = """
                SELECT e.id, e.rate, c1.id AS base_id,c1.fullname AS base_name,
                 c1.code AS base_code, c1.sign AS base_sign,
                 c2.id AS target_id, c2.fullname AS target_name,
                 c2.code AS target_code, c2.sign AS target_sign
                 FROM exchangerates e
                 JOIN currencies c1 ON e.basecurrencyid = c1.id
                 JOIN currencies c2 ON e.targetcurrencyid = c2.id
                 WHERE c1.code = ? AND c2.code = ?
                """;
        ResultSet rs = DataBaseConfig.connect(query, fromCurrencyCode.toUpperCase(), toCurrencyCode.toUpperCase());

       try{
           return parsing(rs, amount);
       } catch (NullPointerException e){
           return null;
       }
    }

}
