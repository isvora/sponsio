package eridanus.sponsio.model.betting;

import eridanus.sponsio.database.FootballOdds;
import eridanus.sponsio.helper.ArbitrageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Arbitrage {

    private Double outlay;

    private FootballOdds oddOne;

    private FootballOdds oddTwo;

    private ArbitrageType arbitrageType;
}
