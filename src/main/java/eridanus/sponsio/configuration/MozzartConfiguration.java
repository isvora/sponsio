package eridanus.sponsio.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class MozzartConfiguration {

    @Value("${mozzart.api.betOffer}")
    private String betOffer;

    @Value("${mozzart.api.odds}")
    private String odds;
}
