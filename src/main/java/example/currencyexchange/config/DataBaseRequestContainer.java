package example.currencyexchange.config;

import lombok.Getter;

public class DataBaseRequestContainer {

    @Getter
    private static final DataBaseRequestContainer REQUEST_CONTAINER = new DataBaseRequestContainer();

    private DataBaseRequestContainer() {
    }

    public final String getAllCurrency = "SELECT * FROM currencies";

    public final String getCurrencyByCode = "SELECT * FROM currencies WHERE code = ?";

    public final String addCurrency = "INSERT INTO currencies (fullname, code, sign) VALUES (?, ?, ?)";

    public final String addExchange = "INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES (?, ?, ?)";

    public final String updateRate = "UPDATE exchangerates SET rate = ? WHERE basecurrencyid = ? AND targetcurrencyid = ?";

    public final String getExchangeAll = """
            SELECT e.id AS id,
                   c1.id AS base_id,
                   c1.fullname AS base_name,
                   c1.code AS base_code,
                   c1.sign AS base_sign,
                   c2.id AS target_id,
                   c2.fullname AS target_name,
                   c2.code AS target_code,
                   c2.sign AS target_sign, e.rate
            FROM exchangerates e
            JOIN currencies c1 ON e.basecurrencyid = c1.id
            JOIN currencies c2 ON e.targetcurrencyid = c2.id
            """;

    public final String getExchangeByCodePair = """
            SELECT e.id,
                   e.rate,
                   c1.id AS base_id,
                   c1.fullname AS base_name,
                   c1.code AS base_code,
                   c1.sign AS base_sign,
                   c2.id AS target_id,
                   c2.fullname AS target_name,
                   c2.code AS target_code,
                   c2.sign AS target_sign
            FROM exchangerates e
            JOIN currencies c1 ON e.basecurrencyid = c1.id
            JOIN currencies c2 ON e.targetcurrencyid = c2.id
            WHERE c1.code = ? AND c2.code = ?
            """;

    public final String exchangeRequest = """
            WITH RECURSIVE exchange_pairs AS (
            
                -- Прямой курс: baseCurrency -> targetCurrency
                SELECT DISTINCT e.id,
                                e.rate,
                                c1.id AS base_id,
                                c1.fullname AS base_name,
                                c1.code AS base_code,
                                c1.sign AS base_sign,
                                c2.id AS target_id,
                                c2.fullname AS target_name,
                                c2.code AS target_code,
                                c2.sign AS target_sign
                FROM exchangerates e
                JOIN currencies c1 ON e.basecurrencyid = c1.id
                JOIN currencies c2 ON e.targetcurrencyid = c2.id
                WHERE c1.code = ? AND c2.code = ?
            
                UNION ALL
            
                -- Обратный курс: targetCurrency -> baseCurrency
                SELECT DISTINCT e.id,
                                ROUND(1 / e.rate, 4) AS rate,
                                c2.id AS base_id,
                                c2.fullname AS base_name,
                                c2.code AS base_code,
                                c2.sign AS base_sign,
                                c1.id AS target_id,
                                c1.fullname AS target_name,
                                c1.code AS target_code,
                                c1.sign AS target_sign
                FROM exchangerates e
                JOIN currencies c1 ON e.basecurrencyid = c1.id
                JOIN currencies c2 ON e.targetcurrencyid = c2.id
                WHERE c1.code = ? AND c2.code = ?
            
                UNION ALL
            
                -- Курс через посредника: baseCurrency -> intermediary -> targetCurrency
                SELECT DISTINCT e1.id,
                                ROUND(e1.rate * e2.rate, 4) AS rate,
                                c1.id AS base_id,
                                c1.fullname AS base_name,
                                c1.code AS base_code,
                                c1.sign AS base_sign,
                                c3.id AS target_id,
                                c3.fullname AS target_name,
                                c3.code AS target_code,
                                c3.sign AS target_sign
                FROM exchangerates e1
                JOIN currencies c1 ON e1.basecurrencyid = c1.id
                JOIN currencies intermediary ON e1.targetcurrencyid = intermediary.id
                JOIN exchangerates e2 ON e2.basecurrencyid = intermediary.id
                JOIN currencies c3 ON e2.targetcurrencyid = c3.id
                WHERE c1.code = ? AND c3.code = ?
                  AND intermediary.code != c1.code
                  AND intermediary.code != c3.code
            
                UNION ALL
            
                -- Обратный курс через посредника: targetCurrency -> intermediary -> baseCurrency
                SELECT DISTINCT e1.id,
                                ROUND(1 / (e2.rate * e1.rate), 4) AS rate,
                                c3.id AS base_id,
                                c3.fullname AS base_name,
                                c3.code AS base_code,
                                c3.sign AS base_sign,
                                c1.id AS target_id,
                                c1.fullname AS target_name,
                                c1.code AS target_code,
                                c1.sign AS target_sign
                FROM exchangerates e1
                JOIN currencies c1 ON e1.basecurrencyid = c1.id
                JOIN currencies intermediary ON e1.targetcurrencyid = intermediary.id
                JOIN exchangerates e2 ON e2.basecurrencyid = intermediary.id
                JOIN currencies c3 ON e2.targetcurrencyid = c3.id
                WHERE c1.code = ? AND c3.code = ?
                  AND intermediary.code != c1.code
                  AND intermediary.code != c3.code
            
                UNION ALL
            
                -- Прямой курс baseCurrency -> обратный курс intermediary -> targetCurrency
                SELECT DISTINCT e1.id,
                                ROUND(e1.rate / e2.rate, 4) AS rate,
                                c1.id AS base_id,
                                c1.fullname AS base_name,
                                c1.code AS base_code,
                                c1.sign AS base_sign,
                                c3.id AS target_id,
                                c3.fullname AS target_name,
                                c3.code AS target_code,
                                c3.sign AS target_sign
                FROM exchangerates e1
                JOIN currencies c1 ON e1.basecurrencyid = c1.id
                JOIN currencies intermediary ON e1.targetcurrencyid = intermediary.id
                JOIN exchangerates e2 ON e2.targetcurrencyid = intermediary.id
                JOIN currencies c3 ON e2.basecurrencyid = c3.id
                WHERE c1.code = ? AND c3.code = ?
                  AND intermediary.code != c1.code
                  AND intermediary.code != c3.code
            
                UNION ALL
            
                -- Обратный курс baseCurrency -> прямой курс intermediary -> targetCurrency
                SELECT DISTINCT e1.id,
                                ROUND(e2.rate / e1.rate, 4) AS rate,
                                c1.id AS base_id,
                                c1.fullname AS base_name,
                                c1.code AS base_code,
                                c1.sign AS base_sign,
                                c3.id AS target_id,
                                c3.fullname AS target_name,
                                c3.code AS target_code,
                                c3.sign AS target_sign
                FROM exchangerates e1
                JOIN currencies c1 ON e1.basecurrencyid = c1.id
                JOIN currencies intermediary ON e1.targetcurrencyid = intermediary.id
                JOIN exchangerates e2 ON e2.basecurrencyid = intermediary.id
                JOIN currencies c3 ON e2.targetcurrencyid = c3.id
                WHERE c1.code = ? AND c3.code = ?
                  AND intermediary.code != c1.code
                  AND intermediary.code != c3.code
            )
            
            SELECT * FROM exchange_pairs LIMIT 1""";
}
