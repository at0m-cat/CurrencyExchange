package example.currencyexchange.model.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.model.Currencies;
import example.currencyexchange.model.ExchangeRates;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ExchangeRatesDAO {

    /**
     * db name: "currencyexchange",
     * parse from table "exchangerates" and join table "currencies"
     *
     * @return List ExchangeRates ->
     * Base currency identifiers and data. Target currency identifiers and data. Exchange rate.
     */
    public static List<ExchangeRates> getExchangeRate() throws SQLException {
        String query = """
                SELECT e.id AS id, c1.id AS base_id, c1.fullname AS base_name,
                 c1.code AS base_code, c1.sign AS base_sign,c2.id AS target_id,
                 c2.fullname AS target_name, c2.code AS target_code,
                 c2.sign AS target_sign, e.rate
                 FROM exchangerates e
                 JOIN currencies c1 ON e.basecurrencyid = c1.id
                 JOIN currencies c2 ON e.targetcurrencyid = c2.id
                """;
        ResultSet resultSet = DataBaseConfig.connect(query);
        return ExchangeRatesDAO.parsing(resultSet);
    }

    /**
     * Parsing currency in table "exchangerates"
     *
     * @param rs ResultSet
     * @return List ExchangeRates
     */
    @SneakyThrows
    public static List<ExchangeRates> parsing(ResultSet rs) {
        List<ExchangeRates> exchangeRates = new ArrayList<>();
        while (rs.next()) {
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
                    rs.getInt("id"),
                    baseCurrency,
                    targetCurrency,
                    rs.getDouble("rate")
            );
            exchangeRates.add(exchangeRate);
        }
        return exchangeRates;
    }

    @SneakyThrows
    public static void setExchangeRate(String baseCode, String targetCode, String rate) throws NoSuchMethodException {
        if (findCodeRate(baseCode, targetCode) != null) {
            throw new NoSuchMethodException();
        }
        String query = "SELECT id FROM currencies WHERE code = ?";
        String insertExchangeRateQuery = """
                INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate)
                VALUES (?, ?, ?)""";

        ResultSet baseCurrencyRs = DataBaseConfig.connect(query, baseCode);
        ResultSet targetCurrencyRs = DataBaseConfig.connect(query, targetCode);

        int baseCurrencyId = baseCurrencyRs.getInt("id");
        int targetCurrencyId = targetCurrencyRs.getInt("id");
        DataBaseConfig.connect(insertExchangeRateQuery, baseCurrencyId, targetCurrencyId, Double.valueOf(rate));
    }

    /**
     * searches for an object in database with target and base codes
     *
     * @param baseCode   String base code currency
     * @param targetCode String target code currency
     * @return ExchangeRates object
     */
    @SneakyThrows
    public static ExchangeRates findCodeRate(String baseCode, String targetCode) {
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

        ResultSet rs = DataBaseConfig.connect(query, baseCode, targetCode);
        try {
            return parsing(rs).getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @SneakyThrows
    public static List<ExchangeRates> findExchangeRateAll(String codeCurrency) {
        String query = """
                SELECT e.id, e.rate, c1.id AS base_id,c1.fullname AS base_name,
                 c1.code AS base_code, c1.sign AS base_sign,
                 c2.id AS target_id, c2.fullname AS target_name,
                 c2.code AS target_code, c2.sign AS target_sign
                 FROM exchangerates e
                 JOIN currencies c1 ON e.basecurrencyid = c1.id
                 JOIN currencies c2 ON e.targetcurrencyid = c2.id
                 WHERE c1.code = ?
                """;

        ResultSet rs = DataBaseConfig.connect(query, codeCurrency);
        try {
            return parsing(rs);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
