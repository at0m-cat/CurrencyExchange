package example.currencyexchange.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "name", "code", "sign"})
@JsonIgnoreProperties(value = "full_NAME")
public class Currency {

    @JsonProperty("name")
    private final String fullName;
    private final String code;
    private final Integer id;
    private final String sign;

}
