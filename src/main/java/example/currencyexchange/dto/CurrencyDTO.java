package example.currencyexchange.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"id", "name", "code", "sign"})
public final class CurrencyDTO {

    private String name;
    private String code;
    private Integer id;
    private String sign;

}
