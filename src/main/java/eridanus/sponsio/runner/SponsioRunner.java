package eridanus.sponsio.runner;

import eridanus.sponsio.database.Odds;
import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.helper.BettingUtils;
import eridanus.sponsio.model.mozzart.MozzartResponse;
import eridanus.sponsio.service.bookies.BetanoService;
import eridanus.sponsio.service.bookies.MozzartService;
import eridanus.sponsio.service.database.TennisMatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SponsioRunner implements CommandLineRunner {

    private final MozzartService mozzartService;
    private final BetanoService betanoService;
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

        log.info("Finished");
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
            // Need more than one pair of odds for possible arbitrage opportunities.
            if (odds.size() > 1) {
                determineArbitrageOpportunities(odds, tennisMatch);
            }
        }
    }

    private void determineArbitrageOpportunities(Set<Odds> odds, TennisMatch tennisMatch) {
        var match = tennisMatch.getPlayerOne() + BettingUtils.VS + tennisMatch.getPlayerTwo();
        var oddsList = odds.stream().toList();

        for (int i = 0; i < oddsList.size()-1; i++) {
            for (int j = 1; j < oddsList.size(); j++) {
                var oddOne = oddsList.get(i);
                var oddTwo = oddsList.get(j);

                var odd1_1 = oddOne.getOddOne();
                var odd1_2 = oddOne.getOddTwo();
                var odd2_1 = oddTwo.getOddOne();
                var odd2_2 = oddTwo.getOddTwo();
                double v1 = 1 / odd1_1 + 1 / odd2_2;
                double v2 = 1 / odd2_1 + 1 / odd1_2;

                if (v1 < 1) {
                    log.info("Arbitrage found for match: " + match);
                    log.info("Odds from " + oddOne.getBookie() + ": " + odd1_1 +
                            ", Odds from " + oddTwo.getBookie() + ": " + odd2_2);
                    log.info("Bet " + 1000/odd1_1 + " on " + odd1_1 + BettingUtils.SPACE + oddOne.getBookie());
                    log.info("Bet " + 1000/odd2_2 + " on " + odd2_2 + BettingUtils.SPACE + oddTwo.getBookie());
                    log.info("Winning: " + (1000-v1*1000));
                }
                if (v2 < 1) {
                    log.info("Odds from " + oddOne.getBookie() + ": " + odd1_2 +
                            ", Odds from " + oddTwo.getBookie() + ": " + odd2_1);
                    log.info("Bet " + 1000/odd1_2 + " on " + odd1_2 + BettingUtils.SPACE + oddOne.getBookie());
                    log.info("Bet " + 1000/odd2_1 + " on " + odd2_1 + BettingUtils.SPACE + oddTwo.getBookie());
                    log.info("Winning: " + (1000-v2*1000));
                }
            }
        }
    }
}
