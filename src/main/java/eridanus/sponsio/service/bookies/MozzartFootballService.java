package eridanus.sponsio.service.bookies;

import eridanus.sponsio.configuration.MozzartConfiguration;
import eridanus.sponsio.json.MozzartCompetitionJson;
import eridanus.sponsio.json.MozzartMatchesJson;
import eridanus.sponsio.mapper.FootballMatchMapper;
import eridanus.sponsio.mapper.FootballOddsMapper;
import eridanus.sponsio.mapper.OddsMapper;
import eridanus.sponsio.mapper.TennisMatchMapper;
import eridanus.sponsio.model.mozzart.MozzartMatch;
import eridanus.sponsio.model.mozzart.MozzartOddsResponse;
import eridanus.sponsio.model.mozzart.MozzartResponse;
import eridanus.sponsio.service.database.FootballMatchService;
import eridanus.sponsio.service.database.FootballOddsService;
import eridanus.sponsio.service.database.OddsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MozzartFootballService {

    private final MozzartConfiguration mozzartConfiguration;

    @Qualifier("mozzartFootballRestTemplate")
    private final RestTemplate restTemplate;

    private final MozzartCompetitionJson mozzartCompetitionJson;

    private final FootballMatchService footballMatchService;

    private final MozzartMatchesJson mozzartMatchesJson;

    private final FootballOddsService footballOddsService;

    public List<MozzartResponse> getFootballMatches() {
        List<MozzartResponse> responses = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        mozzartCompetitionJson.setSportIds(List.of(1));
        /*
          Mozzart api returns games in sets of 50. In order to get all games we have to loop through sets of 50s
          until there is no more games left. This is done by changing the offset parameter in the request body.
         */
        for(int offset = 0; offset<1000; offset += 50) {
            mozzartCompetitionJson.setOffset(offset);
            var response = restTemplate.postForEntity(
                    mozzartConfiguration.getBetOffer(),
                    new HttpEntity<>(mozzartCompetitionJson, headers),
                    MozzartResponse.class);

            if (response.hasBody() && !Objects.requireNonNull(response.getBody()).getMatches().isEmpty()) {
                responses.add(response.getBody());
            } else {
                break;
            }
        }

        saveFootballMatches(responses);

        return responses;
    }

    public void getFootballOdds(List<Integer> matchIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        mozzartMatchesJson.setMatchIds(matchIds);

        var response = restTemplate.exchange(
                mozzartConfiguration.getOdds(),
                HttpMethod.POST,
                new HttpEntity<>(mozzartMatchesJson, headers),
                new ParameterizedTypeReference<List<MozzartOddsResponse>>() {}
        );

        for (var mozzartOddsResponse : Objects.requireNonNull(response.getBody())) {
            var mozzartOdds = new ArrayList<>(mozzartOddsResponse.getMozzartOdds().values());
            var footballMatch = footballMatchService.findById(
                    FootballOddsMapper.calculateFootballMatchId(mozzartOdds.get(0), mozzartOdds.get(2)));
            if (footballMatch.isPresent()) {
                footballOddsService.save(FootballOddsMapper.mapMozzart(mozzartOdds.get(0), mozzartOdds.get(1), mozzartOdds.get(2), footballMatch.get()));
            } else {
                log.info("No match found for given id");
            }
        }
    }

    private void saveFootballMatches(List<MozzartResponse> mozzartResponses) {
        for (MozzartResponse mozzartResponse : mozzartResponses) {
            for (MozzartMatch mozzartMatch : mozzartResponse.getMatches()) {
                var footballMatch = FootballMatchMapper.mapMozzart(mozzartMatch);
                footballMatch.ifPresent(footballMatchService::save);
            }
        }
    }
}
