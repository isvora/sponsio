package eridanus.sponsio.service.database;

import eridanus.sponsio.database.Odds;
import eridanus.sponsio.repository.OddsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OddsService {

    private final OddsRepository oddsRepository;

    public void saveOdds(Odds entity) {
        oddsRepository.save(entity);
    }
}
