package eridanus.sponsio.model.betano.fotbal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetanoFootballMatchData {

    @JsonProperty("blocks")
    private List<BetanoFootballBlock> blocks;
}
