package eridanus.sponsio.service.bookies;

import eridanus.sponsio.configuration.BetanoConfiguration;
import eridanus.sponsio.model.betano.competitions.BetanoResponse;
import eridanus.sponsio.model.betano.matches.BetanoMatchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BetanoService {

    private final BetanoConfiguration betanoConfiguration;

    @Qualifier("betanoRestTemplate")
    private final RestTemplate restTemplate;

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

        log.info(response.toString());
    }
}
