package eridanus.sponsio.model.betano.fotbal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetanoFootballMarket {

    @JsonProperty("name")
    private String name;

    private String teamOne;
    private double teamOneOdds;

    private double drawOdds;

    private String teamTwo;
    private double teamTwoOdds;

    @JsonProperty("selections")
    private void setSelections(List<Map<String, Object>> selections) {
        if (selections.size() > 0) {
            teamOne = (String) selections.get(0).get("name");
            teamOneOdds = (double) selections.get(0).get("price");
        }
        if (selections.size() > 1) {
            drawOdds = (double) selections.get(1).get("price");
        }
        if (selections.size() > 2) {
            teamTwo = (String) selections.get(2).get("name");
            teamTwoOdds = (double) selections.get(2).get("price");
        }
    }

}
