package example.currencyexchange.dao;

import example.currencyexchange.config.DataBaseConnect;
import example.currencyexchange.config.DataBaseRequestContainer;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ExchangeDAO implements DAOInterface<Exchange, String> {

    @Getter
    private static final ExchangeDAO instance = new ExchangeDAO();
    private static final DataBaseConnect db = DataBaseConnect.getCONNCECTION();
    private static final DataBaseRequestContainer query = DataBaseRequestContainer.getInstance();

    private ExchangeDAO() {
    }

    @Override
    public Exchange find(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }

    @Override
    public Exchange building(ResultSet rs)
            throws DataBaseNotAvailable {
        try {
            int id = rs.getInt("id");
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

            return new Exchange(id, baseCurrency, targetCurrency, rate);

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
    }

    @Override
    public Exchange find(String baseCode, String targetCode)
            throws ObjectNotFound, DataBaseNotAvailable {

        ResultSet rs = db.connect(query.getExchangeByCodePair, baseCode, targetCode);
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
    public List<Exchange> findAll() throws DataBaseNotAvailable, ObjectNotFound {

        ResultSet rs = db.connect(query.getExchangeAll);
        try {
            List<Exchange> result = new ArrayList<>();
            while (rs.next()) {
                result.add(building(rs));
            }

            if (!result.isEmpty()) {
                return result;
            }

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }

        throw new ObjectNotFound("Currency pair not found");
    }

    @Override
    public void save(String name, String code, String sign)
            throws DataBaseNotAvailable, ObjectAlreadyExist, IncorrectParams {
    }

    public void addToBase(ExchangeDTO pairsDto, BigDecimal rate)
            throws DataBaseNotAvailable, ObjectAlreadyExist, IncorrectParams {

        int baseId = pairsDto.getBaseCurrency().getId();
        int targetId = pairsDto.getTargetCurrency().getId();
        db.connect(query.addExchange, baseId, targetId, rate);
    }

    public void updateRate(String baseCode, String targetCode, Double rate)
            throws DataBaseNotAvailable, IncorrectParams, ObjectNotFound {

        Exchange model = find(baseCode, targetCode);
        Integer baseId = model.getBaseCurrency().getId();
        Integer targetId = model.getTargetCurrency().getId();
        db.connect(query.updateRate, rate, baseId, targetId);
    }
}