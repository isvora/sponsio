package eridanus.sponsio.repository;

import eridanus.sponsio.database.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OddsRepository extends JpaRepository<Odds, Long> {
}
