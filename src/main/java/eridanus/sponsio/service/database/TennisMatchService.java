package eridanus.sponsio.service.database;

import eridanus.sponsio.database.TennisMatch;
import eridanus.sponsio.repository.TennisMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TennisMatchService {

    private final TennisMatchRepository tennisMatchRepository;

    public void saveTennisMatch(TennisMatch entity) {
        tennisMatchRepository.save(entity);
    }

    public Optional<TennisMatch> findById(long id) {
        return tennisMatchRepository.findById(id);
    }

    public List<TennisMatch> getAllMatches() {
        return tennisMatchRepository.findAll();
    }
}
