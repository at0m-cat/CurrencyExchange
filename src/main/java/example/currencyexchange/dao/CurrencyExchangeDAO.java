package example.currencyexchange.dao;

import example.currencyexchange.config.DataBaseConnect;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.CurrencyExchange;
import example.currencyexchange.model.exceptions.status_400.IncorrectParams;
import example.currencyexchange.model.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.model.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CurrencyExchangeDAO implements DAOInterface<CurrencyExchange, String> {

    @Getter
    private static final CurrencyExchangeDAO DAO = new CurrencyExchangeDAO();
    private static final DataBaseConnect DB = DataBaseConnect.getCONNCECTION();

    private CurrencyExchangeDAO() {
    }

    @Override
    public CurrencyExchange building(ResultSet rs)
            throws DataBaseNotAvailable {
        try {

            CurrencyExchange currencyExchange = new CurrencyExchange();
            Double rate = rs.getDouble("rate");

            Integer bId = rs.getInt("base_id");
            String bName = rs.getString("base_name");
            String bCode = rs.getString("base_code");
            String bSign = rs.getString("base_sign");

            Integer tId = rs.getInt("target_id");
            String tName = rs.getString("target_name");
            String tCode = rs.getString("target_code");
            String tSign = rs.getString("target_sign");

            Currency baseCurrency = new Currency(bName, bCode, bId, bSign);
            Currency targetCurrency = new Currency(tName, tCode, tId, tSign);

            currencyExchange.setBaseCurrency(baseCurrency);
            currencyExchange.setTargetCurrency(targetCurrency);
            currencyExchange.setRate(rate);

            return currencyExchange;

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
    }

    @Override
    public List<CurrencyExchange> getModelAll() {
        return List.of();
    }

    @Override
    public CurrencyExchange getModel(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }


    @Override
    public CurrencyExchange getModel(String baseCode, String targetCode)
            throws ObjectNotFound, DataBaseNotAvailable {
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
            
                SELECT DISTINCT e.id, ROUND (1 / e.rate, 4) AS rate,
                                c2.id AS base_id, c2.fullname AS base_name, c2.code AS base_code, c2.sign AS base_sign,
                                c1.id AS target_id, c1.fullname AS target_name, c1.code AS target_code, c1.sign AS target_sign,
                                'reverse' AS match_type
                FROM exchangerates e
                JOIN currencies c1 ON e.basecurrencyid = c1.id
                JOIN currencies c2 ON e.targetcurrencyid = c2.id
                WHERE c1.code = ? AND c2.code = ?
            
                UNION ALL
            
                SELECT DISTINCT e1.id, ROUND (e1.rate * e2.rate, 4) AS rate,
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
            
                SELECT DISTINCT e1.id, ROUND (1 / (e2.rate * e1.rate), 4) AS rate,
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
            
            SELECT * FROM exchange_pairs LIMIT 1;
            """;

        ResultSet rs = DB.connect(query,
                baseCode, targetCode,
                targetCode, baseCode,
                baseCode, targetCode,
                targetCode, baseCode
        );

        try {
            if (rs.next()) {
                return building(rs);

            }

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }

        throw new ObjectNotFound("Currency pair not found :(");
    }

    @Override
    public void addModel(String name, String code, String sign)
            throws DataBaseNotAvailable, ObjectAlreadyExist, IncorrectParams {

    }
}
