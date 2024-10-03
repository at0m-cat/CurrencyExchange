package example.currencyexchange.model.dao;

import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.model.Currencies;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

public class CurrencyDAO {

    /**
     * db name: "currencyexchange",
     * table name: "currencies"
     *
     * @return List Currencies
     */
    @SneakyThrows
    public static List<Currencies> getCurrencies() {
        String query = "SELECT * from currencies";
        ResultSet resultSet = DataBaseConfig.connect(query);
        return CurrencyDAO.parsing(resultSet);
    }

    @SneakyThrows
    public static void setCurrency(String currencyName, String currencyCode, String currencySign) throws NoSuchMethodException {
        if (findCodeCurrency(currencyCode) != null) {
            throw new NoSuchMethodException();
        }
        String query = """
                INSERT INTO currencies (fullname, code, sign)
                VALUES (?, ?, ?)
                ON CONFLICT (code) DO NOTHING""";

        DataBaseConfig.connect(query, currencyName, currencyCode.toUpperCase(), currencySign);
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
     * @return Currencies object / null
     */
    @SneakyThrows
    public static Currencies findCodeCurrency(String code) {
        String query = "SELECT * from currencies where code = ?";
        ResultSet rs = DataBaseConfig.connect(query, code.toUpperCase());
        try {
            return parsing(rs).getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
