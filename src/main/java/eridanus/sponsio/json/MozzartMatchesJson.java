package eridanus.sponsio.json;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(value = "classpath:mozzart_matches.json",
        factory = JsonPropertySourceFactory.class)
@ConfigurationProperties
@Getter
@Setter
public class MozzartMatchesJson {

    private List<Integer> matchIds;
}
