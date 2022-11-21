package eridanus.sponsio.service.bookies;

import eridanus.sponsio.configuration.BetanoConfiguration;
import eridanus.sponsio.configuration.MozzartConfiguration;
import eridanus.sponsio.model.betano.BetanoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

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
}
