package example.currencyexchange.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Getter
public class Rates {
    private final Integer ID;
    private final String BASE_CURRENCY_ID;
    private final String TARGET_CURRENCY_ID;
    private final Double RATE;

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
