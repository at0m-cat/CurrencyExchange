package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Currencies {

    // HashMap<ID, Currency> ???

    private final String ID;
    private final String FULL_NAME;
    private final String CODE;
    private final String SIGN;

}
