package eridanus.sponsio.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tennis_match")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TennisMatch {

    @Id
    private Long id;

    @OneToMany(mappedBy = "tennisMatch")
    @JsonIgnore
    private Set<Odds> odds;
}
