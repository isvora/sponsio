package eridanus.sponsio.mapper;

import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.model.mozzart.MozzartMatch;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TennisMatchMapper {

    private TennisMatchMapper() {

    }

    public static TennisMatch map(MozzartMatch mozzartMatch) {
        var participants = mozzartMatch.getParticipants();
        var playerOne = participants.get(0).getName();
        var playerTwo = participants.get(1).getName();
        String id = playerOne + " vs " + playerTwo;
        return TennisMatch.builder()
                .id((long) Math.abs(id.hashCode()))
                .playerOne(playerOne)
                .playerTwo(playerTwo)
                .build();
    }
}
