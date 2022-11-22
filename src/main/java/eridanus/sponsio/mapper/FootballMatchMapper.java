package eridanus.sponsio.mapper;

import eridanus.sponsio.database.FootballMatch;
import eridanus.sponsio.model.betano.fotbal.BetanoFootballEvent;

import java.util.Optional;

public final class FootballMatchMapper {

    private FootballMatchMapper() {

    }

    public static Optional<FootballMatch> mapBetano(BetanoFootballEvent event) {
        var name = event.getShortName();
        var teams = name.split("-");
        if (teams.length != 2) {
            return Optional.empty();
        }
        return Optional.of(FootballMatch.builder()
                .id((long) Math.abs(name.hashCode()))
                .teamOne(teams[0].trim())
                .teamTwo(teams[1].trim())
                .build());
    }
}
