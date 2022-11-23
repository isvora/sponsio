package eridanus.sponsio.mapper;

import eridanus.sponsio.database.FootballMatch;
import eridanus.sponsio.database.FootballOdds;
import eridanus.sponsio.helper.Bookie;
import eridanus.sponsio.model.betano.fotbal.BetanoFootballEvent;
import eridanus.sponsio.model.mozzart.MozzartOdds;

import java.util.Optional;

public final class FootballOddsMapper {

    private FootballOddsMapper() {
    }

    public static FootballOdds mapMozzart(MozzartOdds teamOne, MozzartOdds draw, MozzartOdds teamTwo, FootballMatch footballMatch) {
        return FootballOdds.builder()
                .bookie(Bookie.BETANO)
                .footballMatch(footballMatch)
                .oddTeamOneWin(teamOne.getValue() == null ? 0 : Double.parseDouble(teamOne.getValue()))
                .oddTeamTwoWin(teamTwo.getValue() == null ? 0 : Double.parseDouble(teamTwo.getValue()))
                .oddDraw(draw.getValue() == null ? 0 : Double.parseDouble(draw.getValue()))
                .build();
    }

    public static long calculateFootballMatchId(MozzartOdds mozzartOddsOne, MozzartOdds mozzartOddsTwo) {
        String playerOne = determinePlayerName(mozzartOddsOne);
        String playerTwo = determinePlayerName(mozzartOddsTwo);
        String id = playerOne + " - " + playerTwo;
        return Math.abs(id.hashCode());
    }

    public static String determinePlayerName(MozzartOdds mozzartOdds) {
        var split = mozzartOdds.getGameDescription().split("castiga");
        return split[0].trim();
    }

    public static Optional<FootballOdds> mapBetano(BetanoFootballEvent event, FootballMatch footballMatch) {
        var markets = event.getMarkets();
        if (markets == null || markets.isEmpty()) {
            return Optional.empty();
        }
        var market = markets.get(0);
        return Optional.of(FootballOdds.builder()
                .bookie(Bookie.BETANO)
                .footballMatch(footballMatch)
                .oddTeamOneWin(market .getTeamOneOdds() == 0 ? 0 : market .getTeamOneOdds())
                .oddTeamTwoWin(market .getTeamTwoOdds() == 0 ? 0 :market .getTeamTwoOdds())
                .oddDraw(market .getDrawOdds() == 0 ? 0 : market .getDrawOdds())
                .build());
    }
}
