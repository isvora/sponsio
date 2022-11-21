package eridanus.sponsio.mapper;

import eridanus.sponsio.database.Odds;
import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.model.mozzart.MozzartOdds;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public final class OddsMapper {

    private OddsMapper() {

    }

    public static long calculateTennisMatchId(MozzartOdds mozzartOddsOne, MozzartOdds mozzartOddsTwo) {
        String playerOne = determinePlayerName(mozzartOddsOne);
        String playerTwo = determinePlayerName(mozzartOddsTwo);
        String id = playerOne + ". vs " + playerTwo + ".";
        return Math.abs(id.hashCode());
    }

    private static String determinePlayerName(MozzartOdds mozzartOdds) {
        var description = mozzartOdds.getGameDescription();
        var dots = StringUtils.countOccurrencesOf(description, ".");
        if (dots == 2) {
            var arr = Arrays.copyOfRange(description.split("\\."), 0, 2);
            return arr[0] + "." + arr[1];
        }
        if (dots == 3) {
            var arr = Arrays.copyOfRange(description.split("\\."), 0, 3);
            return arr[0] + "." + arr[1] + "." + arr[2];
        }
        return description.split("\\.")[0];
    }

    public static Odds map(MozzartOdds mozzartOddsOne, MozzartOdds mozzartOddsTwo, TennisMatch tennisMatch) {
        return Odds.builder()
                .oddOne(Double.parseDouble(mozzartOddsOne.getValue()))
                .oddTwo(Double.parseDouble(mozzartOddsTwo.getValue()))
                .tennisMatch(tennisMatch)
                .build();
    }
}
