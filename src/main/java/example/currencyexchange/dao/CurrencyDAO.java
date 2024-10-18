package example.currencyexchange.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.config.DataBaseConnect;
import example.currencyexchange.config.DataBaseRequestContainer;
import example.currencyexchange.model.Currency;
import example.currencyexchange.exceptions.IncorrectParamsException;
import example.currencyexchange.exceptions.ObjectNotFoundException;
import example.currencyexchange.exceptions.ObjectAlreadyExistException;
import example.currencyexchange.exceptions.DataBaseNotAvailableException;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CurrencyDAO implements DAOInterface<Currency, String> {

    @Getter
    private static final CurrencyDAO instance = new CurrencyDAO();
    private final DataBaseConnect db;
    private final DataBaseRequestContainer query;

    private CurrencyDAO() {
        this.db = DataBaseConnect.getInstance();
        this.query = DataBaseRequestContainer.getInstance();
    }

    @Override
    public Currency find(String baseCode, String targetCode) {
        return null;
    }

    public Currency building(ResultSet rs) {
        try {
            Integer id = rs.getInt(1);
            String codeCurrency = rs.getString(2);
            String name = rs.getString(3);
            String sign = rs.getString(4);
            return new Currency(name, codeCurrency, id, sign);

        } catch (SQLException e) {
            throw new DataBaseNotAvailableException();
        }
    }


    @Override
    public Currency find(String code) {
        ResultSet rs = db.connect(query.getCurrencyByCode, code.toUpperCase());
        try {
            if (rs.next()) {
                return building(rs);
            }
            throw new ObjectNotFoundException("Currency not found");

        } catch (SQLException | DataBaseNotAvailableException e) {
            throw new DataBaseNotAvailableException();
        }
    }

    @Override
    public List<Currency> findAll() {
        ResultSet rs = db.connect(query.getAllCurrency);
        try {
            List<Currency> currencies = new ArrayList<>();
            while (rs.next()) {
                currencies.add(building(rs));
            }
            if (currencies.isEmpty()) {
                throw new ObjectNotFoundException("Currencies not found");
            }
            return currencies;

        } catch (SQLException | DataBaseNotAvailableException e) {
            throw new DataBaseNotAvailableException();
        }
    }

    @Override
    public void save(String name, String code, String sign) {
        db.connect(query.addCurrency, name, code, sign);
    }
}
