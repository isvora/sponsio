package eridanus.sponsio.model.mozzart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MozzartResponse {

    @JsonProperty("matches")
    private List<MozzartMatch> matches;
}
