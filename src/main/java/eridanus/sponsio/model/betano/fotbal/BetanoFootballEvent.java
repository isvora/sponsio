package eridanus.sponsio.model.betano.fotbal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetanoFootballEvent {

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("markets")
    private List<BetanoFootballMarket> markets;
}
