package example.currencyexchange.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "name", "code", "sign"})
public final class CurrencyDTO {

    public CurrencyDTO(){
    }

    private String name;
    private String code;
    private Integer id;
    private Integer sign;

}
