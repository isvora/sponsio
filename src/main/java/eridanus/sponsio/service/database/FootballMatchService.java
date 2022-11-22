package eridanus.sponsio.service.database;

import eridanus.sponsio.database.FootballMatch;
import eridanus.sponsio.repository.FootballMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FootballMatchService {

    private final FootballMatchRepository footballMatchRepository;

    public void save(FootballMatch entity) {
        footballMatchRepository.save(entity);
    }

    public List<FootballMatch> findAlL() {
        return footballMatchRepository.findAll();
    }
}
