package eridanus.sponsio.mapper;

import eridanus.sponsio.database.Odds;
import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.helper.BettingUtils;
import eridanus.sponsio.helper.Bookie;
import eridanus.sponsio.model.betano.matches.BetanoEvent;
import eridanus.sponsio.model.mozzart.MozzartOdds;
import eridanus.sponsio.model.mozzart.MozzartParticipant;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public final class OddsMapper {

    private OddsMapper() {

    }

    public static long calculateTennisMatchId(MozzartOdds mozzartOddsOne, MozzartOdds mozzartOddsTwo) {
        String playerOne = determinePlayerName(mozzartOddsOne);
        String playerTwo = determinePlayerName(mozzartOddsTwo);
        String id = playerOne + ". vs " + playerTwo + BettingUtils.DOT;
        return Math.abs(id.hashCode());
    }

    private static String determinePlayerName(MozzartOdds mozzartOdds) {
        var description = mozzartOdds.getGameDescription();
        var dots = StringUtils.countOccurrencesOf(description, BettingUtils.DOT);
        if (dots == 2) {
            var arr = Arrays.copyOfRange(description.split("\\."), 0, 2);
            return arr[0] + BettingUtils.DOT + arr[1];
        }
        if (dots == 3) {
            var arr = Arrays.copyOfRange(description.split("\\."), 0, 3);
            return arr[0] + BettingUtils.DOT + arr[1] + BettingUtils.DOT + arr[2];
        }
        return description.split("\\.")[0];
    }

    public static Odds mapMozzartOddsToOdds(MozzartOdds mozzartOddsOne, MozzartOdds mozzartOddsTwo, TennisMatch tennisMatch) {
        return Odds.builder()
                .oddOne(Double.parseDouble(mozzartOddsOne.getValue()))
                .oddTwo(Double.parseDouble(mozzartOddsTwo.getValue()))
                .tennisMatch(tennisMatch)
                .bookie(Bookie.MOZZART)
                .build();
    }

    public static Odds mapBetanoEventToOdds(BetanoEvent betanoEvent, TennisMatch tennisMatch) {
        if (!betanoEvent.getMarkets().isEmpty()) {
            var market = betanoEvent.getMarkets().get(0);
            if (market.getName().equals(BettingUtils.WINNER)) {
                return Odds.builder()
                        .tennisMatch(tennisMatch)
                        .oddOne(market.getPlayerOneOdds())
                        .oddTwo(market.getPlayerTwoOdds())
                        .bookie(Bookie.BETANO)
                        .build();
            }
        }
        return null;
    }
}
