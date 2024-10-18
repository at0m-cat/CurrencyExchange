package example.currencyexchange.dao;

import example.currencyexchange.config.DataBaseConnect;
import example.currencyexchange.config.DataBaseRequestContainer;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.CurrencyExchange;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CurrencyExchangeDAO implements DAOInterface<CurrencyExchange, String> {

    @Getter
    private static final CurrencyExchangeDAO DAO = new CurrencyExchangeDAO();
    private static final DataBaseConnect DB = DataBaseConnect.getCONNCECTION();
    private static final DataBaseRequestContainer QUERY = DataBaseRequestContainer.getInstance();

    private CurrencyExchangeDAO() {
    }

    @Override
    public CurrencyExchange building(ResultSet rs)
            throws DataBaseNotAvailable {
        try {

            CurrencyExchange currencyExchange = new CurrencyExchange();
            BigDecimal rate = rs.getBigDecimal("rate");

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
    public List<CurrencyExchange> findAll() {
        return List.of();
    }

    @Override
    public CurrencyExchange find(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }

    @Override
    public CurrencyExchange find(String baseCode, String targetCode)
            throws ObjectNotFound, DataBaseNotAvailable {

        ResultSet rs = DB.connect(QUERY.exchangeRequest,
                baseCode, targetCode,
                targetCode, baseCode,
                baseCode, targetCode,
                targetCode, baseCode,
                baseCode, targetCode,
                baseCode, targetCode
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
    public void save(String name, String code, String sign)
            throws DataBaseNotAvailable, ObjectAlreadyExist, IncorrectParams {
    }
}
