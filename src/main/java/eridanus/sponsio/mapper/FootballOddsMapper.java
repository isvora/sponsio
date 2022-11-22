package eridanus.sponsio.mapper;

import eridanus.sponsio.database.FootballMatch;
import eridanus.sponsio.database.FootballOdds;
import eridanus.sponsio.helper.Bookie;
import eridanus.sponsio.model.betano.fotbal.BetanoFootballEvent;

import java.util.Optional;

public final class FootballOddsMapper {

    private FootballOddsMapper() {
    }

    public static Optional<FootballOdds> mapBetano(BetanoFootballEvent event, FootballMatch footballMatch) {
        var market = event.getMarkets().get(0);
        if (market == null) {
            return Optional.empty();
        }
        return Optional.of(FootballOdds.builder()
                .bookie(Bookie.BETANO)
                .footballMatch(footballMatch)
                .oddTeamOneWin(market.getTeamOneOdds() == 0 ? 0 : market.getTeamOneOdds())
                .oddTeamTwoWin(market.getTeamTwoOdds() == 0 ? 0 : market.getTeamTwoOdds())
                .oddDraw(market.getDrawOdds() == 0 ? 0 : market.getDrawOdds())
                .build());
    }
}
