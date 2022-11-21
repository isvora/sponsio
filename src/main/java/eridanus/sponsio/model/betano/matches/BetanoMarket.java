package eridanus.sponsio.model.betano.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetanoMarket {

    @JsonProperty("name")
    private String name;

    private String playerOne;
    private double playerOneOdds;

    private String playerTwo;
    private double playerTwoOdds;

    @JsonProperty("selections")
    private void gameDescription(List<Map<String, Object>> selections) {
        playerOne = (String) selections.get(0).get("name");
        playerOneOdds = (double) selections.get(0).get("price");
        playerTwo = (String) selections.get(1).get("name");
        playerTwoOdds = (double) selections.get(1).get("price");
    }

}
