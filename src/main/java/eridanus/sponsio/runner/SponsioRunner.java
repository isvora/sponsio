package eridanus.sponsio.runner;

import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.helper.BettingUtils;
import eridanus.sponsio.model.mozzart.MozzartResponse;
import eridanus.sponsio.service.bookies.BetanoService;
import eridanus.sponsio.service.bookies.MozzartService;
import eridanus.sponsio.service.database.OddsService;
import eridanus.sponsio.service.database.TennisMatchService;
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
    private final OddsService oddsService;
    private final TennisMatchService tennisMatchService;

    @Override
    public void run(String... args) {
        log.info("Starting Sponsio...");
        log.info("Collecting Mozzart tennis games");

        mozzart();

        log.info("Finished collecting Mozzart tennis games");
        log.info("Collecting Betano tennis games");

        betano();

        log.info("Finished collecting Betano tennis games");
        log.info("Checking for arbitrage possibilities");

        calculateOdds();
    }

    private void mozzart() {
        var mozzartMatches = mozzartService.getMozzartTennisMatches();
        for (MozzartResponse mozzartResponse : mozzartMatches) {
            var matchIds = mozzartResponse.getMatches().stream()
                    .flatMap(mozzartMatch -> mozzartMatch.getId().describeConstable().stream()).toList();
            mozzartService.getTennisOdds(matchIds);
        }
    }

    private void betano() {
        var betanoResponse = betanoService.getBetanoTennisLeagues();
        for (var regionGroup : betanoResponse.getData().getRegionGroups()) {
            var region = regionGroup.getRegions();
            region.forEach(betanoRegion -> {
                betanoRegion.getLeagues().removeIf(betanoLeague ->
                        betanoLeague.getName().contains(BettingUtils.BEFORE_BETS)
                                || betanoLeague.getName().contains(BettingUtils.DOUBLE)
                                || betanoLeague.getName().contains(BettingUtils.H2H));
                for (var league : betanoRegion.getLeagues()) {
                    betanoService.getBetanoTennisMatchByCompetition(league.getUrl());
                }
            });
        }
    }

    private void calculateOdds() {
        var tennisMatches = tennisMatchService.getAllMatches();
        for (var tennisMatch : tennisMatches) {
            var odds = tennisMatch.getOdds();
            if (odds.size() > 2) {
                log.info(" YEY " + tennisMatch.getPlayerOne() + BettingUtils.VS + tennisMatch.getPlayerTwo());
            } else {
                log.info(tennisMatch.getPlayerOne() + BettingUtils.VS + tennisMatch.getPlayerTwo());
            }
        }
    }
}
