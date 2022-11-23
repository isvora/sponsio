package eridanus.sponsio.service.bookies;

import eridanus.sponsio.configuration.BetanoConfiguration;
import eridanus.sponsio.mapper.FootballMatchMapper;
import eridanus.sponsio.mapper.FootballOddsMapper;
import eridanus.sponsio.model.betano.competitions.BetanoResponse;
import eridanus.sponsio.model.betano.fotbal.BetanoFootballMatchData;
import eridanus.sponsio.model.betano.fotbal.BetanoFootballMatchResponse;
import eridanus.sponsio.service.database.FootballMatchService;
import eridanus.sponsio.service.database.FootballOddsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BetanoFootballService {

    private final BetanoConfiguration betanoConfiguration;

    @Qualifier("betanoRestTemplate")
    private final RestTemplate restTemplate;

    private final FootballMatchService footballMatchService;

    private final FootballOddsService footballOddsService;

    public BetanoResponse getBetanoFootballLeagues() {
        var response = restTemplate.exchange(
                betanoConfiguration.getFotbalApi(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BetanoResponse>() {});

        return response.getBody();
    }

    public void getBetanoFotbalMatchesByCompetition(String url) {
        var response = restTemplate.exchange(
                String.format(betanoConfiguration.getApi(), url),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BetanoFootballMatchResponse>() {});

        saveBetanoFotbalMatches(Objects.requireNonNull(response.getBody()).getData());
    }

    private void saveBetanoFotbalMatches(BetanoFootballMatchData matchData) {
        if (matchData != null) {
            matchData.getBlocks().get(0).getEvents().forEach(betanoEvent -> {
                var footballMatch = FootballMatchMapper.mapBetano(betanoEvent);
                if (footballMatch.isPresent()) {
                    footballMatchService.save(footballMatch.get());
                    var footballOdds = FootballOddsMapper.mapBetano(betanoEvent, footballMatch.get());
                    footballOdds.ifPresent(footballOddsService::save);
                }
            });
        }
    }
}
