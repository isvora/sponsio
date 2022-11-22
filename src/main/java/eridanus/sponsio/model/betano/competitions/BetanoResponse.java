package eridanus.sponsio.model.betano.competitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
public class BetanoResponse {

    @JsonProperty("data")
    private BetanoData data;
}
