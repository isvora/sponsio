package eridanus.sponsio.model.betano.competitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
public class BetanoLeague {

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;
}
