package example.currencyexchange.dao;//package example.currencyexchange.model.dao;
import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.dto.CurrencyDTO;
import example.currencyexchange.model.Currency;
import example.currencyexchange.model.exceptions.code_404.ObjectNotFound;
import example.currencyexchange.model.exceptions.code_500.DataBaseNotAvailable;
import example.currencyexchange.model.exceptions.code_400.CurrencyPairNotExist;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CurrencyDAO implements DAOInterface<Currency, String> {

    @Getter
    private final static CurrencyDAO INSTANCE = new CurrencyDAO();
    private static final DataBaseConfig DB = DataBaseConfig.getCONNCECTION();

    private CurrencyDAO() {
    }


    @Override
    public Currency getModel(String baseCode, String targetCode) throws ObjectNotFound, DataBaseNotAvailable {
        return null;
    }

    private Currency building(ResultSet rs) throws DataBaseNotAvailable {
        try {
            Integer id = rs.getInt(1);
            String codeCurrency = rs.getString(2);
            String name = rs.getString(3);
            Integer sign = rs.getInt(4);
            return new Currency(name, codeCurrency, id, sign);

        } catch (SQLException e) {
            throw new DataBaseNotAvailable();
        }
    }


    @Override
    public Currency getModel(String code) throws ObjectNotFound, DataBaseNotAvailable {
        String query = "SELECT * FROM currencies WHERE code = ?";
        ResultSet rs = DB.connect(query, code.toUpperCase());
        try {
            if (rs.next()) {
                return building(rs);
            }

        } catch (SQLException | DataBaseNotAvailable e) {
            throw new DataBaseNotAvailable();
        }
        throw new ObjectNotFound("Currency not found");
    }

    @Override
    public List<Currency> getModelAll() {

        String query = "SELECT * FROM currencies";
        ResultSet rs = DB.connect(query);

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
    public void addModel(String name, String code, String sign) throws DataBaseNotAvailable {
        String query = """
                INSERT INTO currencies (fullname, code, sign)
                VALUES (?, ?, ?)
                """;

        DB.connect(query, name, code, sign);
    }
}
