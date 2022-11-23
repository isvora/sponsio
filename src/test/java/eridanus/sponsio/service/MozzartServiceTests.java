package eridanus.sponsio.service;

import eridanus.sponsio.configuration.MozzartConfiguration;
import eridanus.sponsio.json.MozzartMatchesJson;
import eridanus.sponsio.json.MozzartCompetitionJson;
import eridanus.sponsio.model.mozzart.MozzartMatch;
import eridanus.sponsio.model.mozzart.MozzartParticipant;
import eridanus.sponsio.model.mozzart.MozzartResponse;
import eridanus.sponsio.service.bookies.MozzartService;
import eridanus.sponsio.service.database.OddsService;
import eridanus.sponsio.service.database.TennisMatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class MozzartServiceTests {

    private MozzartService mozzartService;

    @Mock
    private MozzartConfiguration mozzartConfiguration;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MozzartCompetitionJson mozzartCompetitionJson;

    @Mock
    private MozzartMatchesJson mozzartMatchesJson;

    @Mock
    private TennisMatchService tennisMatchService;

    @Mock
    private OddsService oddsService;

    @BeforeEach
    void init() {
        mozzartService = new MozzartService(mozzartConfiguration, restTemplate, mozzartCompetitionJson, mozzartMatchesJson, tennisMatchService, oddsService);
    }

    @Test
    void testGetMozzartTennisMatches() {
        // given
        int id = 123;
        var playerOne = "Roger F.";
        var playerTwo = "Nadal R.";
        var betOffer = "/betOffer";
        var mozzartResponse = MozzartResponse.builder()
            .matches(List.of(MozzartMatch.builder()
                    .id(id)
                    .participants(List.of(MozzartParticipant.builder()
                            .name(playerOne)
                            .build(), MozzartParticipant.builder()
                            .name(playerTwo)
                            .build()))
                    .build()))
            .build();

        Mockito.when(mozzartConfiguration.getBetOffer()).thenReturn(betOffer);
        Mockito.when(restTemplate.postForEntity(eq(betOffer), any(HttpEntity.class), eq(MozzartResponse.class)))
                        .thenReturn(ResponseEntity.of(Optional.of(mozzartResponse)));
        Mockito.doNothing().when(tennisMatchService).saveTennisMatch(any());

        // when
        var response = mozzartService.getMozzartTennisMatches();

        // then
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(response.get(0).getMatches().get(0).getId(), id);
        Assertions.assertEquals(response.get(0).getMatches().get(0).getParticipants().get(0).getName(), playerOne);
        Assertions.assertEquals(response.get(0).getMatches().get(0).getParticipants().get(1).getName(), playerTwo);
    }
}
