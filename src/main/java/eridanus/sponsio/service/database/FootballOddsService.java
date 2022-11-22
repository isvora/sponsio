package eridanus.sponsio.service.database;

import eridanus.sponsio.database.FootballOdds;
import eridanus.sponsio.repository.FootballOddsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FootballOddsService {

    private final FootballOddsRepository footballOddsRepository;

    public void save(FootballOdds entity) {
        footballOddsRepository.save(entity);
    }
}
