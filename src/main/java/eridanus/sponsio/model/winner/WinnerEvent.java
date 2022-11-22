package eridanus.sponsio.model.winner;

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
public class WinnerEvent {

    @JsonProperty("j")
    private String playerNames;

    @JsonProperty("o")
    private List<WinnerBets> winnerBets;
}
