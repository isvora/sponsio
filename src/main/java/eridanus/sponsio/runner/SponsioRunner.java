package eridanus.sponsio.runner;

import eridanus.sponsio.service.MozzartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SponsioRunner implements CommandLineRunner {

    private final MozzartService mozzartService;

    @Override
    public void run(String... args) {
        var mozzartMatches = mozzartService.getMozzartTennisMatches();
        mozzartMatches.forEach( mozzartResponse -> mozzartService.odds(mozzartResponse.getMatches().stream()
                .flatMap(mozzartMatch -> mozzartMatch.getId().describeConstable().stream())
                .collect(Collectors.toList()))
        );
    }
}
