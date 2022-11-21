package eridanus.sponsio.service.bookies;

import eridanus.sponsio.configuration.BetanoConfiguration;
import eridanus.sponsio.mapper.OddsMapper;
import eridanus.sponsio.mapper.TennisMatchMapper;
import eridanus.sponsio.model.betano.competitions.BetanoResponse;
import eridanus.sponsio.model.betano.matches.BetanoMatchData;
import eridanus.sponsio.model.betano.matches.BetanoMatchResponse;
import eridanus.sponsio.service.database.OddsService;
import eridanus.sponsio.service.database.TennisMatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BetanoService {

    private final BetanoConfiguration betanoConfiguration;

    @Qualifier("betanoRestTemplate")
    private final RestTemplate restTemplate;

    private final TennisMatchService tennisMatchService;

    private final OddsService oddsService;

    public BetanoResponse getBetanoTennisLeagues() {
        var response = restTemplate.exchange(
                betanoConfiguration.getTennisApi(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BetanoResponse>() {});

        return response.getBody();
    }

    public void getBetanoTennisMatchByCompetition(String url) {
        var response = restTemplate.exchange(
                String.format(betanoConfiguration.getApi(), url),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BetanoMatchResponse>() {});

        saveBetanoMatches(Objects.requireNonNull(response.getBody()).getData());
    }

    private void saveBetanoMatches(BetanoMatchData betanoMatchData) {
        betanoMatchData.getBlocks().get(0).getEvents().forEach(betanoEvent -> {
            var tennisMatch = TennisMatchMapper.map(betanoEvent);
            if (tennisMatch != null) {
                tennisMatchService.saveTennisMatch(tennisMatch);

                var odds = OddsMapper.map(betanoEvent, tennisMatch);

                if (odds != null) {
                    oddsService.saveOdds(odds);
                }
            }
        });
    }


}
