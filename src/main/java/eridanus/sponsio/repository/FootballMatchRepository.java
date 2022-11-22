package eridanus.sponsio.repository;

import eridanus.sponsio.database.FootballMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
}
