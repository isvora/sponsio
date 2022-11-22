package eridanus.sponsio.model.betano.competitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
public class BetanoData {

    @JsonProperty("regionGroups")
    private List<BetanoRegionGroups> regionGroups;
}
