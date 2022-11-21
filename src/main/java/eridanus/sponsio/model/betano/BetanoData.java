package eridanus.sponsio.model.betano;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BetanoData {

    @JsonProperty("regionGroups")
    private List<BetanoRegionGroups> regionGroups;
}
