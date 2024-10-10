package example.currencyexchange.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "name", "code", "sign"})
@JsonIgnoreProperties(value = "full_NAME")
public class Currency {

    @JsonProperty("name")
    private final String FULL_NAME;
    private final String CODE;
    private final Integer ID;
    private final Integer SIGN;

}
