package eridanus.sponsio.repository;

import eridanus.sponsio.database.FootballMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {

    Optional<FootballMatch> findById(long id);
}
