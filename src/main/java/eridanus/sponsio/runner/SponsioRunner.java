package eridanus.sponsio.runner;

import eridanus.sponsio.database.FootballMatch;
import eridanus.sponsio.database.FootballOdds;
import eridanus.sponsio.database.Odds;
import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.helper.ArbitrageType;
import eridanus.sponsio.helper.BettingUtils;
import eridanus.sponsio.model.betting.Arbitrage;
import eridanus.sponsio.model.mozzart.MozzartResponse;
import eridanus.sponsio.service.bookies.BetanoFootballService;
import eridanus.sponsio.service.bookies.BetanoService;
import eridanus.sponsio.service.bookies.MozzartFootballService;
import eridanus.sponsio.service.bookies.MozzartService;
import eridanus.sponsio.service.database.FootballMatchService;
import eridanus.sponsio.service.database.TennisMatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SponsioRunner implements CommandLineRunner {

    private final MozzartService mozzartService;
    private final BetanoService betanoService;
    private final TennisMatchService tennisMatchService;
    private final BetanoFootballService betanoFootballService;
    private final FootballMatchService footballMatchService;
    private final MozzartFootballService mozzartFootballService;

    @Override
    public void run(String... args) {
        log.info("Starting Sponsio...");

        tennis();

        football();

        log.info("Finished");
    }

    private void football() {
        log.info("Collecting Mozzart football matches");

        mozzartFootball();

        log.info("Finished collecting Mozzart football games");
        log.info("Collecting Betano football games");

        betanoFootball();

        log.info("Finished collecting Betano football games");
        log.info("Checking for arbitrage possibilities");

        calculateFootballOdds();
    }

    private void tennis() {
        log.info("Collecting Mozzart tennis games");

        mozzart();

        log.info("Finished collecting Mozzart tennis games");
        log.info("Collecting Betano tennis games");

        betano();

        log.info("Finished collecting Betano tennis games");
        log.info("Checking for arbitrage possibilities");

        calculateOdds();
    }

    private void mozzartFootball() {
        var mozzartFootballMatches = mozzartFootballService.getFootballMatches();
        for (MozzartResponse mozzartResponse : mozzartFootballMatches) {
            var matchIds = mozzartResponse.getMatches().stream()
                    .flatMap(mozzartMatch -> mozzartMatch.getId().describeConstable().stream()).toList();
            mozzartFootballService.getFootballOdds(matchIds);
        }
    }

    private void betanoFootball() {
        var betanoResponse = betanoFootballService.getBetanoFootballLeagues();
        for (var regionGroup : betanoResponse.getData().getRegionGroups()) {
            var region = regionGroup.getRegions();
            region.forEach(betanoRegion -> {
                betanoRegion.getLeagues().removeIf(betanoLeague ->
                        betanoLeague.getName().contains(BettingUtils.BEFORE_BETS));
                for (var league : betanoRegion.getLeagues()) {
                    betanoFootballService.getBetanoFotbalMatchesByCompetition(league.getUrl());
                }
            });
        }
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

    private void calculateFootballOdds() {
        var footballMatches = footballMatchService.findAlL();
        for (var footballMatch : footballMatches) {
            var odds = footballMatch.getFootballOdds();
            if (odds.size()>1) {
                determineFootballArbitrageOpportunities(odds, footballMatch);
            }
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

    private void determineFootballArbitrageOpportunities(Set<FootballOdds> footballOdds, FootballMatch footballMatch) {
        var match = footballMatch.getTeamOne() + BettingUtils.VS + footballMatch.getTeamTwo();
        var oddsList = footballOdds.stream().toList();
        for (int i = 0; i < oddsList.size()-1; i++) {
            for (int j = 1; j < oddsList.size(); j++) {
                var oddOne = oddsList.get(i);
                var oddTwo = oddsList.get(j);

                var allPossibleOutlays = calculateAllPossibleOutlays(oddOne, oddTwo, match);
                var arbitrages = allPossibleOutlays.stream().filter(arbitrage -> arbitrage.getOutlay() < 1).toList();
                for (Arbitrage arbitrage : arbitrages) {
                    footballArbitrageMessage(arbitrage, match, footballMatch.getTeamOne(), footballMatch.getTeamTwo());
                }
            }
        }
    }

    private List<Arbitrage> calculateAllPossibleOutlays(FootballOdds oddOne, FootballOdds oddTwo, String match) {
        List<Arbitrage> allPossibleOutlays = new ArrayList<>();

        allPossibleOutlays.add(
                Arbitrage.builder()
                        .outlay(1 / oddOne.getOddTeamOneWin() + 1 / oddTwo.getOddDraw() + 1/oddTwo.getOddTeamTwoWin())
                        .oddOne(oddOne)
                        .oddTwo(oddTwo)
                        .arbitrageType(ArbitrageType.ARBITRAGE_1)
                        .build());
        allPossibleOutlays.add(
                Arbitrage.builder()
                        .outlay(1 / oddOne.getOddDraw() + 1 / oddTwo.getOddTeamOneWin() + 1/oddTwo.getOddTeamTwoWin())
                        .oddOne(oddOne)
                        .oddTwo(oddTwo)
                        .arbitrageType(ArbitrageType.ARBITRAGE_2)
                        .build());
        allPossibleOutlays.add(
                Arbitrage.builder()
                        .outlay(1 / oddOne.getOddTeamTwoWin() + 1 / oddTwo.getOddDraw() + 1/oddTwo.getOddTeamOneWin())
                        .oddOne(oddOne)
                        .oddTwo(oddTwo)
                        .arbitrageType(ArbitrageType.ARBITRAGE_3)
                        .build());
        allPossibleOutlays.add(
                Arbitrage.builder()
                        .outlay(1 / oddTwo.getOddTeamOneWin() + 1 / oddOne.getOddDraw() + 1/oddTwo.getOddTeamTwoWin())
                        .oddOne(oddOne)
                        .oddTwo(oddTwo)
                        .arbitrageType(ArbitrageType.ARBITRAGE_4)
                        .build());
        allPossibleOutlays.add(
                Arbitrage.builder()
                        .outlay(1 / oddTwo.getOddDraw() + 1 / oddOne.getOddTeamOneWin() + 1/oddTwo.getOddTeamTwoWin())
                        .oddOne(oddOne)
                        .oddTwo(oddTwo)
                        .arbitrageType(ArbitrageType.ARBITRAGE_5)
                        .build());
        allPossibleOutlays.add(
                Arbitrage.builder()
                        .outlay(1 / oddTwo.getOddTeamTwoWin() + 1 / oddOne.getOddTeamOneWin() + 1/oddTwo.getOddDraw())
                        .oddOne(oddOne)
                        .oddTwo(oddTwo)
                        .arbitrageType(ArbitrageType.ARBITRAGE_6)
                        .build());

        return allPossibleOutlays;
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

    private void footballArbitrageMessage(Arbitrage arbitrage, String match, String teamOne, String teamTwo) {
        var oddsOne = arbitrage.getOddOne();
        var oddsTwo = arbitrage.getOddTwo();
        log.info("Arbitrage opportunity found!");
        log.info(match);
        switch (arbitrage.getArbitrageType()) {
            case ARBITRAGE_1 -> {
                var outlay = 1000 / (1 + oddsOne.getOddTeamOneWin() / oddsTwo.getOddDraw() + oddsOne.getOddTeamOneWin() / oddsTwo.getOddTeamTwoWin());
                log.info("Bet: ");
                log.info("$" + outlay
                        + " on " + teamOne + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsTwo.getOddDraw() / oddsOne.getOddTeamOneWin() + oddsTwo.getOddDraw() / oddsTwo.getOddTeamTwoWin())
                        + " on " + teamTwo + " to draw on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsTwo.getOddTeamTwoWin() / oddsOne.getOddTeamOneWin() + oddsTwo.getOddTeamTwoWin() / oddsTwo.getOddDraw())
                        + " on " + teamTwo + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("Guaranteed profit: " + (outlay*oddsOne.getOddTeamOneWin() - 1000));
            }
            case ARBITRAGE_2 -> {
                log.info("Bet: ");
                var outlay = 1000 / (1 + oddsOne.getOddDraw() / oddsTwo.getOddTeamOneWin() + oddsOne.getOddDraw() / oddsTwo.getOddTeamTwoWin());
                log.info("$" + outlay
                        + " on " + teamOne + " to draw on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsTwo.getOddTeamOneWin() / oddsOne.getOddDraw() + oddsTwo.getOddTeamOneWin() / oddsTwo.getOddTeamTwoWin())
                        + " on " + teamOne + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsTwo.getOddTeamTwoWin() / oddsOne.getOddDraw() + oddsTwo.getOddTeamTwoWin() / oddsTwo.getOddTeamOneWin())
                        + " on " + teamTwo + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("Guaranteed profit: " + (outlay*oddsOne.getOddDraw() - 1000));
            }
            case ARBITRAGE_3 -> {
                log.info("Bet: ");
                var outlay = 1000 / (1 + oddsOne.getOddTeamTwoWin() / oddsTwo.getOddDraw() + oddsOne.getOddTeamTwoWin() / oddsTwo.getOddTeamOneWin());
                log.info("$" + outlay
                        + " on " + teamTwo + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsTwo.getOddDraw() / oddsOne.getOddTeamTwoWin() + oddsTwo.getOddDraw() / oddsTwo.getOddTeamOneWin())
                        + " on " + teamTwo + " to draw on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsTwo.getOddTeamOneWin() / oddsOne.getOddTeamTwoWin() + oddsTwo.getOddTeamOneWin() / oddsTwo.getOddDraw())
                        + " on " + teamOne + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("Guaranteed profit: " + (outlay*oddsOne.getOddTeamTwoWin() - 1000));
            }
            case ARBITRAGE_4 -> {
                log.info("Bet: ");
                var outlay = 1000 / (1 + oddsTwo.getOddTeamOneWin() / oddsOne.getOddDraw() + oddsTwo.getOddTeamOneWin() / oddsOne.getOddTeamTwoWin());
                log.info("$" + outlay
                        + " on " + teamOne + " to win on Boookie " + oddsTwo.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsOne.getOddDraw() / oddsTwo.getOddTeamOneWin() + oddsOne.getOddDraw() / oddsOne.getOddTeamTwoWin())
                        + " on " + teamOne + " to draw on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsOne.getOddTeamTwoWin() / oddsTwo.getOddTeamOneWin() + oddsOne.getOddTeamTwoWin() / oddsOne.getOddDraw())
                        + " on " + teamTwo + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("Guaranteed profit: " + (outlay*oddsTwo.getOddTeamOneWin() - 1000));
            }
            case ARBITRAGE_5 -> {
                log.info("Bet: ");
                var outlay = 1000 / (1 + oddsTwo.getOddDraw() / oddsOne.getOddTeamOneWin() + oddsTwo.getOddDraw() / oddsOne.getOddTeamTwoWin());
                log.info("$" + outlay
                        + " on " + teamTwo + " to draw on Boookie " + oddsTwo.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsOne.getOddTeamOneWin() / oddsTwo.getOddDraw() + oddsOne.getOddTeamOneWin() / oddsOne.getOddTeamTwoWin())
                        + " on " + teamOne + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsOne.getOddTeamTwoWin() / oddsTwo.getOddDraw() + oddsOne.getOddTeamTwoWin() / oddsOne.getOddTeamOneWin())
                        + " on " + teamTwo + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("Guaranteed profit: " + (outlay*oddsTwo.getOddDraw() - 1000));
            }
            case ARBITRAGE_6 -> {
                log.info("Bet: ");
                var outlay = 1000 / (1 + oddsTwo.getOddTeamTwoWin() / oddsOne.getOddTeamOneWin() + oddsTwo.getOddTeamTwoWin() / oddsOne.getOddDraw());
                log.info("$" + outlay
                        + " on " + teamTwo + " to win on Boookie " + oddsTwo.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsOne.getOddDraw() / oddsTwo.getOddTeamTwoWin() + oddsOne.getOddDraw() / oddsOne.getOddTeamOneWin())
                        + " on " + teamOne + " to draw on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("$" + 1000 / (1 + oddsOne.getOddTeamOneWin() / oddsTwo.getOddDraw() + oddsOne.getOddTeamOneWin() / oddsOne.getOddTeamTwoWin())
                        + " on " + teamOne + " to win on Boookie " + oddsOne.getBookie().getBookieName());
                log.info("Guaranteed profit: " + (outlay*oddsTwo.getOddTeamTwoWin() - 1000));
            }
            default -> log.info("Don't bet anything");
        }
    }
}
