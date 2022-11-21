package eridanus.sponsio.repository;

import eridanus.sponsio.database.TennisMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TennisMatchRepository extends JpaRepository<TennisMatch, Long> {

    Optional<TennisMatch> findById(long id);
}
