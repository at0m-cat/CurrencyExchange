package example.currencyexchange.model.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.model.ExchangeRates;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ExchangeDAO {

    /**
     * Method for direct search of currency exchange (AB)
     *
     * @param fromCurrencyCode the code of the currency we are changing
     * @param toCurrencyCode   code of the currency to which we are changing
     * @return Returns Double (min) if there is no direct rate
     */
    @SneakyThrows
    public static Double findExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        String query = """
                SELECT rate FROM exchangerates e
                JOIN currencies c1 ON e.basecurrencyid = c1.id
                JOIN currencies c2 ON e.targetcurrencyid = c2.id
                WHERE c1.code = ? AND c2.code = ?""";

        ResultSet rs = DataBaseConfig.connect(query, fromCurrencyCode, toCurrencyCode);

        if (rs.next()) {
            return rs.getDouble("rate");
        }
        return Double.MIN_VALUE;
    }

    // todo: переделать, написать javadoc
    public static Exchange getEx(String fromCurrencyCode, String toCurrencyCode, Double amount) {

        String[] currencyCodes = {fromCurrencyCode, toCurrencyCode};
        for (String currencyCode : currencyCodes) {
            if (!isValidCurrency(currencyCode)) {
                throw new NullPointerException();
            }
        }

        Currencies bc = CurrencyDAO.findCodeCurrency(fromCurrencyCode);
        Currencies tc = CurrencyDAO.findCodeCurrency(toCurrencyCode);

        if (fromCurrencyCode.equalsIgnoreCase(toCurrencyCode)) {
            return new Exchange(bc, bc, 1.0, amount, amount);
        }


        Double rateExchange = findExchangeRate(fromCurrencyCode, toCurrencyCode);
        if (rateExchange == Double.MIN_VALUE) {
            rateExchange = findExchangeRate(toCurrencyCode, fromCurrencyCode);
            if (rateExchange != Double.MIN_VALUE) {
                rateExchange = 1 / rateExchange;
            }
        }


        // todo: поиск посредника (пересобрать)

        if (rateExchange == Double.MIN_VALUE) {
            rateExchange = findIndirectExchangeRate(fromCurrencyCode, toCurrencyCode);
            if (rateExchange == null) {
                rateExchange = findIndirectExchangeRate(toCurrencyCode, fromCurrencyCode);
            }
        }

        Double convertedAmount = amount / rateExchange;
        Exchange exchange = new Exchange(bc, tc, rateExchange, amount, convertedAmount);
        return exchange;
    }

    // todo: некорректно возвращает значение Double, пересмотреть
    @SneakyThrows
    public static Double findIndirectExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        String query = """
                    SELECT c3.code AS intermediary_currency, e1.rate AS rate_to_intermediary, 
                           e2.rate AS rate_from_intermediary
                    FROM exchangerates e1
                    JOIN exchangerates e2 ON e1.targetcurrencyid = e2.basecurrencyid
                    JOIN currencies c1 ON e1.basecurrencyid = c1.id
                    JOIN currencies c2 ON e2.targetcurrencyid = c2.id
                    JOIN currencies c3 ON e1.targetcurrencyid = c3.id
                    WHERE c1.code = ?
                    AND c2.code = ?
                    AND c1.id != c2.id;
                """;
        ResultSet rs = DataBaseConfig.connect(query, fromCurrencyCode, toCurrencyCode);

        if (rs.next()) {
            Double rateToIntermediary = rs.getDouble("rate_to_intermediary");
            Double rateFromIntermediary = rs.getDouble("rate_from_intermediary");
            return rateFromIntermediary / rateToIntermediary;
        }
        return null;
    }

    private static boolean isValidCurrency(String codeCurrency) {
        Currencies bc = CurrencyDAO.findCodeCurrency(codeCurrency);
        if (bc == null) {
            return false;
        }
        return true;
    }

}
