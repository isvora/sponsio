package eridanus.sponsio.repository;

import eridanus.sponsio.database.FootballOdds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballOddsRepository extends JpaRepository<FootballOdds, Long> {
}
