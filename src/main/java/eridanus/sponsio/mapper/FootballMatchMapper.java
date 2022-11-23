package eridanus.sponsio.mapper;

import eridanus.sponsio.database.FootballMatch;
import eridanus.sponsio.model.betano.fotbal.BetanoFootballEvent;
import eridanus.sponsio.model.mozzart.MozzartMatch;

import java.text.Normalizer;
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
        String teamOne = Normalizer
                .normalize(teams[0], Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        String teamTwo = Normalizer
                .normalize(teams[1], Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        return Optional.of(FootballMatch.builder()
                .id((long) Math.abs(name.hashCode()))
                .teamOne(teamOne.trim())
                .teamTwo(teamTwo.trim())
                .build());
    }

    public static Optional<FootballMatch> mapMozzart(MozzartMatch mozzartMatch) {
        var participants = mozzartMatch.getParticipants();
        if (participants.size() != 2) {
            return Optional.empty();
        }
        var teamOne = participants.get(0).getName();
        var teamTwo = participants.get(1).getName();
        teamOne = Normalizer
                        .normalize(teamOne, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "");
        teamTwo = Normalizer
                        .normalize(teamTwo, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "");
        var id = teamOne.trim() + " - " + teamTwo.trim();
        return Optional.of(FootballMatch.builder()
                .id((long) Math.abs(id.hashCode()))
                .teamOne(teamOne)
                .teamTwo(teamTwo)
                .build());
    }
}
