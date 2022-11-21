package eridanus.sponsio.model.mozzart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MozzartOddsResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("kodds")
    private LinkedHashMap<String, MozzartOdds> mozzartOdds;
}
