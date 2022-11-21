package eridanus.sponsio.model.mozzart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MozzartTennisMatchOdds {

    private Map<String, MozzartOdds> mozzartOddsMap;
}
