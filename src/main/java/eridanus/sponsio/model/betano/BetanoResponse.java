package eridanus.sponsio.model.betano;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BetanoResponse {

    @JsonProperty("data")
    private BetanoData data;
}
