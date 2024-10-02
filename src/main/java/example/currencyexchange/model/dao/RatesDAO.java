package example.currencyexchange.model.dao;

import example.currencyexchange.model.Rates;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RatesDAO {


    @SneakyThrows
    public static List<Rates> parsing(ResultSet rs) {
        List<Rates> list = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String baseCurrencyId = rs.getString("basecurrencyid");
            String targetCurrencyId = rs.getString("targetcurrencyid");
            String rate = rs.getString("rate");

            Rates rates = new Rates(Integer.valueOf(id), baseCurrencyId, targetCurrencyId, Double.valueOf(rate));
            list.add(rates);
        }
        return list;
    }

}
