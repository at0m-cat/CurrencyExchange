package example.currencyexchange.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ExchangeDAO implements DAOInterface<Exchange, String> {

    @Getter
    private static final ExchangeDAO INSTANCE = new ExchangeDAO();
    private static final DataBaseConfig DB = DataBaseConfig.getCONNCECTION();


    private ExchangeDAO() {
    }


    @Override
    public Exchange getModel(String code) throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }

    private Exchange building(ResultSet rs) throws DataBaseNotAvailable {
        try {

            int id = rs.getInt("id");
            double rate = rs.getDouble("rate");

            Integer bId = rs.getInt("base_id");
            String bName = rs.getString("base_name");
            String bCode = rs.getString("base_code");
            Integer bSign = rs.getInt("base_sign");

            Integer tId = rs.getInt("target_id");
            String tName = rs.getString("target_name");
            String tCode = rs.getString("target_code");
            Integer tSign = rs.getInt("target_sign");

            Currency baseCurrency = new Currency(bName, bCode, bId, bSign);
            Currency targetCurrency = new Currency(tName, tCode, tId, tSign);

            return new Exchange(id, baseCurrency, targetCurrency, rate);

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
    }

//        String query = """
//            SELECT e.id, e.rate,
//                   c1.id AS base_id, c1.fullname AS base_name, c1.code AS base_code, c1.sign AS base_sign,
//                   c2.id AS target_id, c2.fullname AS target_name, c2.code AS target_code, c2.sign AS target_sign,
//                   CASE WHEN c1.code = ? THEN 'base' ELSE 'target' END AS match_type
//            FROM exchangerates e
//            JOIN currencies c1 ON e.basecurrencyid = c1.id
//            JOIN currencies c2 ON e.targetcurrencyid = c2.id
//            WHERE c1.code = ? OR c2.code = ?
//        """;

    public Exchange getPairsByCode(String baseCode, String targetCode) throws ObjectNotFound, DataBaseNotAvailable {
        String query = """
                WITH RECURSIVE exchange_pairs AS (
                    SELECT DISTINCT e.id, e.rate,
                                    c1.id AS base_id, c1.fullname AS base_name, c1.code AS base_code, c1.sign AS base_sign,
                                    c2.id AS target_id, c2.fullname AS target_name, c2.code AS target_code, c2.sign AS target_sign,
                                    'direct' AS match_type
                    FROM exchangerates e
                    JOIN currencies c1 ON e.basecurrencyid = c1.id
                    JOIN currencies c2 ON e.targetcurrencyid = c2.id
                    WHERE c1.code = ? AND c2.code = ?
                
                    UNION ALL
                
                    SELECT DISTINCT e.id, 1/e.rate AS rate,
                                    c2.id AS base_id, c2.fullname AS base_name, c2.code AS base_code, c2.sign AS base_sign,
                                    c1.id AS target_id, c1.fullname AS target_name, c1.code AS target_code, c1.sign AS target_sign,
                                    'reverse' AS match_type
                    FROM exchangerates e
                    JOIN currencies c1 ON e.basecurrencyid = c1.id
                    JOIN currencies c2 ON e.targetcurrencyid = c2.id
                    WHERE c1.code = ? AND c2.code = ?
                
                    UNION ALL
                
                    SELECT DISTINCT e1.id, e1.rate * e2.rate AS rate,
                                    c1.id AS base_id, c1.fullname AS base_name, c1.code AS base_code, c1.sign AS base_sign,
                                    c3.id AS target_id, c3.fullname AS target_name, c3.code AS target_code, c3.sign AS target_sign,
                                    'intermediary' AS match_type
                    FROM exchangerates e1
                    JOIN currencies c1 ON e1.basecurrencyid = c1.id
                    JOIN currencies intermediary ON e1.targetcurrencyid = intermediary.id
                    JOIN exchangerates e2 ON e2.basecurrencyid = intermediary.id
                    JOIN currencies c3 ON e2.targetcurrencyid = c3.id
                    WHERE c1.code = ? AND c3.code = ?
                      AND intermediary.code != c1.code
                      AND intermediary.code != c3.code
                
                      UNION ALL
                
                      SELECT DISTINCT e1.id, 1/(e2.rate * e1.rate) AS rate,
                                    c3.id AS base_id, c3.fullname AS base_name, c3.code AS base_code, c3.sign AS base_sign,
                                    c1.id AS target_id, c1.fullname AS target_name, c1.code AS target_code, c1.sign AS target_sign,
                                    'reverse_intermediary' AS match_type
                    FROM exchangerates e1
                    JOIN currencies c1 ON e1.basecurrencyid = c1.id
                    JOIN currencies intermediary ON e1.targetcurrencyid = intermediary.id
                    JOIN exchangerates e2 ON e2.basecurrencyid = intermediary.id
                    JOIN currencies c3 ON e2.targetcurrencyid = c3.id
                    WHERE c1.code = ? AND c3.code = ?
                      AND intermediary.code != c1.code
                      AND intermediary.code != c3.code
                )
                
                SELECT *
                FROM exchange_pairs
                LIMIT 1;
                """;

        ResultSet rs = DB.connect(query
                , baseCode, targetCode
                , targetCode, baseCode
                , baseCode, targetCode
                , targetCode, baseCode
        );

        try {
            if (rs.next()) {
                return building(rs);
            }

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }

        throw new ObjectNotFound("Currency pair not found");
    }

    @Override
    public Exchange getModel(String baseCode, String targetCode) throws ObjectNotFound, DataBaseNotAvailable {

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

        ResultSet rs = DB.connect(query, baseCode, targetCode);

        try {
            if (rs.next()) {
                return building(rs);
            }

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
        throw new ObjectNotFound("Currency pair not found");
    }

    @Override
    public List<Exchange> getModelAll() {

        String query = """
                SELECT e.id AS id, c1.id AS base_id, c1.fullname AS base_name,
                 c1.code AS base_code, c1.sign AS base_sign,c2.id AS target_id,
                 c2.fullname AS target_name, c2.code AS target_code,
                 c2.sign AS target_sign, e.rate
                 FROM exchangerates e
                 JOIN currencies c1 ON e.basecurrencyid = c1.id
                 JOIN currencies c2 ON e.targetcurrencyid = c2.id
                """;

        ResultSet rs = DB.connect(query);

        try {
            List<Exchange> result = new ArrayList<>();
            while (rs.next()) {
                result.add(building(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
    }

    @Override
    public void addModel(String name, String code, String sign) throws DataBaseNotAvailable {
    }


    public void addToBase(ExchangeDTO pairsDto, double rate) throws DataBaseNotAvailable {
        String query = """
                INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate)
                VALUES (?, ?, ?)""";

        int baseId = pairsDto.getBaseCurrency().getId();
        int targetId = pairsDto.getTargetCurrency().getId();

        DB.connect(query, baseId, targetId, rate);

    }
}