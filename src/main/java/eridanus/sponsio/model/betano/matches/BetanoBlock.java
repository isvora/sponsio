package eridanus.sponsio.model.betano.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetanoBlock {

    @JsonProperty("events")
    private List<BetanoEvent> events;
}
