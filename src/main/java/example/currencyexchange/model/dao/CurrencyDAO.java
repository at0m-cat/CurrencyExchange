package example.currencyexchange.model.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.model.Currencies;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CurrencyDAO {

    /**
     * db name: "currencyexchange",
     * table name: "currencies"
     *
     * @return List Currencies
     */
    public static List<Currencies> getCurrencies() {
        String query = "select * from currencies";
        ResultSet resultSet = DataBaseConfig.connect(query);
        return CurrencyDAO.parsing(resultSet);
    }

    /**
     * Parsing currency in table "currencies"
     *
     * @param rs ResultSet
     * @return List currencies
     */
    @SneakyThrows
    public static List<Currencies> parsing(ResultSet rs) {
        List<Currencies> list = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString(1);
            String code = rs.getString(2);
            String fullname = rs.getString(3);
            String sign = rs.getString(4);

            Currencies currencies = new Currencies(fullname, code, Integer.valueOf(id), Integer.valueOf(sign));
            list.add(currencies);
        }
        return list;
    }

    /**
     * Find code in table "Currencies"
     *
     * @param code String code currency
     * @return Currencies object
     */
    public static Currencies findCodeCurrency(String code) {
        String query = String.format("SELECT * FROM currencies WHERE code = '%s'", code.toUpperCase());

        ResultSet rs = DataBaseConfig.connect(query);
        return parsing(rs).getFirst();
    }

    /**
     * Checks if an object exists, has an internal find call
     *
     * @param code String
     * @return
     */
    public static boolean isExist(String code) {
        try {
            findCodeCurrency(code);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
