package eridanus.sponsio.service.bookies;

import eridanus.sponsio.configuration.MozzartConfiguration;
import eridanus.sponsio.json.MozzartMatchesJson;
import eridanus.sponsio.json.MozzartTennisJson;
import eridanus.sponsio.mapper.OddsMapper;
import eridanus.sponsio.mapper.TennisMatchMapper;
import eridanus.sponsio.model.mozzart.MozzartMatch;
import eridanus.sponsio.model.mozzart.MozzartOddsResponse;
import eridanus.sponsio.model.mozzart.MozzartResponse;
import eridanus.sponsio.service.database.OddsService;
import eridanus.sponsio.service.database.TennisMatchService;
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
public class MozzartService {

    private final MozzartConfiguration mozzartConfiguration;

    @Qualifier("mozzartRestTemplate")
    private final RestTemplate restTemplate;

    private final MozzartTennisJson mozzartTennisJson;

    private final MozzartMatchesJson mozzartMatchesJson;

    private final TennisMatchService tennisMatchService;

    private final OddsService oddsService;

    public List<MozzartResponse> getMozzartTennisMatches() {
        List<MozzartResponse> responses = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        /*
          Mozzart api returns games in sets of 50. In order to get all games we have to loop through sets of 50s
          until there is no more games left. This is done by changing the offset parameter in the request body.
         */
        for(int offset = 0; offset<1000; offset += 50) {
            mozzartTennisJson.setOffset(offset);
            var response = restTemplate.postForEntity(
                    mozzartConfiguration.getBetOffer(),
                    new HttpEntity<>(mozzartTennisJson, headers),
                    MozzartResponse.class);

            if (response.hasBody() && !Objects.requireNonNull(response.getBody()).getMatches().isEmpty()) {
                responses.add(response.getBody());
            } else {
                break;
            }
        }

        saveTennisMatches(responses);

        return responses;
    }

    public void getTennisOdds(List<Integer> matchIds) {
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
            var tennisMatch = tennisMatchService.findById(
                    OddsMapper.calculateTennisMatchId(mozzartOdds.get(0), mozzartOdds.get(1)));
            if (tennisMatch.isPresent()) {
                oddsService.saveOdds(OddsMapper.mapMozzartOddsToOdds(mozzartOdds.get(0), mozzartOdds.get(1), tennisMatch.get()));
            } else {
                log.info("No match found for given id");
            }
        }
    }

    private void saveTennisMatches(List<MozzartResponse> mozzartResponses) {
        for (MozzartResponse mozzartResponse : mozzartResponses) {
            for (MozzartMatch mozzartMatch : mozzartResponse.getMatches()) {
                tennisMatchService.saveTennisMatch(TennisMatchMapper.map(mozzartMatch));
            }
        }
    }
}
