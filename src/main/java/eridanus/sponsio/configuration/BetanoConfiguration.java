package eridanus.sponsio.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class BetanoConfiguration {

    @Value("${betano.api.tennis}")
    private String tennisApi;

    @Value("${betano.api.fotbal}")
    private String fotbalApi;

    @Value("${betano.api}")
    private String api;
}
