package eridanus.sponsio.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class WinnerConfiguration {

    @Value("${winner.api}")
    private String api;

    @Value("${winner.api.language}")
    private String language;

    @Value("${winner.api.dataFormat}")
    private String dataFormat;
}
