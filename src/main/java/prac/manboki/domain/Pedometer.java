package prac.manboki.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Pedometer {

    @Id @GeneratedValue
    @Column(name = "pedometer_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    private Member member;

    @Column(name = "today_steps")
    private int todaySteps;

    @Column(name = "total_steps")
    private int totalSteps;

}
