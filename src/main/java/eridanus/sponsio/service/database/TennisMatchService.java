package eridanus.sponsio.service.database;

import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.repository.TennisMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TennisMatchService {

    private final TennisMatchRepository tennisMatchRepository;

    public TennisMatch saveTennisMatch(TennisMatch entity) {
        return tennisMatchRepository.save(entity);
    }
}
