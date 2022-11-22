package eridanus.sponsio.model.betano.fotbal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetanoFootballMatchResponse {

    @JsonProperty("data")
    private BetanoFootballMatchData data;
}
