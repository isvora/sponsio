package eridanus.sponsio.database;

import eridanus.sponsio.helper.Bookie;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "odds")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "odd_one")
    private double oddOne;

    @Column(name = "odd_two")
    private double oddTwo;

    @ManyToOne
    @JoinColumn(name="tennis_match", nullable=false)
    private TennisMatch tennisMatch;

    @Column(name = "bookie")
    private Bookie bookie;
}
