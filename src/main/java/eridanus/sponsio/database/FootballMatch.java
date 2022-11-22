package eridanus.sponsio.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "football_match")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FootballMatch {

    @Id
    private Long id;

    @Column(name = "team_one")
    private String teamOne;

    @Column(name = "team_two")
    private String teamTwo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tennisMatch")
    @JsonIgnore
    private Set<FootballOdds> footballOdds;
}
