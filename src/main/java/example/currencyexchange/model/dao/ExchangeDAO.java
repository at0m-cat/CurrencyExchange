package example.currencyexchange.model.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.model.ExchangeRates;
import lombok.SneakyThrows;
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



    private static Double findIndirectExchangeRate(String fromCurrencyCode, String toCurrencyCode) {

        // todo: пересобрать!



        return -1.1;
    }

    private static boolean isValidCurrency(String codeCurrency) {
        Currencies bc = CurrencyDAO.findCodeCurrency(codeCurrency);
        if (bc == null) {
            return false;
        }
        return true;
    }

}
