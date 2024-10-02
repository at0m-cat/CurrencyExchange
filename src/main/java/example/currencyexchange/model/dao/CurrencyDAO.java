package example.currencyexchange.model.dao;

import example.currencyexchange.model.Currencies;
import example.currencyexchange.model.SingleCurrency;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {

    /**
     * Parsing currency from table "currencies"
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
     * Find code in List "Currencies"
     * @param currenciesList List currencies
     * @param code String code currency
     * @return SingleCurrency object
     */
    public static SingleCurrency findCodeCurrency(List<Currencies> currenciesList, String code){
        for (Currencies currencies : currenciesList) {
            if (currencies.getCODE().equalsIgnoreCase(code)) {
                return new SingleCurrency(
                        currencies.getFULL_NAME(),
                        currencies.getCODE(),
                        currencies.getID(),
                        currencies.getSIGN()
                );
            }
        }
        return null;
    }

}
