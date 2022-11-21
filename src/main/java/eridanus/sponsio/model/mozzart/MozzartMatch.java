package eridanus.sponsio.model.mozzart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MozzartMatch {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("participants")
    private List<MozzartParticipant> participants;
}
