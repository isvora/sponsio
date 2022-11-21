package eridanus.sponsio.model.mozzart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MozzartOdds {

    private String gameDescription;

    @JsonProperty("subGame")
    private void gameDescription(Map<String, String> brand) {
        gameDescription = brand.get("subGameDescription");
    }

    @JsonProperty("value")
    private String value;
}
