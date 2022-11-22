package eridanus.sponsio.model.betano.competitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Jacksonized
@Builder
public class BetanoRegion {

    @JsonProperty("leagues")
    private List<BetanoLeague> leagues;
}
