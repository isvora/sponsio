package eridanus.sponsio.runner;

import eridanus.sponsio.model.mozzart.MozzartResponse;
import eridanus.sponsio.service.bookies.MozzartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SponsioRunner implements CommandLineRunner {

    private final MozzartService mozzartService;

    @Override
    public void run(String... args) {
        log.info("Starting Sponsio...");
        log.info("Collecting mozzart tennis games");

        var mozzartMatches = mozzartService.getMozzartTennisMatches();
        for (MozzartResponse mozzartResponse : mozzartMatches) {
            var matchIds = mozzartResponse.getMatches().stream()
                    .flatMap(mozzartMatch -> mozzartMatch.getId().describeConstable().stream()).toList();
            mozzartService.getTennisOdds(matchIds);
        }

        log.info("Finished collecting mozzart tennis games");
    }
}
