package eridanus.sponsio.service.database;

import eridanus.sponsio.database.Odds;
import eridanus.sponsio.repository.OddsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OddsService {

    private final OddsRepository oddsRepository;

    public Odds saveOdds(Odds entity) {
       return oddsRepository.save(entity);
    }
}
