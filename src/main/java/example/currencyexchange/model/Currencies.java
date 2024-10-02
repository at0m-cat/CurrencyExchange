package example.currencyexchange.model;
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

}
