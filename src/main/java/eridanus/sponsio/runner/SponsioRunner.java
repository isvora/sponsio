package eridanus.sponsio.runner;

import eridanus.sponsio.service.bookies.BetanoService;
import eridanus.sponsio.service.bookies.MozzartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SponsioRunner implements CommandLineRunner {

    private final MozzartService mozzartService;
    private final BetanoService betanoService;

    @Override
    public void run(String... args) {
        log.info("Starting Sponsio...");
        log.info("Collecting mozzart tennis games");

        /*var mozzartMatches = mozzartService.getMozzartTennisMatches();
        for (MozzartResponse mozzartResponse : mozzartMatches) {
            var matchIds = mozzartResponse.getMatches().stream()
                    .flatMap(mozzartMatch -> mozzartMatch.getId().describeConstable().stream()).toList();
            mozzartService.getTennisOdds(matchIds);
        }*/

        log.info("Finished collecting mozzart tennis games");
        log.info("Collecting betano tennis games");

        var betanoResponse = betanoService.getBetanoTennisLeagues();
        for (var regionGroup : betanoResponse.getData().getRegionGroups()) {
            var region = regionGroup.getRegions();
            region.forEach(betanoRegion -> {
                betanoRegion.getLeagues().removeIf(betanoLeague ->
                        betanoLeague.getName().contains("Pariuri antepost")
                        || betanoLeague.getName().contains("Dublu")
                        || betanoLeague.getName().contains("H2H"));
                for (var league : betanoRegion.getLeagues()) {
                    betanoService.getBetanoTennisMatchByCompetition(league.getUrl());
                }
            });
        }
    }
}
