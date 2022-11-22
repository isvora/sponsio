package eridanus.sponsio.model.mozzart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Jacksonized
@Builder
public class MozzartMatch {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("participants")
    private List<MozzartParticipant> participants;
}
