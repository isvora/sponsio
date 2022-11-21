package eridanus.sponsio.mapper;

import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.helper.BettingUtils;
import eridanus.sponsio.model.betano.matches.BetanoEvent;
import eridanus.sponsio.model.mozzart.MozzartMatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
public final class TennisMatchMapper {

    private TennisMatchMapper() {

    }

    public static TennisMatch map(MozzartMatch mozzartMatch) {
        var participants = mozzartMatch.getParticipants();
        var playerOne = participants.get(0).getName();
        var playerTwo = participants.get(1).getName();
        String id = playerOne + BettingUtils.VS + playerTwo;
        return TennisMatch.builder()
                .id((long) Math.abs(id.hashCode()))
                .playerOne(playerOne)
                .playerTwo(playerTwo)
                .build();
    }

    public static TennisMatch map(BetanoEvent betanoEvent) {
        var playerNames = getPlayerNames(betanoEvent.getShortName());
        if (playerNames.isEmpty()) {
            log.info("Empty player names");
            return null;
        } else {
            String id = playerNames.get(0) + BettingUtils.VS + playerNames.get(1);
            return TennisMatch.builder()
                    .playerOne(playerNames.get(0))
                    .playerTwo(playerNames.get(1))
                    .id((long) Math.abs(id.hashCode()))
                    .build();
        }
    }

    private static List<String> getPlayerNames(String shortName) {
        if (StringUtils.countOccurrencesOf(shortName, BettingUtils.MINUS) == 1
                && !shortName.contains(BettingUtils.DOT)
                && !shortName.startsWith(BettingUtils.SPACE)) {
            var players = shortName.split(BettingUtils.MINUS);

            var split = players[0].split(BettingUtils.SPACE);
            String playerOne = determinePlayerOneName(split);

            split = players[1].split(BettingUtils.SPACE);
            String playerTwo = determinePlayerTwoName(split);

            return List.of(playerOne, playerTwo);
        }

        return Collections.emptyList();
    }

    private static String determinePlayerOneName(String[] arr) {
        if (arr.length == 2) {
            return arr[1] + BettingUtils.SPACE + arr[0].charAt(0) + BettingUtils.DOT;
        } else {
            return arr[2] + BettingUtils.SPACE + arr[0].charAt(0) + BettingUtils.DOT + arr[1].charAt(0) + BettingUtils.DOT;
        }
    }

    private static String determinePlayerTwoName(String[] arr) {
        if (arr.length == 3) {
            return arr[2] + BettingUtils.SPACE + arr[1].charAt(0) + BettingUtils.DOT;
        } else {
            return arr[3] + BettingUtils.SPACE + arr[1].charAt(0) + BettingUtils.DOT + arr[2].charAt(0) + BettingUtils.DOT;
        }
    }
}
