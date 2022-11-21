package eridanus.sponsio.model.betano.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetanoMatchResponse {

    @JsonProperty("data")
    private BetanoMatchData data;
}
