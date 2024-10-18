package example.currencyexchange.dao;

import example.currencyexchange.config.DataBaseConnect;
import example.currencyexchange.config.DataBaseRequestContainer;
import example.currencyexchange.dto.ExchangeDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.Exchange;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.ObjectAlreadyExistException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ExchangeDAO implements DAOInterface<Exchange, String> {

    @Getter
    private static final ExchangeDAO instance = new ExchangeDAO();
    private final DataBaseConnect db;
    private final DataBaseRequestContainer query;

    private ExchangeDAO() {
        this.db = DataBaseConnect.getInstance();
        this.query = DataBaseRequestContainer.getInstance();
    }

    @Override
    public Exchange find(String code) {
        return null;
    }

    public Exchange building(ResultSet rs) {
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
            throw new DataBaseNotAvailableException();
        }
    }

    @Override
    public Exchange find(String baseCode, String targetCode) {
        ResultSet rs = db.connect(query.getExchangeByCodePair, baseCode, targetCode);
        try {
            if (rs.next()) {
                return building(rs);
            }

        } catch (SQLException e) {
            throw new DataBaseNotAvailableException();
        }
        throw new ObjectNotFoundException("Currency pair not found");
    }

    @Override
    public List<Exchange> findAll() {
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
            throw new DataBaseNotAvailableException();
        }

        throw new ObjectNotFoundException("Currency pair not found");
    }

    @Override
    public void save(String name, String code, String sign) {
    }

    public void addToBase(ExchangeDTO pairsDto, BigDecimal rate) {
        int baseId = pairsDto.getBaseCurrency().getId();
        int targetId = pairsDto.getTargetCurrency().getId();
        db.connect(query.addExchange, baseId, targetId, rate);
    }

    public void updateRate(String baseCode, String targetCode, Double rate) {
        Exchange model = find(baseCode, targetCode);
        Integer baseId = model.getBaseCurrency().getId();
        Integer targetId = model.getTargetCurrency().getId();
        db.connect(query.updateRate, rate, baseId, targetId);
    }
}