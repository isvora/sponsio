package eridanus.sponsio.json;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(value = "classpath:mozzart_tennis.json",
        factory = JsonPropertySourceFactory.class)
@ConfigurationProperties
@Getter
@Setter
public class MozzartTennisJson {

    private String date;

    private List<Integer> sportIds;

    private String sort;

    private int offset;
}
