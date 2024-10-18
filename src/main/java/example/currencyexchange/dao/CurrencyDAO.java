package example.currencyexchange.dao;

import example.currencyexchange.config.DataBaseConnect;
import example.currencyexchange.config.DataBaseRequestContainer;
import example.currencyexchange.model.Currency;
import example.currencyexchange.exceptions.status_400.IncorrectParams;
import example.currencyexchange.exceptions.status_404.ObjectNotFound;
import example.currencyexchange.exceptions.status_409.ObjectAlreadyExist;
import example.currencyexchange.exceptions.status_500.DataBaseNotAvailable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CurrencyDAO implements DAOInterface<Currency, String> {

    @Getter
    private static final CurrencyDAO instance = new CurrencyDAO();
    private static final DataBaseConnect db = DataBaseConnect.getCONNCECTION();
    private static final DataBaseRequestContainer query = DataBaseRequestContainer.getInstance();

    private CurrencyDAO() {
    }

    @Override
    public Currency find(String baseCode, String targetCode) throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }

    @Override
    public Currency building(ResultSet rs) throws DataBaseNotAvailable {
        try {
            Integer id = rs.getInt(1);
            String codeCurrency = rs.getString(2);
            String name = rs.getString(3);
            String sign = rs.getString(4);
            return new Currency(name, codeCurrency, id, sign);

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
    }


    @Override
    public Currency find(String code)
            throws ObjectNotFound, DataBaseNotAvailable {
        ResultSet rs = db.connect(query.getCurrencyByCode, code.toUpperCase());
        try {
            if (rs.next()) {
                return building(rs);
            }
            throw new ObjectNotFound("Currency not found");

        } catch (SQLException | DataBaseNotAvailable e) {
            throw new DataBaseNotAvailable();
        }
    }

    @Override
    public List<Currency> findAll()
            throws DataBaseNotAvailable, ObjectNotFound {
        ResultSet rs = db.connect(query.getAllCurrency);
        try {
            List<Currency> currencies = new ArrayList<>();
            while (rs.next()) {
                currencies.add(building(rs));
            }
            if (currencies.isEmpty()) {
                throw new ObjectNotFound("Currencies not found");
            }
            return currencies;

        } catch (SQLException | DataBaseNotAvailable e) {
            throw new DataBaseNotAvailable();
        }
    }

    @Override
    public void save(String name, String code, String sign)
            throws DataBaseNotAvailable, ObjectAlreadyExist, IncorrectParams {
        db.connect(query.addCurrency, name, code, sign);
    }
}
