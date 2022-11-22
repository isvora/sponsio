package eridanus.sponsio.service.bookies;

import eridanus.sponsio.configuration.WinnerConfiguration;
import eridanus.sponsio.model.winner.WinnerResponse;
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
public class WinnerTennisService {

    private final WinnerConfiguration winnerConfiguration;

    @Qualifier("winnerRestTemplate")
    private final RestTemplate restTemplate;

    public Optional<WinnerResponse> getTennisData() {
        var response = restTemplate.getForObject(
                winnerConfiguration.getApi(),
                WinnerResponse.class,
                winnerConfiguration.getLanguage(),
                winnerConfiguration.getDataFormat()
        );

        return response == null ? Optional.empty() : Optional.of(response);
    }
}
