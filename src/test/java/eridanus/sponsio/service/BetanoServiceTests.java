package eridanus.sponsio.service;

import eridanus.sponsio.configuration.BetanoConfiguration;
import eridanus.sponsio.model.betano.competitions.*;
import eridanus.sponsio.service.bookies.BetanoService;
import eridanus.sponsio.service.database.OddsService;
import eridanus.sponsio.service.database.TennisMatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class BetanoServiceTests {

    private BetanoService betanoService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    private BetanoConfiguration betanoConfiguration;

    @Mock
    TennisMatchService tennisMatchService;

    @Mock
    OddsService oddsService;

    @BeforeEach
    void init() {
        betanoService = new BetanoService(betanoConfiguration, restTemplate, tennisMatchService, oddsService);
    }

    @Test
    void testGetBetanoTennisLeagues() {
        // given
        String api = "http://www.betano.com/api";
        String name= "Wimbledon";
        String url = "/sports/tennis/wimbledon/1234";
        BetanoResponse betanoResponse = BetanoResponse.builder()
                .data(BetanoData.builder()
                        .regionGroups(List.of(BetanoRegionGroups.builder()
                                .regions(List.of(BetanoRegion.builder()
                                        .leagues(List.of(BetanoLeague.builder()
                                                .name(name)
                                                .url(url)
                                                .build()))
                                        .build()))
                                .build()))
                        .build())
                .build();

        Mockito.when(betanoConfiguration.getTennisApi()).thenReturn(api);
        Mockito.when(restTemplate.exchange(eq(api), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.of(Optional.of(betanoResponse)));

        // when
        var response = betanoService.getBetanoTennisLeagues();

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getData().getRegionGroups().get(0).getRegions().get(0).getLeagues().get(0).getName(), name);
        Assertions.assertEquals(response.getData().getRegionGroups().get(0).getRegions().get(0).getLeagues().get(0).getUrl(), url);
    }
}
