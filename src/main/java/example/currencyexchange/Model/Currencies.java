package example.currencyexchange.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Currencies{

    private final String FULL_NAME;
    private final String CODE;
    private final Integer ID;
    private final Integer SIGN;

    /**
     * Parsing currency from table "currencies"
     * @param rs ResultSet
     * @return List of currencies
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

}
