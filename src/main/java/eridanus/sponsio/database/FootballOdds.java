package eridanus.sponsio.database;

import eridanus.sponsio.helper.Bookie;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "football_match")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FootballOdds {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "team_one_win")
    private double oddTeamOneWin;

    @Column(name = "team_two_win")
    private double oddTeamTwoWin;

    @Column(name = "draw")
    private double oddDraw;

    @ManyToOne
    @JoinColumn(name="football_match", nullable=false)
    private FootballMatch tennisMatch;

    @Column(name = "bookie")
    private Bookie bookie;
}
