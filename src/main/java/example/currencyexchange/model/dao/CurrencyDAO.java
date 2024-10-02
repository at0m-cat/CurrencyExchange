package example.currencyexchange.model.dao;
import example.currencyexchange.config.DataBaseConfig;
import example.currencyexchange.model.Currencies;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {

    /**
     * Parsing currency in table "currencies"
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
     * @param code String code currency
     * @return Currencies object
     */
    public static Currencies findCodeCurrency(String code){
        String query = String.format("SELECT * FROM currencies WHERE code = '%s'", code.toUpperCase());

        ResultSet rs = DataBaseConfig.connect(query);
        return parsing(rs).getFirst();
    }

}
